package LearningJava;

import java.util.HashMap;
import java.util.Map;

public class DifferentMapIntialization {

  public static void main(String[] args) {

    Map<Integer,Integer> mp=new HashMap<>();

    //map cant have generics like Map<int,int>
    // because <k,v> generics cant take primitive data types as input

    mp.put(1,10);
    mp.put(2,20);

    for(Map.Entry<Integer,Integer> x:mp.entrySet()){
      System.out.println(x.getKey());
      System.out.println(x.getValue());
    }

    for(Integer x:mp.keySet()){
      System.out.println(x);
    }

    for(Integer x:mp.values()){
      System.out.println(x);
    }

    mp.forEach((k,v)->{
      System.out.println(k);
      System.out.println(v);
    }); //note we can use only and only final variables in lamda expressions
    //suppose you cant declare a mini/maxi outside the lamda and then
    //call this method like Math.min(mini,x);
  }

}
