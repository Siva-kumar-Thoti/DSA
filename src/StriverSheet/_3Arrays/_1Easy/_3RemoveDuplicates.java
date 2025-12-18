package StriverSheet._3Arrays._1Easy;

public class _3RemoveDuplicates {

  public static void main(String[] args) {
    int[] a = {1,1,2,2,3,3,4};
    int k=1,n=a.length;
    for(int i=1;i<n;i++){
      while(i<n && a[i]==a[i-1])
        i++;
      if(i<n)
        a[k++]=a[i];
    }
    System.out.println(k);
  }

}
