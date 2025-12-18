package StriverSheet._3Arrays._1Easy;

import java.util.Arrays;

public class _4LeftRotateArray {

  //TC: O(N) , SC: O(K)
  static void rotate(int[] a, int k) {
    int n=a.length;
    k=k%n;
    int[] b=new int[n-k];

    for(int i=0;i<n-k;i++)
      b[i]=a[i];

    for(int i=n-k,j=0;i<n;)
      a[j++]=a[i++];

    for(int i=0;i<n-k;i++)
      a[i+k]=b[i];

  }


  static void rotate2(int[] a, int k) {
    int n=a.length;
    k=k%n;

    reverse(a,0,n-1);

    reverse(a,0,k-1);
    reverse(a,k,n-1);
  }

  static void reverse(int[] a, int l, int r) {
    int mid = l+(r-l)/2;
    for(int i=l;i<=mid;i++){
      int temp=a[i];
      a[i]=a[r];
      a[r--]=temp;
    }
  }

  public static void main(String[] args) {
    int[] a={-15,2,-100,3,99};
    rotate2(a,2);
    System.out.println(Arrays.toString(a));
  }

}
