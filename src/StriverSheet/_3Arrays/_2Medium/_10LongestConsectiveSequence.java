package StriverSheet._3Arrays._2Medium;

import java.util.HashSet;
import java.util.Set;

public class _10LongestConsectiveSequence {
  public static int longestConsecutive(int[] a) {
    if (a == null || a.length == 0) return 0;

    Set<Integer> st = new HashSet<>();
    for (int v : a) st.add(v);

    int maxi = 1;
    for (int x : st) {
      if (!st.contains(x - 1)) {
        int len = 1;
        int cur = x;
        while (st.contains(cur + 1)) {
          cur++;
          len++;
        }
        maxi = Math.max(maxi, len);
      }
    }
    return maxi;
  }

  public static void main(String[] args) {
    int[] arr = {100, 4, 200, 1, 3, 2};
    System.out.println(longestConsecutive(arr));
  }

}
