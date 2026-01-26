package StriverSheet._3Arrays._2Medium;

public class _12RotateMatrixby90 {

  public static void rotate(int[][] a) {
    int n = a.length;
    if (n < 2) return;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        int x = a[i][j];
        a[i][j] = a[j][i];
        a[j][i] = x;
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n / 2; j++) {
        int x = a[i][j];
        a[i][j] = a[i][n - 1 - j];
        a[i][n - 1 - j] = x;
      }
    }
  }

  public static void main(String[] args) {
    int[][] a = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    rotate(a);

    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[0].length; j++) {
        System.out.print(a[i][j] + " ");
      }
      System.out.println();
    }
  }

}
