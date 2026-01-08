package StriverSheet._3Arrays._1Easy;

import java.util.HashMap;

public class _10LongestSubarrayWithSumK {
   static int longestSubarray(int[] a, int k) {
    int res=0,n=a.length,sum=0;
    HashMap<Integer,Integer> mp=new HashMap<>();
    mp.put(0,-1);
    for(int i=0;i<n;i++){
      sum+=a[i];
      if(mp.containsKey(sum-k)){
        res=Math.max(res,i-mp.get(sum-k));
      }

      if(!mp.containsKey(sum)){
        mp.put(sum,i);
      }
    }

    return res;
  }

  static  int longestSubarrayOnlyPositive(int[] a, int k) {
     int res=0,n=a.length,sum=0;

     int l=0,r=0;

     while(r<n){
       sum+=a[r];
       if(sum==k){
         res=Math.max(res,r-l+1);
       }
       else if(sum>k){
         while(sum>k && l<=r){
           sum-=a[l++];
         }
       }
       r++;
     }

     return res;
  }

  public static void main(String[] args) {
    int[] a={10, 5, 2, 7, 1};
    System.out.println(longestSubarrayOnlyPositive(a,15));
  }

}
