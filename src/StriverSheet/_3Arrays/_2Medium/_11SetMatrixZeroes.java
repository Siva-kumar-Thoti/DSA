package StriverSheet._3Arrays._2Medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _11SetMatrixZeroes {
  public static void setZeroes(int[][] a) {
    int m = a.length, n = a[0].length;
    Set<Integer> x = new HashSet<>();
    Set<Integer> y = new HashSet<>();

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (a[i][j] == 0) {
          x.add(i);
          y.add(j);
        }
      }
    }

    for (int i = 0; i < m; i++) {
      if (x.contains(i)) {
        for (int j = 0; j < n; j++)
          a[i][j] = 0;
      }
    }

    for (int j = 0; j < n; j++) {
      if (y.contains(j)) {
        for (int i = 0; i < m; i++)
          a[i][j] = 0;
      }
    }
  }

  public static void main(String[] args) {
    int[][] a= {{1,2,3},{4,0,6},{7,8,9}};
    setZeroes(a);
    for(int i=0; i<a.length; i++){
      System.out.println(Arrays.toString(a[i]));
    }
  }

}
