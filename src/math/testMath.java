package math;

import java.math.BigDecimal;

/**
 * Created by ${zhangzz} on 2016/5/13.
 */
public class testMath {
    private static double test=0.531;
    public static void main(String[] args) {
    /*    ring();
        round();
        floor();
        ceil();*/
        Rint();
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
     *
     */
    private static void Rint() {
        System.out.println( new BigDecimal(Double.toString(test)).
                setScale(2, BigDecimal.ROUND_CEILING)); //2  保留2位数字
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
