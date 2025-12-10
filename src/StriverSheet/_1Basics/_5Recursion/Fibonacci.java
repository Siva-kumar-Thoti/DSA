package StriverSheet._1Basics._5Recursion;

public class Fibonacci {
  public static void main(String[] args) {
    int n=10;
    int a=1,b=1,c;
    System.out.println(a);
    System.out.println(b);

    for(int i=1;i<n;i++){
      c=a+b;
      System.out.println(c);
      a=b;
      b=c;
    }

    System.out.println("The "+n+" fibonacci sequence is: "+fib(n));
  }

  static int fib(int n) {
    if(n<=1) return 1;
    return fib(n-1)+fib(n-2);
  }

}
