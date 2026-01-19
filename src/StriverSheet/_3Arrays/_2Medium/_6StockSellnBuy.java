package StriverSheet._3Arrays._2Medium;

public class _6StockSellnBuy {
  public static int maxProfit(int[] a) {
    int mini=Integer.MAX_VALUE,res=0;
    for(int x:a){
      mini=Math.min(x,mini);
      res=Math.max(res,x-mini);
    }
    return res;
  }

  public static void main(String[] args) {
    System.out.println(maxProfit(new int[]{1,2,3,4,5}));
  }

}
