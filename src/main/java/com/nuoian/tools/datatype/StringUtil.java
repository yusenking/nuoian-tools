package com.nuoian.tools.datatype;

import com.nuoian.tools.constants.Constants;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/16 15:22
 * @Description: 字符串工具
 * @Package: com.nuoian.tools.datatype
 * @Version: 1.0
 */
public class StringUtil {

    /**
     * 功能描述:
     * 〈判断对象是否为空〉
     * @param object 对象
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 功能描述:
     * 〈判断字符串是否为空〉
     * @param str 字符串
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isBlank(String str) {
        if (str == null || str.isEmpty() || str.replaceAll(Constants.BLANK, "").isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈判断字符串集是否为空〉
     * @param strs 字符串集
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isBlank(String... strs) {
        for (int i = Constants.NUMBER_0; i < strs.length; i++) {
            if (isBlank(strs[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈字符串是否包含键〉
     * @param str 字符串
     * @param key 键
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/2/16 15:46
     */
    public static boolean contains(String str, String key) {
        if (str != null && str.contains(key)) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈字符串集是否包含键〉
     * @param strs 字符串集
     * @param key 键
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/2/16 15:46
     */
    public static boolean contains(String[] strs, String key) {
        for (int i = Constants.NUMBER_0; i < strs.length; i++) {
            if (contains(strs[i], key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈字符串首字母大写〉
     * @param str
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:48
     */
    public static String upperHeadChar(String str) {
        if(isBlank(str)){
            return null;
        }
        String head = str.substring(Constants.NUMBER_0, Constants.NUMBER_1);
        return head.toUpperCase() + str.substring(Constants.NUMBER_1);
    }

    /**
     * 功能描述:
     * 〈生成数字验证码0-9〉
     * @param number 多少位
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:49
     */
    public static String generateMessageCode(Integer number) {
        //生成随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = Constants.NUMBER_0; i < number; i++) {
            code.append(Constants.AUTH_CODE_ARR.charAt(new Random().nextInt(Constants.AUTH_CODE_ARR.length())));
        }
        return code.toString();
    }

    /**
     * 功能描述:
     * 〈生成数字验证码1-9〉
     * @param number 多少位
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:50
     */
    public static String generateMessageCode2(Integer number) {
        //生成随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = Constants.NUMBER_0; i < number; i++) {
            code.append(Constants.AUTH_CODE_ARR2.charAt(new Random().nextInt(Constants.AUTH_CODE_ARR2.length())));
        }
        return code.toString();
    }

    /**
     * 功能描述:
     * 〈混合随机串码 大小写字母+0-9组合〉
     *
     * @param number 长度
     * @return : java.lang.String
     * @author : 吴宇森
     * @date : 2020/07/08 08:45
     */
    public static String mixtureCodeForBidAndSmall(int number) {
        char[] codeSequence = Constants.LETTER_NUMBER.toCharArray();
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        for (int i = Constants.NUMBER_0; i < number; i++) {
            str.append(codeSequence[r.nextInt(codeSequence.length)]);
        }
        return str.toString();
    }

    /**
     * 功能描述:
     * 〈字符串转Base64编码的字符〉
     * @param str 原始字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:03
     */
    public static String stringToBase64(String str) {
        if(isBlank(str)){
            return null;
        }
        return new BASE64Encoder().encode(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 功能描述:
     * 〈Base64编码的字符转字符串〉
     * @param str 被解密字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:03
     */
    public static String base64ToString(String str) {
        try{
            if(isBlank(str)){
                return null;
            }
            return new String(new BASE64Decoder().decodeBuffer(str),StandardCharsets.UTF_8) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 功能描述:
     * 〈字符串替换空格、换行〉
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:05
     */
    public static String replaceBlank(String str) {
        String dest = null;
        if (str != null) {
            Pattern p = Pattern.compile(Constants.LINE_BREAK);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replaceAll(" ", "");
        }
        return dest;
    }

    /**
     * 功能描述:
     * 〈int 转 中文数字〉
     * @param intNum 数字
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:12
     */
    public static String intToChineseNum(int intNum) {
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        if (intNum < Constants.NUMBER_0) {
            isNegative = true;
            intNum *= Constants.LOSE_NUMBER_1;
        }
        int count = Constants.NUMBER_0;
        while (intNum > Constants.NUMBER_0) {
            sb.insert(Constants.NUMBER_0, Constants.CN_NUM[intNum % Constants.NUMBER_10] + Constants.CN_UNIT[count]);
            intNum = intNum / Constants.NUMBER_10;
            count++;
        }
        if (isNegative) {
            sb.insert(Constants.NUMBER_0, Constants.CN_NEGATIVE);
        }
        return sb.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }

    /**
     * 功能描述:
     * 〈bigDecimal 转 中文数字〉
     * @param bigDecimalNum 要转换的BigDecimal数
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:11
     */
    public static String bigDecimalToChineseNum(BigDecimal bigDecimalNum) {
        if (bigDecimalNum == null) {
            return Constants.CN_NUM[Constants.NUMBER_0];
        }
        StringBuilder sb = new StringBuilder();
        //将小数点后面的零给去除
        String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();
        String[] split = numStr.split("\\.");
        String integerStr = intToChineseNum(Integer.parseInt(split[Constants.NUMBER_0]));
        sb.append(integerStr);
        //如果传入的数有小数，则进行切割，将整数与小数部分分离
        if (split.length == Constants.NUMBER_2) {
            //有小数部分
            sb.append(Constants.CN_POINT);
            String decimalStr = split[Constants.NUMBER_1];
            char[] chars = decimalStr.toCharArray();
            for (char aChar : chars) {
                int index = Integer.parseInt(String.valueOf(aChar));
                sb.append(Constants.CN_NUM[index]);
            }
        }
        //判断传入数字为正数还是负数
        if (bigDecimalNum.signum() == Constants.LOSE_NUMBER_1) {
            sb.insert(Constants.NUMBER_0, Constants.CN_NEGATIVE);
        }
        return sb.toString();
    }

    /**
     * 功能描述 :
     * 〈取汉字的第一个字符〉
     *
     * @param chineseLanguage 需要转换的汉字字符串
     * @return : java.lang.String
     * @author : 吴宇森
     * @date : 2020/10/12 09:56
     */
    public static String getFirstLetter(String chineseLanguage) {
        char[] chars = chineseLanguage.trim().toCharArray();
        StringBuilder hanyupinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = Constants.NUMBER_0; i < chars.length; i++) {
                String str = String.valueOf(chars[i]);
                // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    hanyupinyin.append(PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[Constants.NUMBER_0].substring(Constants.NUMBER_0, Constants.NUMBER_1));
                    // 如果字符是数字,取数字
                } else if (str.matches("[0-9]+")) {
                    hanyupinyin.append(str);
                    // 如果字符是字母,取字母
                } else if (str.matches("[a-zA-Z]+")) {
                    hanyupinyin.append(str);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return hanyupinyin.toString();
    }
}
