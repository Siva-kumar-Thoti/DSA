package StriverSheet._3Arrays._2Medium;

import java.util.ArrayList;
import java.util.List;

public class _13SpiralMatrix {
  public static List<Integer> spiralOrder(int[][] a) {
    int m = a.length, n = a[0].length;
    int l = 0, r = n - 1, t = 0, b = m - 1;
    List<Integer> res = new ArrayList<>();

    while (l <= r && t <= b) {
      for (int j = l; j <= r; j++) {
        res.add(a[t][j]);
      }
      t++;

      if (l <= r && t <= b) {
        for (int i = t; i <= b; i++) {
          res.add(a[i][r]);
        }
        r--;
      }

      if (l <= r && t <= b) {
        for (int j = r; j >= l; j--) {
          res.add(a[b][j]);
        }
        b--;
      }

      if (l <= r && t <= b) {
        for (int i = b; i >= t; i--) {
          res.add(a[i][l]);
        }
        l++;
      }
    }

    return res;
  }

  public static void main(String[] args) {
    int[][] a = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    List<Integer> ans = spiralOrder(a);
    for (int x : ans) {
      System.out.print(x + " ");
    }
  }
}
