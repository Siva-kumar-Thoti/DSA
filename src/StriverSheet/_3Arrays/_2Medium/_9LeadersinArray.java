package StriverSheet._3Arrays._2Medium;

import java.util.Arrays;

public class _9LeadersinArray {

  public static int[] replaceElements(int[] a) {
    int n=a.length,maxi=-1,temp=-1;
    for(int i=n-2;i>=0;i--){
      temp=maxi;
      maxi=Math.max(maxi,a[i+1]);
      a[i+1]=temp;
    }

    a[0]=maxi;

    return a;

  }

  public static void main(String[] args) {
    int[] a=replaceElements(new int[]{10, 22, 12, 3, 0, 6});
    System.out.println(Arrays.toString(a));
  }

}
