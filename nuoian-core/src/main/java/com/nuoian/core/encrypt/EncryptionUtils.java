package com.nuoian.core.encrypt;

import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:26
 * @Description: 加解密工具类
 * @Package: com.nuoian.core.encrypt
 * @Version: 1.0
 */
public class EncryptionUtils {

    private static final Key KEY;

    static {
        try{
            //生成DES算法对象
            KeyGenerator generator=KeyGenerator.getInstance(GlobalConstants.DES_ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom=SecureRandom.getInstance(GlobalConstants.SHA1PRNG);
            //设置上密钥种子
            secureRandom.setSeed(GlobalConstants.DES_KEY.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密钥对象
            KEY=generator.generateKey();
            generator=null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈字符串加密〉
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:21
     */
    public static String desEncrypt(String str) {
        if(str==null){
            return "";
        }
        //基于BASE64编码，接收byte[]并转换成String
        Base64.Encoder encoder=Base64.getEncoder();
        try {
            //按utf8编码
            byte[] bytes = str.getBytes(GlobalConstants.CHARSET_NAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(GlobalConstants.DES_ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //byte[]to encode好的String 并返回
            return encoder.encodeToString(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈字符串解密〉
     * @param str 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:21
     */
    public static String desDecrypt(String str) {
        if(str==null){
            return "";
        }
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            //将字符串decode成byte[]
            byte[] bytes = decoder.decode(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(GlobalConstants.DES_ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            //解密
            byte[] doFial = cipher.doFinal(bytes);
            return new String(doFial, GlobalConstants.CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈字符串AES加密〉
     * @param data 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:27
     */
    public static String aesEncrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(GlobalConstants.CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(GlobalConstants.AES_KEY.getBytes(StandardCharsets.UTF_8), GlobalConstants.AES_ALGORITHM);
            //CBC模式偏移量IV
            IvParameterSpec iv = new IvParameterSpec(GlobalConstants.OFFSET.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈字符串AES解密〉
     * @param data 字符串
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 15:37
     */
    public static String aesDecrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(GlobalConstants.CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(GlobalConstants.AES_KEY.getBytes(StandardCharsets.UTF_8), GlobalConstants.AES_ALGORITHM);
            //CBC模式偏移量IV
            IvParameterSpec iv = new IvParameterSpec(GlobalConstants.OFFSET.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(parseHexStr2Byte(data));
            return new String(encrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈Md5加盐加密〉
     *
     * @param password 密码
     * @param salt     盐值
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/8/30 16:06
     */
    public static String saltingMd5(String password, String salt) {
        String sb = encrytMd5(password) +
                encrytMd5(salt);
        return encrytMd5(sb);
    }

    /**
     * 功能描述:
     * 〈MD5加密〉
     * @param message 要进行MD5加密的字符串
     * @return java.lang.String 加密结果为32位字符串
     * @author 吴宇森
     * @date 2023/4/12 14:33
     */
    public static String encrytMd5(String message) {
        MessageDigest messageDigest;
        StringBuilder md5StrBuff = new StringBuilder();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] byteArray = messageDigest.digest();
            for (byte b : byteArray) {
                if (Integer.toHexString(0xFF & b).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & b));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & b));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        //字母大写
        return md5StrBuff.toString().toUpperCase();
    }


    /**
     * 功能描述:
     * 〈解析登录密码〉
     * @param text 原始字符串
     * @param key 原始键
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2021/7/30 15:18
     */
    public static String analysisLoginPassword(String text,String key){
        if(StringUtils.isBlank(text,key)){
            return null;
        }
        String yspwd=decrypt(text,key);
        if(!StringUtils.isBlank(yspwd)){
            String key2= StringUtils.base64ToString(key).split(":")[1];
            StringBuilder sb=new StringBuilder();
            for (String s : key2.split(GlobalConstants.SYMBOL)) {
                if(!StringUtils.isBlank(s)){
                    int index=Integer.parseInt(s);
                    sb.append(yspwd.charAt(index));
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈解密字符串〉
     * @param encrypeText 需解密的字符串
     * @param key 键
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2021/7/30 14:56
     */
    private static String decrypt(String encrypeText,String key){
        if(StringUtils.isBlank(encrypeText,key)){
            return null;
        }
        try{
            DESKeySpec desKeySpec=new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory secretKeyFactory=SecretKeyFactory.getInstance(GlobalConstants.DES_ALGORITHM);
            SecretKey secretKey=secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encrypeText));
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈二进制转16进制〉
     * @param buf 1
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 14:45
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /** 
     * 功能描述: 
     * 〈16进制转2进制〉
     * @param hexStr 字符串
     * @return: byte[]
     * @author: 吴宇森
     * @date: 2022/4/13 14:44
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / GlobalConstants.NUMBER_2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
