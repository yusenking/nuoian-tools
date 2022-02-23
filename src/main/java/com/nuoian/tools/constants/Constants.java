package com.nuoian.tools.constants;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/16 13:44
 * @Description: 常量
 * @Package: com.nuoian.tools.constants
 * @Version: 1.0
 */
public class Constants {
    /**
     * 数字-1
     */
    public static final Integer LOSE_NUMBER_1 = -1;
    /**
     * 数字0
     */
    public static final Integer NUMBER_0 = 0;
    /**
     * 数字1
     */
    public static final Integer NUMBER_1 = 1;
    /**
     * 数字3
     */
    public static final Integer NUMBER_2 = 2;
    /**
     * 数字3
     */
    public static final Integer NUMBER_3 = 3;
    /**
     * 数字4
     */
    public static final Integer NUMBER_4 = 4;
    /**
     * 数字5
     */
    public static final Integer NUMBER_5 = 5;
    /**
     * 数字6
     */
    public static final Integer NUMBER_6 = 6;
    /**
     * 数字7
     */
    public static final Integer NUMBER_7 = 7;
    /**
     * 数字9
     */
    public static final Integer NUMBER_9 = 9;
    /**
     * 数字10
     */
    public static final Integer NUMBER_10 = 10;
    /**
     * 数字13
     */
    public static final Integer NUMBER_13 = 13;
    /**
     * 数字23
     */
    public static final Integer NUMBER_23 = 23;
    /**
     * 数字24
     */
    public static final Integer NUMBER_24 = 24;
    /**
     * 数字59
     */
    public static final Integer NUMBER_59 = 59;
    /**
     * 数字60
     */
    public static final Integer NUMBER_60 = 60;
    /**
     * 数字999
     */
    public static final Integer NUMBER_999 = 999;
    /**
     * 数字1000
     */
    public static final Integer NUMBER_1000 = 1000;

    /**
     * 数字1024
     */
    public static final Integer NUMBER_1024 = 1024;
    /**
     * 1小时毫秒数
     */
    public static final Long HOUR_MS = 3600000L;
    /**
     * 字符集名称
     */
    public static final String CHARSET_NAME="UTF-8";

    public static final String CHARSET_UTF_OPTS="OPTS UTF8";

    public static final String ON="ON";
    /**
     * 算法
     */
    public static final String DES_ALGORITHM="DES";
    /**
     * 安全策略
     */
    public static final String SHA1PRNG="SHA1PRNG";
    /**
     * 加密盐值
     */
    public static final String DES_KEY="nuoian";
    /**
     * 算法
     */
    public static final String AES_ALGORITHM = "AES";
    /**
     * 默认的加密算法，CBC模式
     */
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 密匙，必须16位
     */
    public static final String AES_KEY = "gi5gCJXu6iUFLbWN";
    /**
     * 偏移量
     */
    public static final String OFFSET = "eIhxJdbDuNM7G6v0";

    /**
     * 验证码规则
     */
    public static final String AUTH_CODE_ARR = "0123456789";
    public static final String AUTH_CODE_ARR2 = "123456789";
    /**
     * 数字及字符串
     */
    public static final String LETTER_NUMBER="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    /**
     * 换行符
     */
    public static final String LINE_BREAK="\n";
    /**
     * 空格符
     */
    public static final String BLANK=" ";
    /**
     * 下划线
     */
    public static final String UNDERLINE="_";
    /**
     * 中文数字
     */
    public static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 中文数字单位
     */
    public static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
    /**
     * 特殊字符：负
     */
    public static final String CN_NEGATIVE = "负";

    /**
     * 特殊字符：点
     */
    public static final String CN_POINT = "点";

}
