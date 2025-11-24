package StriverSheet._5Recursion;

public class printNtimes {
  static void display(int n, String x){
    System.out.println(x);
    if(n>0)
    display(n-1,x);
  }

  public static void main(String[] args) {
    int x=50;
    display(x,"Siva Pawanism");
  }

}
