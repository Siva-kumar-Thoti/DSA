package StriverSheet._3Arrays._1Easy;

public class _2SecondLargestElement {

  public static void main(String[] args) {
    int[] a={10,5,10,8};
    int n=a.length;

    int max1=Integer.MIN_VALUE,max2=Integer.MIN_VALUE;

    for(int i=0;i<n;i++){
      if(a[i]>max1){
        max2=max1;
        max1=a[i];
      } else if(a[i]>max2 && a[i]!=max1)
        max2=a[i];
    }
    if (max2 == Integer.MIN_VALUE) {
      System.out.println("No 2nd largest element found");
    }
    System.out.println("2nd largest element: "+max2);
  }

}
