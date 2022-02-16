package com.nuoian.tools.datatype;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/16 18:50
 * @Description: 数字工具
 * @Package: com.nuoian.tools.datatype
 * @Version: 1.0
 */
public class NumberUtil {

    /**
     * 功能描述:
     * 〈判断对象是否为数字〉
     * @param obj 对象
     * @return: java.lang.Boolean
     * @author: 吴宇森
     * @date: 2022/2/16 18:50
     */
    public static Boolean isNumber(Object obj){
        if(null == obj){
            return false;
        }else{
            String str = obj.toString();
            return str.matches("-?[0-9]+.*[0-9]*");
        }
    }

    /**
     * 功能描述:
     * 〈对象转Integer〉
     * @param obj 对象
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 18:52
     */
    public static Integer parseInteger(Object obj) {
        return parseInteger(obj,false);
    }

    /**
     * 功能描述:
     * 〈对象转Integer〉
     * @param obj 对象
     * @param isNull true 为空返回null false 为空返回0
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 18:52
     */
    public static Integer parseInteger(Object obj,Boolean isNull) {
        if(!StringUtil.isNull(obj) && isNumber(obj)){
            return Integer.valueOf(obj.toString());
        }
        if(isNull){
            return null;
        }else{
            return 0;
        }
    }

   /**
    * 功能描述:
    * 〈对象转Double〉
    * @param obj 对象
    * @return: java.lang.Double
    * @author: 吴宇森
    * @date: 2022/2/16 18:58
    */
    public static Double parseDouble(Object obj){
        return parseDouble(obj,false);
    }

    /**
     * 功能描述:
     * 〈对象转Double〉
     * @param obj 对象
     * @param isNull true 为空返回null false 为空返回0.0
     * @return: java.lang.Double
     * @author: 吴宇森
     * @date: 2022/2/16 18:58
     */
    public static Double parseDouble(Object obj,Boolean isNull){
        if(!StringUtil.isNull(obj) && isNumber(obj)){
            return Double.valueOf(obj.toString());
        }
        if(isNull){
            return null;
        }else{
            return 0.0;
        }
    }

    /**
     * 功能描述:
     * 〈对象转Long〉
     * @param obj 对象
     * @return: java.lang.Long
     * @author: 吴宇森
     * @date: 2022/2/16 18:59
     */
    public static Long parseLong(Object obj) {
        return parseLong(obj,false);
    }

    /**
     * 功能描述:
     * 〈对象转Long〉
     * @param obj 对象
     * @param isNull true 为空返回null false 为空返回0L
     * @return: java.lang.Long
     * @author: 吴宇森
     * @date: 2022/2/16 18:59
     */
    public static Long parseLong(Object obj,Boolean isNull) {
        if(!StringUtil.isNull(obj) && isNumber(obj)){
            return Long.valueOf(obj.toString());
        }
        if(isNull){
            return null;
        }else{
            return 0L;
        }
    }

    /**
     * 功能描述:
     * 〈BigDecimal四舍五入处理〉
     * @param numb 需要处理的数据
     * @param digit 小数保留位数
     * @param type 四舍五入 true:向上舍 false:向下舍
     * @return: java.math.BigDecimal
     * @author: 吴宇森
     * @date: 2022/2/16 19:02
     */
    public static BigDecimal parseBigDecimal(BigDecimal numb, int digit, boolean type){
        BigDecimal setScale = numb;
        if(!StringUtil.isNull(numb)){
            if(type){
                setScale = numb.setScale(digit,BigDecimal.ROUND_HALF_UP);
            }else {
                setScale = numb.setScale(digit,BigDecimal.ROUND_HALF_DOWN);
            }
        }
        return setScale;
    }

    /**
     * 功能描述:
     * 〈浮点相加〉
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleAdd(double d1,double d2){
        BigDecimal bd1=new BigDecimal(d1);
        BigDecimal bd2=new BigDecimal(d2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相减〉
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleSub(double d1,double d2){
        BigDecimal bd1=new BigDecimal(d1);
        BigDecimal bd2=new BigDecimal(d2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相乘〉
     * @param d1 double参数1
     * @param d2 double参数2
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleRide(double d1,double d2){
        BigDecimal bd1=new BigDecimal(d1);
        BigDecimal bd2=new BigDecimal(d2);
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 功能描述:
     * 〈浮点相除〉
     * @param d1 double参数1
     * @param d2 double参数2
     * @param digit 小数位
     * @return: double
     * @author: 吴宇森
     * @date: 2021/10/20 17:31
     */
    public static double doubleExcept(double d1,double d2,int digit){
        BigDecimal bd1=new BigDecimal(d1);
        BigDecimal bd2=new BigDecimal(d2);
        return bd1.divide(bd2,digit,BigDecimal.ROUND_DOWN).doubleValue();
    }
}
