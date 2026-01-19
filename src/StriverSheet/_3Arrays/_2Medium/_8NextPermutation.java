package StriverSheet._3Arrays._2Medium;

import static java.util.Collections.reverse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _8NextPermutation {
  public static void nextPermutation(int[] a) {
    int n=a.length;

    int x=-1,y=-1;
    for(int i=n-2;i>=0;i--){
      if(a[i]<a[i+1]){
        x=i;
        break;
      }
    }

    if(x==-1){
      reverse(a,0,n-1);
      return;
    }

    for(int i=n-1;i>x;i--){
      if(a[x]<a[i]){
        y=i;
        swap(a,i,x);
        break;
      }
    }

    reverse(a,x+1,n-1);
  }

  private static void reverse(int[] a, int l, int r){
    while(l<r) swap(a,l++,r--);
  }

  private static void swap(int[] a, int i, int j){
    int temp=a[i];
    a[i]=a[j];
    a[j]=temp;
  }

  public static void main(String[] args) {
    int[] a={1,2,3,4,5};
    nextPermutation(a);
    System.out.println(Arrays.toString(a));
  }

}
