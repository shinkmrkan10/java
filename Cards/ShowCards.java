/*
 * クラス名:ShowCards
 * 作成日：2020/09/06
 * 作成者:shinkmrkan10
 * 修正日:
 * 修正者:
 * ver:1.0.0
 */
/*   ShowCards.java   */
/*   ポーカーの役を判定する   */
/*   Original written in C    */
/*   Shin Kimura 2015-05-25   */

import java.util.Random;

public class ShowCards {

  public static final int SEED = 100 ;       /*   シャッフルの乱数シード    */
  public static final int OFFSET = 0 ;       /*   先頭の番号(0または1)　    */
  public static final int TOTAL = 52 ;       /*   カードの総数              */
  public static final int CUT = 6 ;          /*   カードをカットする枚数    */
  public static final int ROW = 4 ;          /*   表示する行数　　　　　    */
  public static final int COL = 13 ;         /*   表示する列数              */

/*   配列をコピーするメソッド   */

  public static void copy(final int[] deck1, final int[] deck2, final int total) {
      int i;
      for (i = 0; i < total; i++) {
          deck2[i] = deck1[i]; /* deck1からdeck2へコピー */

      }
  }

  /* カードのマークを返すメソッド */

  public static char suit(final int index) {

      final char[] mark = { 'S', 'H', 'D', 'C' };

      return mark[index / 13];
  }

  /* カードの数字を返すメソッド */

  public static char number(final int index) {

      final char[] numbers = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', '+', 'J', 'Q', 'K' };

      return numbers[index % 13];
  }

  /* 配列の要素を表示するメソッド */

  public static void display(final int[] deck, final int row, final int col) {
      int i, j;
      for (i = 0; i < row; i++) {
          for (j = 0; j < col; j++) {
              System.out.print(String.format("%4d", deck[col * i + j]));
          }
          System.out.println();

      }
  }

  /* 配列のカードを表示する関数 */

  public static void display_card(final int[] deck, final int row, final int col) {
      int i, j;
      for (i = 0; i < row; i++) {
          for (j = 0; j < col; j++) {
              System.out.print(String.format("  %c%c", suit(deck[col * i + j]), number(deck[col * i + j])));
          }
          System.out.println();

      }
  }

  /* 配列に番号順に格納するメソッド */

  public static void set(final int[] deck, final int total, final int offset) {
      int i;
      for (i = 0; i < total; i++) {
          deck[i] = i + offset;
      }
  }

  /* 配列にランダムに格納するメソッド */

  public static void set_random(final int[] deck, final int total, final int offset, final Random r1) {
      int i, pick;
      final int card_in_use[] = new int[100]; /* 未使用 0 で初期化 */

      for (i = 0; i < total; i++) {
          do {
              pick = r1.nextInt(total);
          } while (card_in_use[pick] == 1); /* 未使用を探す */

          deck[i] = pick + offset; /* offsetからの数 */
          card_in_use[pick] = 1; /* 使用済みは 1 */
      }
  }

  /* 配列を半分に分け交互に格納するメソッド（シャッフル） */

  public static void shuffle(final int[] deck1, final int total) {

      int i;
      final int deck2[] = new int[100];

      copy(deck1, deck2, total); /* コピーを作る */
      for (i = 0; i < total / 2; i++) {
          deck1[i * 2] = deck2[i]; /* 偶数番目 */
          deck1[i * 2 + 1] = deck2[i + total / 2]; /* 奇数番目 */
      }
  }

  /* 配列をCUTずらして格納するメソッド（カット） */
  public static void cut(final int[] deck1, final int total, final int cut) {

      int i;
      final int deck2[] = new int[100];

      copy(deck1, deck2, total); /* コピーを作る */
      for (i = 0; i < total; i++) {
          deck1[i % total] = deck2[(i + cut) % total]; /* 剰余を利用 */
      }
  }

  /* 手札の役を判定する関数 */

  public static void judge(final int[] hand) {
      int i, j, tmp;
      int sum, multi;

      int fl_straight = 0;
      int fl_flush = 0;

      /* sum, multiを計算 */

      sum = 0;
      multi = 1;
      for (i = 0; i < 5; i++) {
          sum += hand[i] / 13;
          multi *= hand[i] / 13;
      }

      /* 手札がフラッシュであるか？ */

      if (sum == 0 || sum == 15)
          fl_flush = 1;
      else if (sum == 5 && multi == 1)
          fl_flush = 1;
      else if (sum == 10 && multi == 32)
          fl_flush = 1;
      else
          fl_flush = 0;

      /* 手札を数でソート */

      for (i = 0; i < 4; i++) {
          for (j = 4; j > i; j--) {
              if (hand[j - 1] % 13 > hand[j] % 13) {
                  tmp = hand[j];
                  hand[j] = hand[j - 1];
                  hand[j - 1] = tmp;
              }
          }
      }

      /* 手札を数の順で表示 */

      System.out.println();
      System.out.println("手札整列");
      for (i = 0; i < 5; i++) {
          System.out.print(String.format("  %c%c", suit(hand[i]), number(hand[i])));
      }

      /* 手札がストレートであるか？ */

      if (hand[4] % 13 - hand[0] % 13 == 4) {
          if (hand[1] % 13 == hand[0] % 13 + 1 && hand[2] % 13 == hand[1] % 13 + 1 && hand[3] % 13 == hand[2] % 13 + 1)
              fl_straight = 1;
      } else if (hand[0] % 13 == 0 && hand[1] % 13 == 9 && hand[2] % 13 == 10 && hand[3] % 13 == 11
              && hand[4] % 13 == 12)
          fl_straight = 1;
      else
          fl_straight = 0;

      /* ストレートフラッシュの判定 */

      if (fl_flush == 1 && fl_straight == 1)
          System.out.println("　ストレートフラッシュです。");
      else if (fl_flush == 1)
          System.out.println("　フラッシュです。");
      else if (fl_straight == 1)
          System.out.println("　ストレートです。");

      /* フォーカードの判定 */

      else if (hand[0] % 13 == hand[3] % 13 || hand[1] % 13 == hand[4] % 13)
          System.out.println("　フォーカードです。");

      /* フルハウスの判定 */

      else if ((hand[0] % 13 == hand[2] % 13 && hand[3] % 13 == hand[4] % 13)
              || (hand[0] % 13 == hand[1] % 13 && hand[2] % 13 == hand[4] % 13))
          System.out.println("　フルハウスです。");

      /* スリーカードの判定 */

      else if (hand[0] % 13 == hand[2] % 13 || hand[1] % 13 == hand[3] % 13 || hand[2] % 13 == hand[4] % 13)
          System.out.println("　スリーカードです。");

      /* ツーペアの判定 */

      else if ((hand[0] % 13 == hand[1] % 13 && hand[2] % 13 == hand[3] % 13)
              || (hand[0] % 13 == hand[1] % 13 && hand[3] % 13 == hand[4] % 13)
              || (hand[1] % 13 == hand[2] % 13 && hand[3] % 13 == hand[4] % 13))
          System.out.println("　ツーペアです。");

      /* ワンペアの判定 */

      else if (hand[0] % 13 == hand[1] % 13 || hand[1] % 13 == hand[2] % 13 || hand[2] % 13 == hand[3] % 13
              || hand[3] % 13 == hand[4] % 13)
          System.out.println("　ワンペアです。");

      else
          System.out.println("　ノーペアです。");

  }

  /* メインクラス */

  public static void main(final String[] args) {
      int i, j, k;

//      final int fl_straight = 0;
//      final int fl_flush = 0;

      final int deck[] = new int[TOTAL]; /* カード格納配列 */
      final int hand[] = new int[5]; /* 手札格納配列 */

      /* deckに番号順に格納する */

      set(deck, TOTAL, OFFSET);

      System.out.println();
      System.out.println("配列（整列）");
      display_card(deck, ROW, COL);

      /* deckを半分に分け交互に格納する（シャッフル） */

      shuffle(deck, TOTAL);
      System.out.println();
      System.out.println("配列（シャッフル）");
      display_card(deck, ROW, COL);

      /* deckをCUTだけずらして格納する（カット） */

      cut(deck, TOTAL, CUT);
      System.out.println();
      System.out.println(String.format("配列（カット　%d）", CUT));
      display_card(deck, ROW, COL);

      /* deckにランダムに格納する */

      final Random r1 = new Random(SEED);


/*   5回繰り返す   */

    for ( k = 0 ; k < 5 ; k++ )
    {
        System.out.println();
        System.out.println(String.format("new deck %d", k+1));

   set_random(deck, TOTAL, OFFSET,r1);

    System.out.println();
    System.out.println(String.format("配列（ランダム　SEED=%d）", SEED));
    display(deck, ROW, COL);

/*   5枚ずつ10回配る   */

    for ( j = 0 ; j < 10 ; j++ )
    {


/*   手札を配る   */

    for (i=0 ; i < 5 ; i++ ){
        hand[i] = deck[i + j * 5] ;
     }

/*   handを固定して役の判定を検査する場合   

     hand[0]= 8;
     hand[1]= 11;
     hand[2]= 25;
     hand[3]= 9;
     hand[4]= 49;
*/

/*   手札を表示   */

    System.out.println();
    System.out.println("手札");
    for (i=0 ; i < 5 ; i++ ){
        System.out.print(String.format("  %c%c",suit(hand[i]), number(hand[i])));
     }

/*   手札の役を表示   */

    judge( hand ) ;

     }
  }
  }
}

/*


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

*/