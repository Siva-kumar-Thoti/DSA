package StriverSheet._3Arrays._2Medium;

public class _7RearrangeArray {
  public static int[] rearrangeArray(int[] a) {
    int n=a.length,i=0,j=1;
    int[] b=new int[n];

    for(int x:a){
      if(x>0){
        b[i]=x;
        i+=2;
      } else{
        b[j]=x;
        j+=2;
      }
    }

    return b;

  }

  public static void main(String[] args) {
    rearrangeArray(new int[]{3,1,-2,-5,2,-4});
  }

}
