package StriverSheet._3Arrays._1Easy;

public class _9TwiceSingleNumber {

  static int findOne(int[] a) {
    int x=0;
    for(int i=0;i<a.length;i++){
      x^=a[i];
    }
    return x;
  }
  public static void main(String[] args) {
    int[] a={4,1,2,1,4};
    System.out.println(findOne(a));
  }

}
