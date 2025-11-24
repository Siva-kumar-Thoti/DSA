package StriverSheet._6Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class frequency {

  public static void main(String[] args) {
    int[] a = {2, 2, 3,3, 5, 5, 6,6,7, 7, 5, 5};
    System.out.println("difference in high-low frequency: " + diffFrequency3(a));
  }

  static int diffFrequency(int[] a) {
    int n = a.length, mini = Integer.MAX_VALUE, maxi = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int count = 0;
      for (int j = 0; j < n; j++) {
        if (a[i] == a[j]) {
          count++;
        }
      }
      mini = Math.min(mini, count);
      maxi = Math.max(maxi, count);
    }

    return maxi - mini;
  }


  static int diffFrequency2(int[] a) {
    int n = a.length,count=1,mini=Integer.MAX_VALUE,maxi=Integer.MIN_VALUE;
    Arrays.sort(a);

    for(int i=1;i<n;i++){
      count=1;
      while(i<n && a[i]==a[i-1]){
        count++;
        i++;
      }

      maxi=Math.max(maxi,count);
      mini=Math.min(mini,count);
    }

    System.out.println(maxi);
    System.out.println(mini);
    return maxi-mini;
  }

  static int diffFrequency3(int[] a) {
    int n = a.length,count=1,mini=Integer.MAX_VALUE,maxi=Integer.MIN_VALUE;
    Map<Integer,Integer> mp=new HashMap<>();

    for(int x:a){
      mp.put(x, mp.getOrDefault(x,0)+1);
    }

    for(Map.Entry<Integer, Integer> x:mp.entrySet()){
      mini=Math.min(mini,x.getValue());
      maxi=Math.max(maxi,x.getValue());
    }



    System.out.println(maxi);
    System.out.println(mini);
    return maxi-mini;
  }


}


