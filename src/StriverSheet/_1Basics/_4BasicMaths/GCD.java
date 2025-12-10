package StriverSheet._1Basics._4BasicMaths;

import java.util.Scanner;

public class GCD {
  public int gcd(int a, int b) {
    while(b!=0){
      int temp=b;
      b=a%b;
      a=temp;
    }
    return a;
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter 1st number");
    int x = sc.nextInt();
    System.out.println("Enter 2nd number");
    int y = sc.nextInt();
    GCD gcd = new GCD();
    System.out.println(gcd.gcd(x,y));
  }

}
