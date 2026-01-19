package StriverSheet._3Arrays._2Medium;

import java.util.*;

public class _1TwoSum {

  public static int[] twoSum(int[] a, int x) {
    int n=a.length;
    Map<Integer,Integer> mp=new HashMap<>();
    for(int i=0;i<n;i++){
      if(mp.containsKey(x-a[i]))
        return new int[]{mp.get(x-a[i]),i};
      else mp.put(a[i],i);
    }
    return new int[]{};
  }

  public static void main(String[] args) {
    int[] a={2,7,11,15};
    System.out.println(twoSum(a,9));
  }

}
