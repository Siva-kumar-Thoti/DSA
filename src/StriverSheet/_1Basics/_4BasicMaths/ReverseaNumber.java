package StriverSheet._1Basics._4BasicMaths;

import java.util.Scanner;

public class ReverseaNumber {
  public int reverse ( int x){
    int rev = 0;
    while (x != 0) {
      int d = x % 10;
      x /= 10;
      if ((rev > (Integer.MAX_VALUE / 10))
          || (rev < (Integer.MIN_VALUE / 10))) {
        return 0;
      }
      rev = rev * 10 + d;
    }
    return rev;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter a number");
    int x = sc.nextInt();
    ReverseaNumber obj = new ReverseaNumber();
    System.out.println(obj.reverse(x));
  }
}
