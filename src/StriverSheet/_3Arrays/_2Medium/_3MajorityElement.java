package StriverSheet._3Arrays._2Medium;

import java.util.ArrayList;
import java.util.List;

public class _3MajorityElement {

  public static int majorityElement(int[] a) {
    int cnt=1,x=a[0],n=a.length;

    for(int i=1;i<n;i++){
      if(a[i]==x) cnt++;
      else cnt--;

      if(cnt>(n/2))
        return x;
      else if(cnt==0){
        cnt=1;
        x=a[i];
      }
    }

    return x;
  }

  public static List<Integer> majorityElement2(int[] a) {
    int c1 = 0, c2 = 0, x = 0, y = 1;
    int n = a.length;
    List<Integer> res = new ArrayList<>();

    for (int i : a) {
      if (i == x) {
        c1++;
      } else if (i == y) {
        c2++;
      } else if (c1 == 0) {
        x = i;
        c1 = 1;
      } else if (c2 == 0) {
        y = i;
        c2 = 1;
      } else {
        c1--;
        c2--;
      }
    }

    // verify
    c1 = 0;
    c2 = 0;
    for (int i : a) {
      if (i == x) c1++;
      else if (i == y) c2++;
    }

    if (c1 > n / 3) res.add(x);
    if (c2 > n / 3) res.add(y);

    return res;
  }

  public static void main(String[] args) {
    int[] a={2,0,2,1,1,};
    majorityElement(a);
    majorityElement2(a);
  }

}
