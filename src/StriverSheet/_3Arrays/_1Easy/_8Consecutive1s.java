package StriverSheet._3Arrays._1Easy;

public class _8Consecutive1s {

   static int findMaxConsecutiveOnes(int[] a) {
    int cnt=0,max=0,n=a.length;

    for(int i=0;i<n;i++){
      while(i<n && a[i]==1){
        cnt++;
        i++;
      }

      max=Math.max(max,cnt);
      cnt=0;
    }

    return max;

  }
  public static void main(String[] args) {
    int a[]={1,1,0,1,1,1};
    System.out.println(findMaxConsecutiveOnes(a));
  }

}
