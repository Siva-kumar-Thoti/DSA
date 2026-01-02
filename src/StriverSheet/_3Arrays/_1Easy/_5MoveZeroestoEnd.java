package StriverSheet._3Arrays._1Easy;

import java.util.Arrays;

public class _5MoveZeroestoEnd {
  static void moveZeroes(int[] a) {
    int x=0,k=0, n=a.length;
    for(int i=0;i<n;i++){
      while(i<n && a[i]==0)
        i++;
      if(i<n)
        a[k++]=a[i];
    }

    while(k<n){
      a[k++]=0;
    }

  }
  public static void main(String[] args) {
    int[] a = {1,2,0,1,0,4,0};
    moveZeroes(a);
    System.out.println(Arrays.toString(a));
  }

}
