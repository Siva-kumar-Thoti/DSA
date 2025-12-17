package LLD;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// ==================== ENUMS ====================

enum TransactionType {
  ADD_MONEY, SEND_MONEY, RECEIVE_MONEY, REFUND
}

enum TransactionStatus {
  PENDING, SUCCESS, FAILED, REVERSED
}

enum PaymentMethod {
  UPI, CARD, NET_BANKING
}

// ==================== MODELS ====================


class User {
  private final String userId;
  private final String name;
  private final String email;
  private final String phone;

  public User(String userId, String name, String email, String phone) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public String getUserId() { return userId; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getPhone() { return phone; }
}

class Wallet {
  private final String walletId;
  private final String userId;
  private BigDecimal balance;
  private final ReentrantReadWriteLock lock;
  private final LocalDateTime createdAt;

  public Wallet(String walletId, String userId) {
    this.walletId = walletId;
    this.userId = userId;
    this.balance = BigDecimal.ZERO;
    this.lock = new ReentrantReadWriteLock(true); // Fair lock
    this.createdAt = LocalDateTime.now();
  }

  public String getWalletId() { return walletId; }
  public String getUserId() { return userId; }

  public BigDecimal getBalance() {
    lock.readLock().lock();
    try {
      return balance;
    } finally {
      lock.readLock().unlock();
    }
  }

  public boolean debit(BigDecimal amount) {
    lock.writeLock().lock();
    try {
      if (balance.compareTo(amount) >= 0) {
        balance = balance.subtract(amount);
        return true;
      }
      return false;
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void credit(BigDecimal amount) {
    lock.writeLock().lock();
    try {
      balance = balance.add(amount);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public LocalDateTime getCreatedAt() { return createdAt; }
}

class Transaction {
  private final String transactionId;
  private final String fromWalletId;
  private final String toWalletId;
  private final BigDecimal amount;
  private final TransactionType type;
  private TransactionStatus status;
  private final PaymentMethod paymentMethod;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String remarks;

  public Transaction(String transactionId, String fromWalletId, String toWalletId,
      BigDecimal amount, TransactionType type, PaymentMethod paymentMethod) {
    this.transactionId = transactionId;
    this.fromWalletId = fromWalletId;
    this.toWalletId = toWalletId;
    this.amount = amount;
    this.type = type;
    this.status = TransactionStatus.PENDING;
    this.paymentMethod = paymentMethod;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Getters
  public String getTransactionId() { return transactionId; }
  public String getFromWalletId() { return fromWalletId; }
  public String getToWalletId() { return toWalletId; }
  public BigDecimal getAmount() { return amount; }
  public TransactionType getType() { return type; }
  public TransactionStatus getStatus() { return status; }
  public PaymentMethod getPaymentMethod() { return paymentMethod; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public String getRemarks() { return remarks; }

  // Setters
  public void setStatus(TransactionStatus status) {
    this.status = status;
    this.updatedAt = LocalDateTime.now();
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s | %s -> %s | Amount: %.2f | Status: %s | %s",
        transactionId, type, fromWalletId, toWalletId, amount, status, createdAt);
  }
}

// ==================== EXCEPTIONS ====================

class WalletException extends Exception {
  public WalletException(String message) {
    super(message);
  }
}

class InsufficientBalanceException extends WalletException {
  public InsufficientBalanceException(String message) {
    super(message);
  }
}

class InvalidTransactionException extends WalletException {
  public InvalidTransactionException(String message) {
    super(message);
  }
}

// ==================== SERVICES ====================

class WalletService {
  private final Map<String, User> users;
  private final Map<String, Wallet> wallets;
  private final Map<String, String> userToWallet; // userId -> walletId
  private final Map<String, Transaction> transactions;
  private final Map<String, List<String>> walletTransactions; // walletId -> transaction IDs
  private final Set<String> processedTransactions; // For idempotency
  private final ReentrantLock transactionLock;

  public WalletService() {
    this.users = new ConcurrentHashMap<>();
    this.wallets = new ConcurrentHashMap<>();
    this.userToWallet = new ConcurrentHashMap<>();
    this.transactions = new ConcurrentHashMap<>();
    this.walletTransactions = new ConcurrentHashMap<>();
    this.processedTransactions = ConcurrentHashMap.newKeySet();
    this.transactionLock = new ReentrantLock(true);
  }

  // ==================== USER & WALLET MANAGEMENT ====================

  public User createUser(String name, String email, String phone) {
    String userId = UUID.randomUUID().toString();
    User user = new User(userId, name, email, phone);
    users.put(userId, user);
    return user;
  }

  public Wallet createWallet(String userId) throws WalletException {
    if (!users.containsKey(userId)) {
      throw new WalletException("User not found: " + userId);
    }

    if (userToWallet.containsKey(userId)) {
      throw new WalletException("Wallet already exists for user: " + userId);
    }

    String walletId = "WLT_" + UUID.randomUUID().toString().substring(0, 8);
    Wallet wallet = new Wallet(walletId, userId);
    wallets.put(walletId, wallet);
    userToWallet.put(userId, walletId);
    walletTransactions.put(walletId, new CopyOnWriteArrayList<>());

    return wallet;
  }

  public BigDecimal getBalance(String walletId) throws WalletException {
    Wallet wallet = getWallet(walletId);
    return wallet.getBalance();
  }

  private Wallet getWallet(String walletId) throws WalletException {
    Wallet wallet = wallets.get(walletId);
    if (wallet == null) {
      throw new WalletException("Wallet not found: " + walletId);
    }
    return wallet;
  }

  // ==================== ADD MONEY ====================

  public Transaction addMoney(String walletId, BigDecimal amount,
      PaymentMethod paymentMethod, String idempotencyKey)
      throws WalletException {

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidTransactionException("Amount must be positive");
    }

    // Check idempotency
    if (idempotencyKey != null && processedTransactions.contains(idempotencyKey)) {
      throw new InvalidTransactionException("Duplicate transaction: " + idempotencyKey);
    }

    Wallet wallet = getWallet(walletId);

    String txnId = "TXN_" + UUID.randomUUID().toString().substring(0, 12);
    Transaction transaction = new Transaction(
        txnId, null, walletId, amount, TransactionType.ADD_MONEY, paymentMethod
    );

    transactions.put(txnId, transaction);

    try {
      // Simulate payment gateway processing
      boolean paymentSuccess = simulatePaymentGateway(paymentMethod);

      if (paymentSuccess) {
        wallet.credit(amount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setRemarks("Money added successfully");

        // Mark as processed for idempotency
        if (idempotencyKey != null) {
          processedTransactions.add(idempotencyKey);
        }
      } else {
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setRemarks("Payment gateway declined");
      }

    } catch (Exception e) {
      transaction.setStatus(TransactionStatus.FAILED);
      transaction.setRemarks("Error: " + e.getMessage());
    }

    walletTransactions.get(walletId).add(txnId);
    return transaction;
  }

  // ==================== SEND MONEY (P2P Transfer) ====================

  public Transaction sendMoney(String fromWalletId, String toWalletId,
      BigDecimal amount, String idempotencyKey)
      throws WalletException {

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidTransactionException("Amount must be positive");
    }

    if (fromWalletId.equals(toWalletId)) {
      throw new InvalidTransactionException("Cannot transfer to same wallet");
    }

    // Check idempotency
    if (idempotencyKey != null && processedTransactions.contains(idempotencyKey)) {
      throw new InvalidTransactionException("Duplicate transaction: " + idempotencyKey);
    }

    Wallet fromWallet = getWallet(fromWalletId);
    Wallet toWallet = getWallet(toWalletId);

    String txnId = "TXN_" + UUID.randomUUID().toString().substring(0, 12);
    Transaction transaction = new Transaction(
        txnId, fromWalletId, toWalletId, amount,
        TransactionType.SEND_MONEY, PaymentMethod.UPI
    );

    transactions.put(txnId, transaction);

    // Acquire locks in consistent order to prevent deadlock
    Wallet first = fromWalletId.compareTo(toWalletId) < 0 ? fromWallet : toWallet;
    Wallet second = fromWalletId.compareTo(toWalletId) < 0 ? toWallet : fromWallet;

    transactionLock.lock();
    try {
      // Phase 1: Debit from sender
      boolean debitSuccess = fromWallet.debit(amount);

      if (!debitSuccess) {
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setRemarks("Insufficient balance");
        throw new InsufficientBalanceException("Insufficient balance in wallet: " + fromWalletId);
      }

      try {
        // Phase 2: Credit to receiver
        toWallet.credit(amount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setRemarks("Transfer successful");

        // Mark as processed for idempotency
        if (idempotencyKey != null) {
          processedTransactions.add(idempotencyKey);
        }

        // Create corresponding receive transaction
        String receiveTxnId = "TXN_" + UUID.randomUUID().toString().substring(0, 12);
        Transaction receiveTransaction = new Transaction(
            receiveTxnId, fromWalletId, toWalletId, amount,
            TransactionType.RECEIVE_MONEY, PaymentMethod.UPI
        );
        receiveTransaction.setStatus(TransactionStatus.SUCCESS);
        receiveTransaction.setRemarks("Money received from " + fromWalletId);
        transactions.put(receiveTxnId, receiveTransaction);
        walletTransactions.get(toWalletId).add(receiveTxnId);

      } catch (Exception e) {
        // Rollback: Credit back to sender
        fromWallet.credit(amount);
        transaction.setStatus(TransactionStatus.REVERSED);
        transaction.setRemarks("Transfer failed, amount reversed: " + e.getMessage());

        // Create refund transaction
        createRefundTransaction(fromWalletId, amount, txnId);
      }

    } finally {
      transactionLock.unlock();
    }

    walletTransactions.get(fromWalletId).add(txnId);
    return transaction;
  }

  // ==================== REFUND LOGIC ====================

  private void createRefundTransaction(String walletId, BigDecimal amount, String originalTxnId) {
    String refundTxnId = "REFUND_" + UUID.randomUUID().toString().substring(0, 12);
    Transaction refundTransaction = new Transaction(
        refundTxnId, null, walletId, amount,
        TransactionType.REFUND, PaymentMethod.UPI
    );
    refundTransaction.setStatus(TransactionStatus.SUCCESS);
    refundTransaction.setRemarks("Refund for failed transaction: " + originalTxnId);
    transactions.put(refundTxnId, refundTransaction);
    walletTransactions.get(walletId).add(refundTxnId);
  }

  // ==================== TRANSACTION HISTORY ====================

  public List<Transaction> getTransactionHistory(String walletId) throws WalletException {
    if (!wallets.containsKey(walletId)) {
      throw new WalletException("Wallet not found: " + walletId);
    }

    List<String> txnIds = walletTransactions.get(walletId);
    List<Transaction> history = new ArrayList<>();

    for (String txnId : txnIds) {
      Transaction txn = transactions.get(txnId);
      if (txn != null) {
        history.add(txn);
      }
    }

    // Sort by creation time descending (latest first)
    history.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));

    return history;
  }

  public List<Transaction> getMiniStatement(String walletId, int limit) throws WalletException {
    List<Transaction> history = getTransactionHistory(walletId);
    return history.subList(0, Math.min(limit, history.size()));
  }

  // ==================== HELPER METHODS ====================

  private boolean simulatePaymentGateway(PaymentMethod method) {
    // Simulate 95% success rate
    return Math.random() < 0.95;
  }

  public Transaction getTransaction(String transactionId) {
    return transactions.get(transactionId);
  }

  public Wallet getWalletByUserId(String userId) {
    String walletId = userToWallet.get(userId);
    return walletId != null ? wallets.get(walletId) : null;
  }
}

// ==================== DEMO APPLICATION ====================

public class PaytmWallet {

  public static void main(String[] args) throws Exception {
    WalletService service = new WalletService();

    System.out.println("=== Digital Wallet System Demo ===\n");

    // Create users
    User user1 = service.createUser("Alice Johnson", "alice@example.com", "9876543210");
    User user2 = service.createUser("Bob Smith", "bob@example.com", "9876543211");
    User user3 = service.createUser("Charlie Brown", "charlie@example.com", "9876543212");

    System.out.println("✓ Created 3 users\n");

    // Create wallets
    Wallet wallet1 = service.createWallet(user1.getUserId());
    Wallet wallet2 = service.createWallet(user2.getUserId());
    Wallet wallet3 = service.createWallet(user3.getUserId());

    System.out.println("✓ Created wallets:");
    System.out.println("  " + user1.getName() + " -> " + wallet1.getWalletId());
    System.out.println("  " + user2.getName() + " -> " + wallet2.getWalletId());
    System.out.println("  " + user3.getName() + " -> " + wallet3.getWalletId() + "\n");

    // Test concurrent add money operations
    System.out.println("=== Test 1: Concurrent Add Money ===");
    ExecutorService executor = Executors.newFixedThreadPool(5);
    CountDownLatch latch = new CountDownLatch(3);

    executor.submit(() -> {
      try {
        Transaction txn = service.addMoney(wallet1.getWalletId(),
            new BigDecimal("1000"), PaymentMethod.UPI, "key1");
        System.out.println("Alice added ₹1000: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      } finally {
        latch.countDown();
      }
    });

    executor.submit(() -> {
      try {
        Transaction txn = service.addMoney(wallet2.getWalletId(),
            new BigDecimal("2000"), PaymentMethod.CARD, "key2");
        System.out.println("Bob added ₹2000: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      } finally {
        latch.countDown();
      }
    });

    executor.submit(() -> {
      try {
        Transaction txn = service.addMoney(wallet3.getWalletId(),
            new BigDecimal("1500"), PaymentMethod.NET_BANKING, "key3");
        System.out.println("Charlie added ₹1500: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      } finally {
        latch.countDown();
      }
    });

    latch.await();
    Thread.sleep(500);

    System.out.println("\nBalances after adding money:");
    System.out.println("  Alice: ₹" + service.getBalance(wallet1.getWalletId()));
    System.out.println("  Bob: ₹" + service.getBalance(wallet2.getWalletId()));
    System.out.println("  Charlie: ₹" + service.getBalance(wallet3.getWalletId()) + "\n");

    // Test concurrent P2P transfers
    System.out.println("=== Test 2: Concurrent P2P Transfers ===");
    CountDownLatch transferLatch = new CountDownLatch(3);

    executor.submit(() -> {
      try {
        Transaction txn = service.sendMoney(wallet1.getWalletId(),
            wallet2.getWalletId(), new BigDecimal("200"), "transfer1");
        System.out.println("Alice -> Bob ₹200: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Transfer failed: " + e.getMessage());
      } finally {
        transferLatch.countDown();
      }
    });

    executor.submit(() -> {
      try {
        Transaction txn = service.sendMoney(wallet2.getWalletId(),
            wallet3.getWalletId(), new BigDecimal("500"), "transfer2");
        System.out.println("Bob -> Charlie ₹500: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Transfer failed: " + e.getMessage());
      } finally {
        transferLatch.countDown();
      }
    });

    executor.submit(() -> {
      try {
        Transaction txn = service.sendMoney(wallet3.getWalletId(),
            wallet1.getWalletId(), new BigDecimal("300"), "transfer3");
        System.out.println("Charlie -> Alice ₹300: " + txn.getStatus());
      } catch (Exception e) {
        System.out.println("Transfer failed: " + e.getMessage());
      } finally {
        transferLatch.countDown();
      }
    });

    transferLatch.await();
    Thread.sleep(500);

    System.out.println("\nBalances after transfers:");
    System.out.println("  Alice: ₹" + service.getBalance(wallet1.getWalletId()));
    System.out.println("  Bob: ₹" + service.getBalance(wallet2.getWalletId()));
    System.out.println("  Charlie: ₹" + service.getBalance(wallet3.getWalletId()) + "\n");

    // Test idempotency
    System.out.println("=== Test 3: Idempotency (Retry Protection) ===");
    try {
      service.addMoney(wallet1.getWalletId(), new BigDecimal("500"),
          PaymentMethod.UPI, "idempotency_test");
      System.out.println("First request: SUCCESS");

      service.addMoney(wallet1.getWalletId(), new BigDecimal("500"),
          PaymentMethod.UPI, "idempotency_test");
      System.out.println("Duplicate request: Should not reach here!");
    } catch (InvalidTransactionException e) {
      System.out.println("Duplicate request blocked: " + e.getMessage());
    }

    System.out.println("Alice balance: ₹" + service.getBalance(wallet1.getWalletId()) + "\n");

    // Test insufficient balance
    System.out.println("=== Test 4: Insufficient Balance ===");
    try {
      service.sendMoney(wallet1.getWalletId(), wallet2.getWalletId(),
          new BigDecimal("10000"), "insufficient_test");
    } catch (InsufficientBalanceException e) {
      System.out.println("✓ Correctly rejected: " + e.getMessage());
    }

    // Transaction history
    System.out.println("\n=== Test 5: Mini Statement (Alice - Last 5 transactions) ===");
    List<Transaction> statement = service.getMiniStatement(wallet1.getWalletId(), 5);
    for (Transaction txn : statement) {
      System.out.println(txn);
    }

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);

    System.out.println("\n=== All tests completed successfully! ===");
  }
}