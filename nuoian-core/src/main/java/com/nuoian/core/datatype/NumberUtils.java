package com.nuoian.core.datatype;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:04
 * @Description: 数字工具
 * @Package: com.nuoian.core.datatype
 * @Version: 1.0
 */
public class NumberUtils {

    /**
     * 功能描述:
     * 〈比较 两个 BigDecimal 是否相等〉
     *
     * @param bigNum1 1
     * @param bigNum2 2
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/4/8 17:45
     */
    public static Boolean equals(BigDecimal bigNum1, BigDecimal bigNum2) {
        if (bigNum1.equals(bigNum2)) {
            return true;
        } else if (bigNum1 != null && bigNum2 != null) {
            return 0 == bigNum1.compareTo(bigNum2);
        } else {
            return false;
        }
    }


    /**
     * 功能描述:
     * 〈判断对象是否为数字〉
     *
     * @param obj 对象
     * @return: java.lang.Boolean
     * @author: 吴宇森
     * @date: 2022/2/16 18:50
     */
    public static Boolean isNumber(Object obj) {
        if (null == obj) {
            return false;
        } else {
            String str = obj.toString();
            return str.matches("-?[0-9]+.*[0-9]*");
        }
    }

    /**
     * 功能描述:
     * 〈对象转Integer〉
     *
     * @param obj 对象
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 18:52
     */
    public static Integer parseInteger(Object obj) {
        return parseInteger(obj, false);
    }

    /**
     * 功能描述:
     * 〈对象转Integer〉
     *
     * @param obj    对象
     * @param isNull true 为空返回null false 为空返回0
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 18:52
     */
    public static Integer parseInteger(Object obj, Boolean isNull) {
        if (!StringUtils.isNull(obj) && isNumber(obj)) {
            return Integer.valueOf(obj.toString());
        }
        if (isNull) {
            return null;
        } else {
            return 0;
        }
    }

    /**
     * 功能描述:
     * 〈对象转Float〉
     *
     * @param obj 对象
     * @return: java.lang.Float
     * @author: 吴宇森
     * @date: 2022/2/16 18:58
     */
    public static Float parseFloat(Object obj) {
        return parseFloat(obj, false);
    }

    /**
     * 功能描述:
     * 〈对象转Float〉
     *
     * @param obj    对象
     * @param isNull true 为空返回null false 为空返回0.0F
     * @return: java.lang.Float
     * @author: 吴宇森
     * @date: 2022/2/16 18:58
     */
    public static Float parseFloat(Object obj, Boolean isNull) {
        if (!StringUtils.isNull(obj) && isNumber(obj)) {
            return Float.valueOf(obj.toString());
        }
        if (isNull) {
            return null;
        } else {
            return 0.0F;
        }
    }

    /**
     * 功能描述:
     * 〈对象转Double〉
     *
     * @param obj 对象
     * @return: java.lang.Double
     * @author: 吴宇森
     * @date: 2022/2/16 18:58
     */
    public static Double parseDouble(Object obj) {
        return parseDouble(obj, false);
    }

    /**
     * 功能描述:
     * 〈对象转Double〉
     *
     * @param obj    对象
     * @param isNull true 为空返回null false 为空返回0.0D
     * @return: java.lang.Double
     * @author: 吴宇森
     * @date: 2022/2/16 18:58
     */
    public static Double parseDouble(Object obj, Boolean isNull) {
        if (!StringUtils.isNull(obj) && isNumber(obj)) {
            return Double.valueOf(obj.toString());
        }
        if (isNull) {
            return null;
        } else {
            return 0.0D;
        }
    }

    /**
     * 功能描述:
     * 〈对象转Long〉
     *
     * @param obj 对象
     * @return: java.lang.Long
     * @author: 吴宇森
     * @date: 2022/2/16 18:59
     */
    public static Long parseLong(Object obj) {
        return parseLong(obj, false);
    }

    /**
     * 功能描述:
     * 〈对象转Long〉
     *
     * @param obj    对象
     * @param isNull true 为空返回null false 为空返回0L
     * @return: java.lang.Long
     * @author: 吴宇森
     * @date: 2022/2/16 18:59
     */
    public static Long parseLong(Object obj, Boolean isNull) {
        if (!StringUtils.isNull(obj) && isNumber(obj)) {
            return Long.valueOf(obj.toString());
        }
        if (isNull) {
            return null;
        } else {
            return 0L;
        }
    }

    /**
     * 功能描述:
     * 〈BigDecimal四舍五入处理〉
     *
     * @param numb  需要处理的数据
     * @param digit 小数保留位数
     * @param type  四舍五入 true:向上舍 false:向下舍
     * @return: java.math.BigDecimal
     * @author: 吴宇森
     * @date: 2022/2/16 19:02
     */
    public static BigDecimal parseBigDecimal(BigDecimal numb, int digit, boolean type) {
        BigDecimal setScale = numb;
        if (!StringUtils.isNull(numb)) {
            if (type) {
                setScale = numb.setScale(digit, BigDecimal.ROUND_HALF_UP);
            } else {
                setScale = numb.setScale(digit, BigDecimal.ROUND_HALF_DOWN);
            }
        }
        return setScale;
    }

    /**
     * 功能描述:
     * 〈浮点相加〉
     *
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleAdding(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相减〉
     *
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleSubtract(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相乘〉
     *
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleMultiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相除〉
     *
     * @param d1    double参数1
     * @param d2    double参数2
     * @param digit 小数位
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleDivide(double d1, double d2, int digit) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.divide(bd2, digit, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 功能描述:
     * 〈计算同比、环比〉
     *
     * @param front 被比数
     * @param after 比数
     * @param scale 结果小数位
     * @return: double
     * @author: 吴宇森
     * @date: 2022/9/8 10:30
     */
    public static double calculateYearOnYear(BigDecimal front, BigDecimal after, int scale) {
        return (after.subtract(front)).divide(front, scale + 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
    }

    /**
     * @param number: 数量
     * @param total:  总数
     * @param digit:  保留的位数
     * @Description: 获取占比
     * @Author: WangChangMing
     * @Date: 2021/4/2 17:36
     * @return: java.lang.String
     **/
    public static String getPercentage(double number, double total, int digit) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后X位
        numberFormat.setMaximumFractionDigits(digit);
        return numberFormat.format(number / total * 100);
    }

    /**
     * 功能描述:
     * 〈获取环比提升率〉
     *
     * @param thisNum 本次数值
     * @param lastNum 上次数值
     * @Author: WangChangMing
     * @Date: 2021/11/15 11:29
     * @return: java.lang.String
     **/
    public static String getChainRatio(double thisNum, double lastNum) {
        return lastNum == 0 ? "0" : String.format("%.2f", (thisNum / lastNum - 1) * 100);
    }

    /**
     * 功能描述:
     * 〈
     * 计算QPS
     * 原理：每天80%的访问集中在20%的时间里，这20%时间叫做峰值时间
     * 计算：(总数 * 80%) / (秒数 * 20%) = 峰值时间每秒请求数(QPS)
     * 〉
     *
     * @param total   总数
     * @param seconds 秒数
     * @Author: WangChangMing
     * @Date: 2022/7/26 14:56
     * @return: long
     **/
    public static String getQps(long total, long seconds) {
        return String.format("%.2f", (total * 0.8) / (seconds * 0.2));
    }

    /**
     * 获取指定范围的随机数
     *
     * @param max 最大值
     * @return
     */
    public static Integer scopeRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * 功能描述:
     * 〈获取定长的随机数集合〉
     * @param length 定长
     * @param max 随机数最大值
     * @return: java.util.List<java.lang.Integer>
     * @author: LeiZiLin
     * @date: 2023/1/11 15:59
     */
    public static List<Integer> fixedLengthRandomGather(int length,int max){
        List<Integer> gather = new ArrayList<>();
        for(int i = 0;i < length;i++){
            gather.add(scopeRandomNumber(max));
        }
        return gather;
    }

    /**
     * 功能描述:
     * 〈判断Integer是否不为null〉
     *
     * @param integer Integer
     * @Author: WangChangMing
     * @Date: 2023/3/7 10:12
     * @return: boolean
     **/
    public static boolean isNotNull(Integer integer) {
        return integer != null;
    }
}
