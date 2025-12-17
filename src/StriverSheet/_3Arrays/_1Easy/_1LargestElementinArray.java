package StriverSheet._3Arrays._1Easy;

import java.util.Arrays;

public class _1LargestElementinArray {

  public static int findLargest(int[] nums) {
    return Arrays.stream(nums)
        .max()
        .orElse(Integer.MIN_VALUE);
  }

  public static void main(String[] args) {
    int[] nums = {};
    System.out.println("Largest: " + findLargest(nums));
  }

}
