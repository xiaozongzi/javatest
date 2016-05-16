package math;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by ${zhangzz} on 2016/5/13.
 */
public class testMath {
    private static double test=0.51600;
    public static void main(String[] args) {
    /*    ring();
        round();
        floor();
        ceil();*/
//        Rint();
//        testBig();
        testNumFormat();
    }

    private static void testNumFormat() {
        NumberFormat format=NumberFormat.getCurrencyInstance(Locale.CHINA);
        System.out.println(format.format(test));
    }

    public static final void testBig(){
        BigDecimal bigDecimal=new BigDecimal(0.2+"");
//        System.out.println(bigDecimal.abs());//绝对值
//        System.out.println(bigDecimal.movePointLeft(2));//向左移动2为的数
//        System.out.println(bigDecimal.movePointRight(2));//向右移动2为的小数

    }
    /**
     * 四舍五入 正确用法 new BigDecimal 需要把Double转string
     * {@link BigDecimal.ROUND_HALF_EVEN} 如果四舍五入的 那个数字刚好是‘5’ 并且的左边是偶数则不做四舍五入的处理
     * 如果左边的是奇数则要做四舍五入的处理 （四舍六入，逢五奇进偶舍）
     * {@link BigDecimal.ROUND_HALF_DOWN}  逢五 舍去
     * {@link BigDecimal.ROUND_HALF_UP}  逢五 进一
     * {@link BigDecimal.ROUND_FLOOR}  BigDecimal   为正，则作   ROUND_DOWN      ；如果为负，则作   ROUND_UP
     * {@link BigDecimal.ROUND_CEILING} BigDecimal   为正，则作   ROUND_UP      ；如果为负，则作   ROUND_DOWN
     * {@link BigDecimal.ROUND_UP}  绝对值 向上取整
     * {@link BigDecimal.ROUND_DOWN}绝对值 向下取整
     * {@link BigDecimal.ROUND_UNNECESSARY} 保留的小数尾数和原数字 一样 如果要缩小 必须得保证后面都是0
     *
     */
    private static void Rint() {
        System.out.println( new BigDecimal(Double.toString(test)).
                setScale(2, BigDecimal.ROUND_UNNECESSARY)); //2  保留2位数字
        System.out.println( new BigDecimal(Double.toString(test)).
                setScale(2, BigDecimal.ROUND_UP));
        System.out.println( new BigDecimal(Double.toString(test)).
                setScale(2, BigDecimal.ROUND_DOWN));


    }

    /**
     * 向上取整
     */
    private static void ceil() {
        System.out.println(Math.ceil(test));
    }

    /**
     * 向下取整
     * */
    private static void floor( ) {
        System.out.println(Math.floor(test));

    }

    /**
     * 最接近的long值
     */
    private static void round() {
        System.out.println(Math.round(test));
    }

    /**
     * 四舍五入  不准确 .5的时候
     */
    private static void ring() {
        System.out.println(Math.rint(test));
    }

}
