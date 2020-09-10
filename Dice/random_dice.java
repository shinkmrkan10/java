import java.util.Random;

public class random_dice{
  public static void main(String[] args) {
    String[] dice = { "1", "2", "3", "4", "5", "6" };
    Random r = new Random();
    for(int i=0 ; i<5 ; i++) {
      String die = dice[r.nextInt(6)];
      System.out.println(die);
    }
  }
}