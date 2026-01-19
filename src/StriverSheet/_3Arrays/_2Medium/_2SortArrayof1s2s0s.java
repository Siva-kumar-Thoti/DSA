package StriverSheet._3Arrays._2Medium;

public class _2SortArrayof1s2s0s {
  public static void sortColors(int[] a) {
    int n=a.length;
    int x=0,y=0,z=0;
    for(int i:a){
      if(i==0) x++;
      else if(i==1) y++;
      else z++;
    }

    for(int i=0;i<n;i++){
      if(i<x)
        a[i]=0;
      else if(i<x+y) a[i]=1;
      else a[i]=2;
    }

  }
  public static void main(String[] args) {
    int[] a={2,0,2,1,1,};
    sortColors(a);
  }

}
