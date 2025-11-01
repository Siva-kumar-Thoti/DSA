package StriverSheet._4BasicMaths;

import java.util.Scanner;

public class CountDigits {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter a number: ");
    int num = sc.nextInt();
    System.out.println(count(num));
  }

  static int count(int n){
    int count=0;
    while(n>0){
      n=n/10;
      count++;
    }
    return count;
  }

  static int count2(int n){
    return (int) (Math.log10(n)+1);
  }

}