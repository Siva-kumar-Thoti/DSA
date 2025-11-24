package StriverSheet._5Recursion;

import java.util.Scanner;

public class StringPalindrome {
  public static void main(String[] args) {
    Scanner scanner=new Scanner(System.in);
    String str=scanner.nextLine();
    System.out.println(isPalindrome(str,str.length(),0));
  }

  private static boolean isPalindrome(String str, int n, int i) {
    if(i>=n/2) return true;
    if(str.charAt(i)==str.charAt(n-i-1))
      return isPalindrome(str,n,i+1);
    return false;
  }

}
