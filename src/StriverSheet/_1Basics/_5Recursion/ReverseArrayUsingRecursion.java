package StriverSheet._1Basics._5Recursion;

public class ReverseArrayUsingRecursion {

  public static void main(String[] args) {
    int[] a = {1,2,3,4,5,6,7,8,9};
    reverse(a);
    reverseArray(a,0,a.length-1);
    for(int i:a)
    System.out.println(i);
  }

  static void reverseArray(int a[], int start, int end) {
    if (start < end) {
      int tmp = a[start];
      a[start] = a[end];
      a[end] = tmp;
      reverseArray(a, start + 1, end - 1);
    }
  }

  private static void reverse(int[] a) {
    int n=a.length;
    for(int i=0;i<n/2;i++){
      int temp=a[i];
      a[i]=a[n-1-i];
      a[n-1-i]=temp;
    }
  }
}
