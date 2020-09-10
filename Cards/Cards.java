/*
 * クラス名:Cards
 * 作成日：2020/09/06
 * 作成者:shinkmrkan10
 * 修正日:
 * 修正者:
 * ver:1.0.0
 */
import java.util.Random;
/*   cards.java   */
/*   カードを並べる   */
public class Cards {
  public static final int SEED = 52 ;        /*   シャッフルの乱数シード   */
  public static final int OFFSET = 1 ;       /*   先頭の番号(0または1)　   */
  public static final int TOTAL = 52 ;       /*   カードの総数(花札:48)    */
  public static final int CUT = 6 ;          /*   カードをカットする枚数   */
  public static final int ROW = 4 ;          /*   表示する行数　　　　　   */
  public static final int COL = 13 ;         /*   表示する列数(花札:12)    */




/*   配列をコピーするメソッド   */

  public static void copy(int[] deck1, int[] deck2, int total)
{
    int i ;
    for( i = 0 ; i < total ; i++ ){
        deck2[i] = deck1[i];            /*   deck1からdeck2へコピー   */

    }
}



/*   配列の要素を表示するメソッド   */

  public static void display(int[] deck, int row, int col)
{
    int i, j ;
    for( i = 0 ; i < row ; i++ ){
        for( j = 0 ; j < col ; j++ ){
            System.out.print(String.format("%4d", deck[col * i + j] ));
        }
        System.out.println();

    }
}



/*   配列に番号順に格納するメソッド   */

  public static void set(int[] deck, int total, int offset)
{
    int i ;
    for( i = 0 ; i < total ; i++ ){
        deck[i] = i + offset ;
    }
}



/*   配列にランダムに格納するメソッド   */

  public static void set_random(int[] deck, int total, int offset)
{
    int i , pick ;
    int card_in_use[] = new int[100];           /*   未使用 0 で初期化   */
    Random r1 = new Random(SEED);

    for( i=0 ; i<total ; i++ ){
        do
        {
            pick = r1.nextInt(total);
        }while(card_in_use[pick] == 1);    /*   未使用を探す        */

        deck[i] = pick + offset;           /*   offsetからの数      */
        card_in_use[pick] = 1;             /*   使用済みは 1        */
    }
}



/*   配列を半分に分け交互に格納するメソッド（シャッフル）   */

  public static void shuffle(int[] deck1, int total)
{

    int i ;
    int deck2[] = new int[100];

    copy(deck1, deck2, total) ;                        /*   コピーを作る   */
    for( i = 0 ; i < total / 2 ; i++ ){
        deck1[i * 2]     = deck2[i] ;                  /*   偶数番目       */
        deck1[i * 2 + 1] = deck2[i + total / 2] ;      /*   奇数番目       */
    }
}



/*   配列をCUTずらして格納するメソッド（カット）   */
  public static void cut(int[] deck1, int total, int cut)
{

    int i ;
    int deck2[] = new int[100];

    copy(deck1, deck2, total) ;                           /*   コピーを作る   */
    for( i=0 ; i < total ; i++ ){
        deck1[i % total]   = deck2[(i + cut) % total];    /*   剰余を利用     */
    }
}


/*   メインクラス   */

  public static void main(String[] args) {
    int i ;
    int deck[] = new int[TOTAL];              /*   カード格納配列　　　   */

/*   deckに番号順に格納する   */

    set(deck, TOTAL, OFFSET);

    System.out.println();
    System.out.println("配列（整列）");
    display(deck, ROW, COL);

/*   deckを半分に分け交互に格納する（シャッフル）   */

    shuffle(deck, TOTAL);
    System.out.println();
    System.out.println("配列（シャッフル）");
    display(deck, ROW, COL);

/*   deckをCUTだけずらして格納する（カット）   */

    cut(deck, TOTAL, CUT);
    System.out.println();
    System.out.println(String.format("配列（カット　%d）", CUT));
    display(deck, ROW, COL);

/*   deckにランダムに格納する   */

//    Random r2 = new Random(SEED);

    set_random(deck, TOTAL, OFFSET);
    System.out.println();
    System.out.println(String.format("配列（ランダム　SEED=%d）", SEED));
    display(deck, ROW, COL);

/*   deckに番号順に格納する   */

    set(deck, TOTAL, OFFSET);

    System.out.println();
    System.out.println("シャッフル");
    System.out.println();
    System.out.println(String.format("%d回目",0));
    display(deck, ROW, COL);

/*   シャッフルを繰り返す   */
    for (i=0 ; i < 8 ; i++ ){
        shuffle(deck, TOTAL);
    System.out.println();
    System.out.println(String.format("%d回目",i+1));
        display(deck, ROW, COL);
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