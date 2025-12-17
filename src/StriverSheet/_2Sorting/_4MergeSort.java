package StriverSheet._2Sorting;

import java.util.Arrays;

public class _4MergeSort {

  public static void main(String[] args) {
    int[] a = {64, 25, 12, 22, 11};
    int n = a.length;
    mergeSort(a,0,n-1);
    System.out.println(Arrays.toString(a));
  }

  private static void mergeSort(int[] a, int l, int r) {
    if(l<r){
      int mid=l+(r-l)/2;
      mergeSort(a,l,mid);
      mergeSort(a,mid+1,r);

      merge(a,l,mid,r);
    }
  }

  private static void merge(int[] a, int l, int mid, int r) {
    int m=mid-l+1;
    int n=r-mid;

    int[] x=new int[m];
    int[] y=new int[n];

    for(int i=0;i<m;i++){
      x[i]=a[l+i];
    }

    for(int j=0;j<n;j++){
      y[j]=a[mid+1+j];
    }

    int i,j,k;
    i=0;j=0;k=l;

    while(i<m && j<n){
      if(x[i]<y[j]){
        a[k++]=x[i++];
      } else{
        a[k++]=y[j++];
      }
    }

    while(i<m){
      a[k++]=x[i++];
    }

    while(j<n){
      a[k++]=y[j++];
    }
  }

}
