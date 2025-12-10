package StriverSheet._2Sorting;

import java.util.Arrays;

public class SelectionSort {

  public static void main(String[] args) {
    int[] a = {64, 25, 12, 22, 11};
    int n = a.length, mini = a[0];
    for (int i = 0; i < n; i++) {
      int idx = i;
      for (int j = i + 1; j < n; j++) {
        if (a[idx] > a[j]) {
          idx = j;
        }
      }
      int temp = a[i];
      a[i] = a[idx];
      a[idx] = temp;
    }

    System.out.println(Arrays.toString(a));
  }

}
