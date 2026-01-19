package StriverSheet._3Arrays._2Medium;

public class _4MaxSumSubarrayKadane {

  public static int maxSubArray(int[] a) {
    int s=0,ms=Integer.MIN_VALUE;
    for(int i:a){
      s=Math.max(i,s+i);
      ms=Math.max(ms,s);
    }
    return ms;
  }

  public static void main(String[] args) {
    maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4});
  }

}
