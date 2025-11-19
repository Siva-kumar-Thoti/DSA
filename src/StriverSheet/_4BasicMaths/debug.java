package StriverSheet._4BasicMaths;

import java.util.HashMap;
import java.util.Map;

class debug {
  public static void main(String[] args) {
    Map<String, String[]> mp = new HashMap<>();
    String key = "3";
    String val = "premium";

    mp.put("1", new String[]{"siva", "kumar"});
    mp.put("2", new String[]{"java", "code"});

    mp.computeIfAbsent(key, k -> new String[]{ val });

    System.out.println(mp);
  }
}