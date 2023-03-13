package com.nuoian.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:41
 * @Description: JSON解析工具类
 * @Package: com.nuoian.common.utils
 * @Version: 1.0
 */
public class JSONUtil {

    /**
     * 功能描述:
     * 〈获取JSON中的 String〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.String
     * @author 吴宇森
     * @date 2023/3/10 16:28
     */
    public static String getString(String json, String key) {
        return JSON.parseObject(json).getString(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 IntValue〉
     * @param json JSON字符串
     * @param key 键
     * @return int
     * @author 吴宇森
     * @date 2023/3/10 16:28
     */
    public static int getIntValue(String json, String key) {
        return JSON.parseObject(json).getIntValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 BooleanValue〉
     * @param json JSON字符串
     * @param key 键
     * @return boolean
     * @author 吴宇森
     * @date 2023/3/10 16:27
     */
    public static boolean getBooleanValue(String json, String key) {
        return JSON.parseObject(json).getBooleanValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 LongValue〉
     * @param json JSON字符串
     * @param key 键
     * @return long
     * @author 吴宇森
     * @date 2023/3/10 16:27
     */
    public static long getLongValue(String json, String key) {
        return JSON.parseObject(json).getLongValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 DoubleValue〉
     * @param json JSON字符串
     * @param key 键
     * @return double
     * @author 吴宇森
     * @date 2023/3/10 16:27
     */
    public static double getDoubleValue(String json, String key) {
        return JSON.parseObject(json).getDoubleValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 FloatValue〉
     * @param json JSON字符串
     * @param key 键
     * @return float
     * @author 吴宇森
     * @date 2023/3/10 16:26
     */
    public static float getFloatValue(String json, String key) {
        return JSON.parseObject(json).getFloatValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 ByteValue〉
     * @param json JSON字符串
     * @param key 键
     * @return byte
     * @author 吴宇森
     * @date 2023/3/10 16:25
     */
    public static byte getByteValue(String json, String key) {
        return JSON.parseObject(json).getByteValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 ShortValue〉
     * @param json JSON字符串
     * @param key 键
     * @return short
     * @author 吴宇森
     * @date 2023/3/10 16:25
     */
    public static short getShortValue(String json, String key) {
        return JSON.parseObject(json).getShortValue(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Integer〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.Integer
     * @author 吴宇森
     * @date 2023/3/10 16:25
     */
    public static Integer getInteger(String json, String key) {
        return JSON.parseObject(json).getInteger(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Boolean〉
     * @param json JSON字符串
     * @param key 键
     * @return boolean
     * @author 吴宇森
     * @date 2023/3/10 16:24
     */
    public static boolean getBoolean(String json, String key) {
        return JSON.parseObject(json).getBoolean(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Long〉
     * @param json JSON字符串
     * @param key 键
     * @return long
     * @author 吴宇森
     * @date 2023/3/10 16:24
     */
    public static long getLong(String json, String key) {
        return JSON.parseObject(json).getLong(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Double〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.Double
     * @author 吴宇森
     * @date 2023/3/10 16:24
     */
    public static Double getDouble(String json, String key) {
        return JSON.parseObject(json).getDouble(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Float〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.Float
     * @author 吴宇森
     * @date 2023/3/10 16:24
     */
    public static Float getFloat(String json, String key) {
        return JSON.parseObject(json).getFloat(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Byte〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.Byte
     * @author 吴宇森
     * @date 2023/3/10 16:23
     */
    public static Byte getByte(String json, String key) {
        return JSON.parseObject(json).getByte(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Short〉
     * @param json JSON字符串
     * @param key 键
     * @return java.lang.Short
     * @author 吴宇森
     * @date 2023/3/10 16:23
     */
    public static Short getShort(String json, String key) {
        return JSON.parseObject(json).getShort(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 byte[]〉
     * @param json JSON字符串
     * @param key 键
     * @return byte[]
     * @author 吴宇森
     * @date 2023/3/10 16:22
     */
    public static byte[] getBytes(String json, String key) {
        return JSON.parseObject(json).getBytes(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 BigInteger〉
     * @param json JSON字符串
     * @param key 键
     * @return java.math.BigInteger
     * @author 吴宇森
     * @date 2023/3/10 16:22
     */
    public static BigInteger getBigInteger(String json, String key) {
        return JSON.parseObject(json).getBigInteger(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 BigDecimal〉
     * @param json JSON字符串
     * @param key 键
     * @return java.math.BigDecimal
     * @author 吴宇森
     * @date 2023/3/10 16:22
     */
    public static BigDecimal getBigDecimal(String json, String key) {
        return JSON.parseObject(json).getBigDecimal(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的 Date〉
     * @param json JSON字符串
     * @param key 键
     * @return java.util.Date
     * @author 吴宇森
     * @date 2023/3/10 16:21
     */
    public static Date getDate(String json, String key) {
        return JSON.parseObject(json).getDate(key);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的对象〉
     * @param json JSON 字符串
     * @param key 键
     * @param clazz 类
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 16:21
     */
    public static <T> T getObject(String json, String key, Class<T> clazz) {
        return JSON.parseObject(json).getObject(key, clazz);
    }

    /**
     * 功能描述:
     * 〈获取JSON中的集合〉
     * @param json JSON字符串
     * @param key 键
     * @param clazz 类
     * @return java.util.List<T>
     * @author 吴宇森
     * @date 2023/3/10 16:20
     */
    public static <T> List<T> getArray(String json, String key, Class<T> clazz) {
        return JSON.parseObject(json).getList(key,clazz);
    }

    /**
     * 功能描述:
     * 〈JSON 转换换成对象〉
     * @param json JSON字符串
     * @param clazz 类
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 16:20
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 功能描述:
     * 〈JSON 转换换成集合〉
     * @param json JSON字符串
     * @param clazz 类
     * @return java.util.List<T>
     * @author 吴宇森
     * @date 2023/3/10 16:20
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 功能描述:
     * 〈Object 转 String〉
     * @param o 对象
     * @return java.lang.String
     * @author 吴宇森
     * @date 2023/3/10 16:19
     */
    public static String objectToString(Object o) {
        return JSONObject.toJSONString(o);
    }

}
