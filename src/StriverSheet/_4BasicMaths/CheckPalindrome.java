package StriverSheet._4BasicMaths;

import java.util.Scanner;

public class CheckPalindrome {
  public boolean isPalindrome(int x) {
    if(x<0) return false;
    int org=x;
    int rev=0;
    while(x>0){
      rev=rev*10+x%10;
      x/=10;
    }
    return rev==org;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter number");
    int n = sc.nextInt();
    CheckPalindrome obj = new CheckPalindrome();
    System.out.println(obj.isPalindrome(n));
  }
}
