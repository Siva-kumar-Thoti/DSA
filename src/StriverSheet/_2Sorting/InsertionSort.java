package StriverSheet._2Sorting;

import java.util.Arrays;

public class InsertionSort {

  public static void main(String[] args) {
    int[] a = {64, 25, 12, 22, 11};
    int n = a.length;
    for(int i=1;i<n;i++){
      int j=i-1;
      int x=a[i];
      while(j>=0 && x<a[j]){
        a[j+1]=a[j];
        j=j-1;
      }
      a[j+1]=x;
    }

    System.out.println(Arrays.toString(a));

  }

}
