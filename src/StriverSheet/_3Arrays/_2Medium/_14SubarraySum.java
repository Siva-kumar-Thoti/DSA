package StriverSheet._3Arrays._2Medium;

import java.util.HashMap;
import java.util.Map;

public class _14SubarraySum {

  public static int subarraySum(int[] a, int k) {
    int n = a.length, res = 0;
    int sum = 0;
    Map<Integer, Integer> mp = new HashMap<>();
    mp.put(0, 1);
    for (int i = 0; i < n; i++) {
      sum += a[i];
      res += mp.getOrDefault(sum - k, 0);
      mp.put(sum, mp.getOrDefault(sum, 0) + 1);
    }
    return res;
  }

  public static void main(String[] args) {
    int[] a1 = {1, 1, 1};
    System.out.println(subarraySum(a1, 2)); // expected 2

    int[] a2 = {1, 2, 3};
    System.out.println(subarraySum(a2, 3)); // expected 2
  }

}
