package StriverSheet._2Sorting;

public class BubbleSort {

  public static void main(String[] args) {
    int[] a = {64, 25, 12, 22, 11};
    int n = a.length;
    int count = n - 1;
    while (count > 0) {
      for (int i = 0; i < n - 1; i++) {
        if (a[i] > a[i + 1]) {
          int temp = a[i];
          a[i] = a[i + 1];
          a[i + 1] = temp;
        }
      }
      count--;
    }
    for (int i = 0; i < n; i++) {
      System.out.println(a[i]);
    }
  }

}
