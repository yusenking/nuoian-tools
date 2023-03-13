package com.nuoian.core.datatype;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.nuoian.core.constants.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:11
 * @Description: 字符串工具
 * @Package: com.nuoian.core.datatype
 * @Version: 1.0
 */
@Slf4j
public class StringUtils {

    /**
     * 功能描述:
     * 〈比较俩对象是否相等〉
     *
     * @param obj1 1
     * @param obj2 2
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/4/8 17:46
     */
    public static boolean equal(Object obj1, Object obj2) {
        return obj1 instanceof BigDecimal && obj2 instanceof BigDecimal ? NumberUtils.equals((BigDecimal) obj1, (BigDecimal) obj2) : Objects.equals(obj1, obj2);
    }

    /**
     * 功能描述:
     * 〈判断对象是否为空〉
     *
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
     * 〈判断对象是否不为空〉
     *
     * @param object 对象
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isNotNull(Object object) {
        return object != null;
    }

    /**
     * 功能描述:
     * 〈判断字符串是否为空〉
     *
     * @param str 字符串
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isBlank(String str) {
        return str == null || GlobalConstants.NULL_STRING.equals(str) || str.isEmpty() || str.replaceAll(GlobalConstants.SPACE, GlobalConstants.BLANK).isEmpty();
    }

    /**
     * 功能描述:
     * 〈判断字符串是否为空〉
     *
     * @param str 字符串
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 功能描述:
     * 〈判断字符串集是否为空〉
     *
     * @param strs 字符串集
     * @return: boolean true 空 false 非空
     * @author: 吴宇森
     * @date: 2022/2/16 15:41
     */
    public static boolean isBlank(String... strs) {
        for (int i = GlobalConstants.NUMBER_0; i < strs.length; i++) {
            if (isBlank(strs[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈字符串是否包含键〉
     *
     * @param str 字符串
     * @param key 键
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/2/16 15:46
     */
    public static boolean contains(String str, String key) {
        return str != null && str.contains(key);
    }

    /**
     * 功能描述:
     * 〈字符串集是否包含键〉
     *
     * @param strs 字符串集
     * @param key  键
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/2/16 15:46
     */
    public static boolean contains(String[] strs, String key) {
        for (int i = GlobalConstants.NUMBER_0; i < strs.length; i++) {
            if (contains(strs[i], key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈字符串首字母大写〉
     *
     * @param str
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:48
     */
    public static String upperHeadChar(String str) {
        if (isBlank(str)) {
            return null;
        }
        String head = str.substring(GlobalConstants.NUMBER_0, GlobalConstants.NUMBER_1);
        return head.toUpperCase() + str.substring(GlobalConstants.NUMBER_1);
    }

    /**
     * 功能描述:
     * 〈生成数字验证码0-9〉
     *
     * @param number 多少位
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:49
     */
    public static String generateMessageCode(Integer number) {
        //生成随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = GlobalConstants.NUMBER_0; i < number; i++) {
            code.append(GlobalConstants.AUTH_CODE_ARR.charAt(new Random().nextInt(GlobalConstants.AUTH_CODE_ARR.length())));
        }
        return code.toString();
    }

    /**
     * 功能描述:
     * 〈生成数字验证码1-9〉
     *
     * @param number 多少位
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:50
     */
    public static String generateMessageCode2(Integer number) {
        //生成随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = GlobalConstants.NUMBER_0; i < number; i++) {
            code.append(GlobalConstants.AUTH_CODE_ARR2.charAt(new Random().nextInt(GlobalConstants.AUTH_CODE_ARR2.length())));
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
        char[] codeSequence = GlobalConstants.LETTER_NUMBER.toCharArray();
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        for (int i = GlobalConstants.NUMBER_0; i < number; i++) {
            str.append(codeSequence[r.nextInt(codeSequence.length)]);
        }
        return str.toString();
    }

    /**
     * 功能描述:
     * 〈字符串转Base64编码的字符〉
     *
     * @param str 原始字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:03
     */
    public static String stringToBase64(String str) {
        if (isBlank(str)) {
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 功能描述:
     * 〈Base64编码的字符转字符串〉
     *
     * @param str 被解密字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:03
     */
    public static String base64ToString(String str) {
        try {
            if (isBlank(str)) {
                return null;
            }
            Base64.Decoder decoder = Base64.getDecoder();
            return new String(decoder.decode(str), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 功能描述:
     * 〈字符串替换空格、换行〉
     *
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:05
     */
    public static String replaceBlank(String str) {
        String dest = null;
        if (str != null) {
            Pattern p = Pattern.compile(GlobalConstants.LINE_BREAK);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replaceAll(" ", "");
        }
        return dest;
    }

    /**
     * 功能描述:
     * 〈int 转 中文数字〉
     *
     * @param intNum 数字
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:12
     */
    public static String intToChineseNum(int intNum) {
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        if (intNum < GlobalConstants.NUMBER_0) {
            isNegative = true;
            intNum *= GlobalConstants.LOSE_NUMBER_1;
        }
        int count = GlobalConstants.NUMBER_0;
        while (intNum > GlobalConstants.NUMBER_0) {
            sb.insert(GlobalConstants.NUMBER_0, GlobalConstants.CN_NUM[intNum % GlobalConstants.NUMBER_10] + GlobalConstants.CN_UNIT[count]);
            intNum = intNum / GlobalConstants.NUMBER_10;
            count++;
        }
        if (isNegative) {
            sb.insert(GlobalConstants.NUMBER_0, GlobalConstants.CN_NEGATIVE);
        }
        return sb.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }

    /**
     * 功能描述:
     * 〈bigDecimal 转 中文数字〉
     *
     * @param bigDecimalNum 要转换的BigDecimal数
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 16:11
     */
    public static String bigDecimalToChineseNum(BigDecimal bigDecimalNum) {
        if (bigDecimalNum == null) {
            return GlobalConstants.CN_NUM[GlobalConstants.NUMBER_0];
        }
        StringBuilder sb = new StringBuilder();
        //将小数点后面的零给去除
        String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();
        String[] split = numStr.split("\\.");
        String integerStr = intToChineseNum(Integer.parseInt(split[GlobalConstants.NUMBER_0]));
        sb.append(integerStr);
        //如果传入的数有小数，则进行切割，将整数与小数部分分离
        if (split.length == GlobalConstants.NUMBER_2) {
            //有小数部分
            sb.append(GlobalConstants.CN_POINT);
            String decimalStr = split[GlobalConstants.NUMBER_1];
            char[] chars = decimalStr.toCharArray();
            for (char aChar : chars) {
                int index = Integer.parseInt(String.valueOf(aChar));
                sb.append(GlobalConstants.CN_NUM[index]);
            }
        }
        //判断传入数字为正数还是负数
        if (bigDecimalNum.signum() == GlobalConstants.LOSE_NUMBER_1) {
            sb.insert(GlobalConstants.NUMBER_0, GlobalConstants.CN_NEGATIVE);
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
            for (int i = GlobalConstants.NUMBER_0; i < chars.length; i++) {
                String str = String.valueOf(chars[i]);
                // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    hanyupinyin.append(PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[GlobalConstants.NUMBER_0].substring(GlobalConstants.NUMBER_0, GlobalConstants.NUMBER_1));
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

    /**
     * 功能描述:
     * 〈将驼峰命名转换为大写下划线命名〉
     *
     * @param name 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 19:17
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (isBlank(name)) {
            // 将第一个字符处理成大写
            result.append(name.substring(GlobalConstants.NUMBER_0, GlobalConstants.NUMBER_1).toUpperCase());
            // 循环处理其余字符
            for (int i = GlobalConstants.NUMBER_1; i < name.length(); i++) {
                String s = name.substring(i, i + GlobalConstants.NUMBER_1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(GlobalConstants.NUMBER_0))) {
                    result.append(GlobalConstants.UNDERLINE);
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 功能描述:
     * 〈将大写下划线名称转换为驼峰命名〉
     *
     * @param name 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 19:18
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (isBlank(name)) {
            // 没必要转换
            return null;
        } else if (!contains(name, GlobalConstants.UNDERLINE)) {
            // 不含下划线，仅将首字母小写
            return name.substring(GlobalConstants.NUMBER_0, GlobalConstants.NUMBER_1).toLowerCase() + name.substring(GlobalConstants.NUMBER_1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split(GlobalConstants.UNDERLINE);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (isBlank(camel)) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == GlobalConstants.NUMBER_0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(GlobalConstants.NUMBER_0, GlobalConstants.NUMBER_1).toUpperCase());
                result.append(camel.substring(GlobalConstants.NUMBER_1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 功能描述:
     * 〈字符串根据符号切割返回集合〉
     *
     * @param str    字符串
     * @param symbol 符号
     * @return: java.util.List<java.lang.String>
     * @author: 吴宇森
     * @date: 2022/2/16 19:22
     */
    public static List<String> stringToList(String str, String symbol) {
        if (isBlank(str)) {
            return null;
        }
        String[] params = str.split(symbol);
        return new ArrayList<>(Arrays.asList(params));
    }

    /**
     * 功能描述:
     * 〈字符串列表根据符号转换成字符串〉
     *
     * @param list   字符串列表
     * @param symbol 符号
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 19:24
     */
    public static String listToString(List<String> list, String symbol) {
        return String.join(symbol, list);
    }

    /**
     * 功能描述:
     * 〈生成8位UUID〉
     *
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 19:25
     */
    public static String randomUuid8() {
        char[] chars = GlobalConstants.LETTER_NUMBER.toCharArray();
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < GlobalConstants.NUMBER_8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 生成24位uuid
     *
     * @return
     * @author wangfan
     */
    public static String randomUuid24() {
        UUID uuid = UUID.randomUUID();
        String[] ids = uuid.toString().split("-");
        return ids[0] + ids[1] + ids[4];
    }

    /**
     * 功能描述:
     * 〈生成32位UUID〉
     *
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 19:25
     */
    public static String randomUuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 纯数字随机串码
     *
     * @param number 多少位
     * @return 运行结果
     */
    public static String numberCode(int number) {
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        int[] ary = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        for (int i = 0; i < number; i++) {
            str.append(ary[r.nextInt(ary.length)]);
        }
        return str.toString();
    }

    /**
     * 纯数字随机串码
     *
     * @param number 多少位
     * @return 运行结果
     */
    public static String numberCodeNot0(int number) {
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        int[] ary = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < number; i++) {
            str.append(ary[r.nextInt(ary.length)]);
        }
        return str.toString();
    }


    /**
     * 功能描述:
     * 〈将路径转换为匹配路径〉
     *
     * @param path 路径
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 16:23
     */
    private static String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {
            //遇到*字符
            if (chars[i] == '*') {
                //如果是第二次遇到*，则将**替换成.*
                if (preX) {
                    sb.append(".*");
                    preX = false;
                }//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                else if (i + 1 == len) {
                    sb.append("[^/]*");
                }//否则单星后面还有字符，则不做任何动作，下一把再做动作
                else {
                    preX = true;
                }
            } else {//遇到非*字符
                //如果上一把是*，则先把上一把的*对应的[^/]*添进来
                if (preX) {
                    sb.append("[^/]*");
                    preX = false;
                }
                //接着判断当前字符是不是?，是的话替换成.
                if (chars[i] == '?') {
                    sb.append('.');
                }
                //不是?的话，则就是普通字符，直接添进来
                else {
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 功能描述:
     * 〈根据路径判断请求的路径是否匹配〉
     *
     * @param whitePath 排除的路径
     * @param reqPath   请求的路径
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/4/13 16:24
     */
    public static boolean wildcardEquals(String whitePath, String reqPath) {
        String regPath = getRegPath(whitePath);
        return Pattern.compile(regPath).matcher(reqPath).matches();

    }


    /**
     * 功能描述:
     * 〈集合根据间隔符转字符串〉
     *
     * @param datas     集合
     * @param separator 间隔符
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/8/9 16:29
     */
    public static String collectionToString(Collection<String> datas, String separator) {
        if (isNull(datas) || datas.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String data : datas) {
            sb.append("\"");
            sb.append(data);
            sb.append("\"");
            sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * 功能描述:
     * 〈生成订单号〉
     *
     * @return java.lang.String
     * @author 吴宇森
     * @date 2023/1/11 11:11
     */
    public static String generateOrderNo() {
        //最大支持1-9个集群机器部署
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    /**
     * 功能描述:
     * 〈判断数组中是否包含某个字符串〉
     *
     * @param arrar 1
     * @param str   2
     * @return: boolean
     * @author: LeiZiLin
     * @date: 2020/10/23 10:52
     */
    public static boolean isContainsByArray(String[] arrar, String str) {
        return Arrays.asList(arrar).contains(str);
    }

    /**
     * 获取随机整数
     *
     * @param size
     * @return
     */
    public static int getRandoNumber(int size) {
        return new Random().nextInt(size);
    }

    /**
     * Object转String
     *
     * @param o
     * @return
     */
    public static String objectToString(Object o) {
        try {
            return JSONObject.toJSONString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述 :
     * 〈组装分页参数〉
     *
     * @param page 页
     * @param size 大小
     * @return : com.alibaba.fastjson.JSONObject
     * @author : 吴宇森
     * @date : 2021/01/27 18:10
     */
    public static JSONObject pageAssemble(int page, int size) {
        JSONObject data = new JSONObject();
        data.put("page", ((Integer.max(1, page) - 1) * size));
        data.put("size", Integer.max(10, size));
        return data;
    }

    /**
     * 功能描述:
     * 〈去除字符串所有的空格〉
     *
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/6/29 11:34
     */
    public static String removeStringSpace(String str) {
        StringBuilder sb = new StringBuilder();
        char[] strs = str.toCharArray();
        for (char c : strs) {
            if (!isBlank(String.valueOf(c))) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(GlobalConstants.UNDERLINE);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(GlobalConstants.UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 功能描述:
     * 〈字符串脱敏〉
     *
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/10/20 16:22
     */
    public static String stringDesensitization(String str) {
        //字符串为空或长度小于3
        if (isBlank(str) || str.length() <= GlobalConstants.NUMBER_3) {
            return str;
        }
        int l = str.length() / GlobalConstants.NUMBER_3;
        int length = Math.min(4, l);
        return str.substring(0, length) +
                "*****" +
                str.substring(str.length() - length);
    }

    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }

    public static String assembleFloorLayer(Integer floor, Integer layer) {
        StringBuilder sb = new StringBuilder();
        if (floor != 0) {
            switch (floor) {
                case -1:
                    sb.append("地下车库");
                    break;
                case -2:
                    sb.append("地下室防火分区");
                    break;
                default:
                    sb.append(floor).append("号楼");
                    if (layer != 0) {
                        if (layer == -1) {
                            sb.append("_B1");
                        } else {
                            sb.append("_").append(layer == -10 ? "R" : layer == -20 ? "M" : String.valueOf(layer)).append("F");
                        }

                    }
            }
        }
        return sb.toString();
    }

    /**
     * 功能描述:
     * 〈对象转换〉
     *
     * @param t   对象原始数据
     * @param cla 对象class
     * @return: T
     * @author: 吴宇森
     * @date: 2022/10/31 10:42
     */
    public static <T> T objectTransition(T t, Class<T> cla) {
        JSONObject temp = JSONObject.parseObject(JSONObject.toJSONString(t));
        return JSONObject.parseObject(temp.toJSONString(), cla);
    }

    public static int strToInt(String str) {
        if (isBlank(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String 转 Map
     *
     * @param outstr json字符串
     * @return Map对象
     */
    public static Map<String, Object> stringToMap(String outstr) {
        try {
            return JSONObject.parseObject(outstr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈异常抛出信息字符串输出〉
     *
     * @param t 异常抛出信息
     * @return java.lang.String
     * @author 吴宇森
     * @date 2022/12/1 11:11
     */
    public static String throwableStackExport(Throwable t) {
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer, true));
        return writer.getBuffer().toString();
    }


    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 以，分割的字符串转换成list
     *
     * @param param
     * @return
     */
    public static List<String> stringToList(String param) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(param)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String[] params = param.split(",");
        for (String str : params) {
            list.add(str);
        }
        return list;
    }

    /**
     * list转换成以，分割的字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        return String.join(",", list);
    }

    /**
     * 功能描述:
     * 〈获取随机值集合〉
     *
     * @param count  总个数
     * @param maxVal 随机值最大值
     * @date: 2021/4/1 16:25
     */
    public static List<Integer> getRandomVal(int count, int maxVal) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            list.add(getRandom(maxVal));
        }
        return list;
    }

    /* 不等于0的随机数 */
    private static int getRandom(int maxVal) {
        Random random = new Random();
        int val = random.nextInt(maxVal);
        if (val == 0) {
            getRandom(maxVal);
        }
        return val + 1;
    }

    /**
     * 功能描述:
     * 〈获取随机值集合，指定区间〉
     *
     * @param min   最小值
     * @param max   最大值
     * @param count 总个数
     * @date: 2021/4/1 16:25
     */
    public static int getRandom(int min, int max, int count) {
        Random random = new Random();
        // 先取0-max之间随机数，再对max - min + 1取余，最后加上min，就是最终随机数
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 功能描述:
     * 〈集合根据标识组装成字符串〉
     *
     * @param iterable  集合
     * @param separator 标识
     * @return java.lang.String
     * @author 吴宇森
     * @date 2023/1/11 11:17
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        if (!iterable.iterator().hasNext()) {
            return GlobalConstants.BLANK;
        }
        final Object first = iterable.iterator().next();
        if (!iterable.iterator().hasNext()) {
            return Objects.toString(first, "");
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(GlobalConstants.NUMBER_256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterable.iterator().hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterable.iterator().next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * 判断对象中属性值是否为空
     *
     * @param object
     * @param excludeField 排除字段
     * @return
     */
    public static boolean checkObjFieldsIsNull(Object object, List<String> excludeField) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (excludeField.contains(f.getName())) {
                    continue;
                }
                if (f.get(object) == null || StringUtils.isBlank(f.get(object).toString()) || "0.0".equals(String.valueOf(f.get(object))) || "0".equals(String.valueOf(f.get(object)))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 将Map字符串转换为Map
     *
     * @param str Map字符串
     * @return Map
     */
    public static Map<String, Object> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strArray = str.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        for (String string : strArray) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            // 去掉头部空格
            String key1 = key.trim();
            String value1 = value.trim();
            map.put(key1, value1);
        }
        return map;
    }

    /**
     * 功能描述:
     * 〈Map转为JSONObject〉
     *
     * @param map        1
     * @param resultJson 2
     * @return: com.alibaba.fastjson.JSONObject
     * @author: LeiZiLin
     * @date: 2023/2/27 14:56
     */
    public static JSONObject toJsonObj(Map<String, Object> map, JSONObject resultJson) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            resultJson.put(key, map.get(key));
        }
        return resultJson;
    }


    /**
     * 功能描述:
     * 〈手机号脱敏〉
     * @param phone 手机号
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:38
     */
    public static String encryptPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 功能描述:
     * 〈邮箱脱敏〉
     * @param email 邮箱
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:38
     */
    public static String encryptEmail(String email){
        if (StringUtils.isBlank(email)) {
            return null;
        }
        return  email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 功能描述:
     * 〈身份证脱敏〉
     * @param idCard 身份证
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:39
     */
    public static String encryptIdCard(String idCard){
        if (StringUtils.isBlank(idCard)) {
            return null;
        }
        return  idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2");
    }
}
