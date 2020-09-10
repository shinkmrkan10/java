/*
 * クラス名:Dice
 * 作成日：2020/09/05
 * 作成者:shinkmrkan10
 * 修正日:
 * 修正者:
 * ver:1.0.0
 */
import java.util.Random;
/*
 * サイの出目を分析する
 */
public class Dice {
  public static final int SEED_ONE = 215;             /*   サイ１の乱数シード   */
  public static final int SEED_TWO = 216;             /*   サイ２の乱数シード   */
  public static final int TOTAL_NUMBER = 216*216;     /*   試技数　　　　　　   */
  public static final int COLUMN_NUMBER = 24;         /*   １列あたりの表示数   */
  public static final int ROW_NUMBER = 9;             /*   表示行数　　　　　   */
  public static final int MAX_NUMBER = 6;             /*   出目の最高値　　　   */

  public static void main(String[] args) {
    int i, j, sum, sum1, sum2 ;
    int die_one[] = new int[TOTAL_NUMBER];                   /*   サイ１の出目　　　   */
    int die_two[] = new int[TOTAL_NUMBER];                   /*   サイ２の出目　　　   */
    int even_odd[] = new int[TOTAL_NUMBER];                  /*   丁半（偶数奇数）　   */
    int cross_sum[][] = new int[MAX_NUMBER][MAX_NUMBER];     /*   出目の集計表　　　   */
    int sequence_one[][] = new int[MAX_NUMBER][MAX_NUMBER];  /*   サイ１の推移表　　   */
    int sequence_two[][] = new int[MAX_NUMBER][MAX_NUMBER];  /*   サイ２の推移表　　   */

/*   サイ1の出目を配列に格納する   */

    Random r1 = new Random(SEED_ONE);

    for( i=0 ; i<TOTAL_NUMBER ; i++ ){
        die_one[i] = r1.nextInt(6)+1;
    }

/*   サイ2の出目を配列に格納する   */

    Random r2 = new Random(SEED_TWO);

    for( i=0 ; i<TOTAL_NUMBER ; i++ ){
        die_two[i] = r2.nextInt(6)+1;
    }

/*   丁半の結果を０１で配列に格納する   */

    for( i=0 ; i<TOTAL_NUMBER ; i++ ){
        even_odd[i] = (die_one[i]+die_two[i])%2;
    }

/*   指定した行数列数までの出目を表示する   */

    System.out.println();
    System.out.println(String.format("The result of 1=>%3d", ROW_NUMBER*COLUMN_NUMBER));
    System.out.println(String.format("die1 seed=%d : die2 seed=%d", SEED_ONE, SEED_TWO ));
    System.out.println();
    for( j=0 ; j<ROW_NUMBER ; j++ ){
        System.out.println(String.format("%3d=>%3d", j*COLUMN_NUMBER+1, (j+1)*COLUMN_NUMBER));
        System.out.print("die_one :");
        for( i=0 ; i<COLUMN_NUMBER ; i++ ){
            System.out.print(String.format("%2d", die_one[j*ROW_NUMBER+i]));
        }
        System.out.println();

        System.out.print("die_two :");
        for( i=0 ; i<COLUMN_NUMBER ; i++ ){
            System.out.print(String.format("%2d", die_two[j*ROW_NUMBER+i]));
        }
        System.out.println();

        System.out.print("even/odd:");
        sum = 0;
        for( i=0 ; i<COLUMN_NUMBER ; i++ ){
            System.out.print(String.format("%2d", even_odd[j*ROW_NUMBER+i]));
            sum+=even_odd[j*ROW_NUMBER+i];
        }
        System.out.println(String.format(" : %2d/%2d", COLUMN_NUMBER-sum, sum));
        System.out.println();

   }

/*   出目の集計表を表示する   */

    System.out.println();
    System.out.println(String.format("The trial of %d", TOTAL_NUMBER));
    System.out.println(String.format("die1 seed=%d : die2 seed=%d", SEED_ONE, SEED_TWO ));
    System.out.println();

    System.out.println("The cross sum of dice");
    for( i=0 ; i < TOTAL_NUMBER ; i++ ){
        cross_sum[(die_two[i]-1)][(die_one[i]-1)]++ ;
    }

    System.out.println("   die1 |   (1)   (2)   (3)   (4)   (5)   (6)" ) ;
    System.out.println("--------+--------------------------------------" ) ;
    for ( j = 0 ; j < 6 ; j++ ){
        sum = 0 ;
        System.out.print(String.format("die2(%d) |", j + 1 )) ;
        for ( i = 0 ; i < 6 ; i++ ){
            System.out.print(String.format("%6d", cross_sum[j][i] )) ;
            sum+=cross_sum[j][i] ;
        }
        System.out.println(String.format("  :%6d", sum )) ;
    }
    System.out.println("--------+--------------------------------------" ) ;
    System.out.print("         ") ;
    for ( i = 0 ; i < 6 ; i++ ){
        sum = 0 ;
        for ( j = 0 ; j < 6 ; j++ ){
            sum+=cross_sum[j][i] ;
        }
        System.out.print(String.format("%6d", sum )) ;
    }
    System.out.println();

/*   サイ１の推移表を表示する   */

    System.out.println();
    System.out.println("The sequence of die1");
    for( i=0 ; i < TOTAL_NUMBER-1 ; i++ ){
        sequence_one[(die_one[i+1]-1)][(die_one[i]-1)]++ ;
    }

    System.out.println("   die1 |   (1)   (2)   (3)   (4)   (5)   (6)" ) ;
    System.out.println("--------+--------------------------------------" ) ;
    for ( j = 0 ; j < 6 ; j++ ){
        sum = 0 ;
        System.out.print(String.format("next(%d) |", j + 1 )) ;
        for ( i = 0 ; i < 6 ; i++ ){
            System.out.print(String.format("%6d", sequence_one[j][i] )) ;
            sum+=sequence_one[j][i] ;
        }
        System.out.println(String.format("  :%6d", sum )) ;
    }
    System.out.println("--------+--------------------------------------" ) ;
    System.out.print("         ") ;
    for ( i = 0 ; i < 6 ; i++ ){
        sum = 0 ;
        for ( j = 0 ; j < 6 ; j++ ){
            sum+=sequence_one[j][i] ;
        }
        System.out.print(String.format("%6d", sum )) ;
    }
    System.out.println();

/*   サイ２の推移表を表示する   */

    System.out.println();
    System.out.println("The sequence of die2");
    for( i=0 ; i < TOTAL_NUMBER-1 ; i++ ){
        sequence_two[(die_two[i+1]-1)][(die_two[i]-1)]++ ;
    }

    System.out.println("   die2 |   (1)   (2)   (3)   (4)   (5)   (6)" ) ;
    System.out.println("--------+--------------------------------------" ) ;
    for ( j = 0 ; j < 6 ; j++ ){
        sum = 0 ;
        System.out.print(String.format("next(%d) |", j + 1 )) ;
        for ( i = 0 ; i < 6 ; i++ ){
            System.out.print(String.format("%6d", sequence_two[j][i] )) ;
            sum+=sequence_two[j][i] ;
        }
        System.out.println(String.format("  :%6d", sum )) ;
    }
    System.out.println("--------+--------------------------------------" ) ;
    System.out.print("         ") ;
    for ( i = 0 ; i < 6 ; i++ ){
        sum = 0 ;
        for ( j = 0 ; j < 6 ; j++ ){
            sum+=sequence_two[j][i] ;
        }
        System.out.print(String.format("%6d", sum )) ;
    }
    System.out.println();

/*   ３連続の出現表を表示する   */

    System.out.println();
    System.out.println("3 sequence of die1  die2");
    for ( j = 0 ; j < 6 ; j++){
        sum1 = 0 ;
        sum2 = 0 ;
        for( i=0 ; i < TOTAL_NUMBER-2 ; i++ ){
            if ( die_one[i] == j + 1 && die_one[i+1] == j+1 && die_one[i+2] == j+1 ){
                sum1++ ;
            }
            if ( die_two[i] == j + 1 && die_two[i+1] == j+1 && die_two[i+2] == j+1 ){
                sum2++ ;
            }
        }
        System.out.println(String.format("       %d%d%d %6d%6d", j+1,j+1,j+1,sum1, sum2 ));
    }

/*   ４連続の出現表を表示する   */

    System.out.println();
    System.out.println("4 sequence of die1  die2");
    for ( j = 0 ; j < 6 ; j++){
        sum1 = 0 ;
        sum2 = 0 ;
        for( i=0 ; i < TOTAL_NUMBER-3 ; i++ ){
            if ( die_one[i] == j + 1 && die_one[i+1] == j+1 && die_one[i+2] == j+1 && die_one[i+3] == j+1 ){
                sum1++ ;
            }
            if ( die_two[i] == j + 1 && die_two[i+1] == j+1 && die_two[i+2] == j+1 && die_two[i+3] == j+1 ){
                sum2++ ;
            }
        }
        System.out.println(String.format("      %d%d%d%d %6d%6d", j+1,j+1,j+1,j+1,sum1, sum2 ));
    }

/*   ５連続の出現表を表示する   */

    System.out.println();
    System.out.println("5 sequence of die1  die2");
    for ( j = 0 ; j < 6 ; j++){
        sum1 = 0 ;
        sum2 = 0 ;
        for( i=0 ; i < TOTAL_NUMBER-4 ; i++ ){
            if ( die_one[i] == j + 1 && die_one[i+1] == j+1 && die_one[i+2] == j+1 && die_one[i+3] == j+1 && die_one[i+4] == j+1 ){
                sum1++ ;
            }
            if ( die_two[i] == j + 1 && die_two[i+1] == j+1 && die_two[i+2] == j+1 && die_two[i+3] == j+1 && die_two[i+4] == j+1 ){
                sum2++ ;
            }
        }
        System.out.println(String.format("     %d%d%d%d%d %6d%6d", j+1,j+1,j+1,j+1,j+1,sum1, sum2 ));
    }

/*   ６連続の出現表を表示する   */

    System.out.println();
    System.out.println("6 sequence of die1  die2");
    for ( j = 0 ; j < 6 ; j++){
        sum1 = 0 ;
        sum2 = 0 ;
        for( i=0 ; i < TOTAL_NUMBER-5 ; i++ ){
            if ( die_one[i] == j + 1 && die_one[i+1] == j+1 && die_one[i+2] == j+1 && die_one[i+3] == j+1 && die_one[i+4] == j+1 && die_one[i+5] == j+1 ){
                sum1++ ;
            }
            if ( die_two[i] == j + 1 && die_two[i+1] == j+1 && die_two[i+2] == j+1 && die_two[i+3] == j+1 && die_two[i+4] == j+1 && die_two[i+5] == j+1 ){
                sum2++ ;
            }
        }
        System.out.println(String.format("    %d%d%d%d%d%d %6d%6d", j+1,j+1,j+1,j+1,j+1,j+1,sum1, sum2 ));
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