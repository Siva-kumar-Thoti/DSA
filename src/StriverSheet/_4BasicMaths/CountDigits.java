package StriverSheet._4BasicMaths;

public class CountDigits {

  public static void main(String[] args) {
    int n=123;
    System.out.println(count(n));
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