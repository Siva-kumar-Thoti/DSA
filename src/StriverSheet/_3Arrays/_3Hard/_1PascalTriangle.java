package StriverSheet._3Arrays._3Hard;

import java.util.ArrayList;
import java.util.List;

public class _1PascalTriangle {

  static List<List<Integer>> generate(int n) {
    List<List<Integer>> res = new ArrayList<>();
    if (n <= 0) return res;

    for (int i = 0; i < n; i++) {
      List<Integer> row = new ArrayList<>(i + 1);
      row.add(1);

      for (int j = 1; j < i; j++) {
        row.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
      }

      if (i > 0) row.add(1);
      res.add(row);
    }

    return res;
  }

  public static void main(String[] args) {
    int n = 5;
    List<List<Integer>> ans = generate(n);

    for (List<Integer> row : ans) {
      System.out.println(row);
    }
  }

}
