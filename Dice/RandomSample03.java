import java.util.ArrayList;
import java.util.Collections;
/**
 * 重複しない0～9の乱数を作成するサンプル
 *
 */
public class RandomSample03 {
  public static void main(String[] args) {
    // 0 ～ 9 の数値が入ったリストを作成
    ArrayList<Integer> list = new ArrayList<Integer>();
    for ( int i = 0; i < 10; i++ ) {
      list.add(i);
    }
    // リストの内容をシャッフルします。（１回目）
    Collections.shuffle(list);
    for( Integer num : list ) {
      System.out.print(num + " ");
    }
    System.out.println();
    
    // リストの内容をシャッフルします。（２回目）
    Collections.shuffle(list);
    for( Integer num : list ) {
      System.out.print(num + " ");
    }
  }
}