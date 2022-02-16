package com.nuoian.tools.encrypt;

import com.nuoian.tools.constants.Constants;
import com.nuoian.tools.datatype.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/16 15:15
 * @Description: DES加密解密
 * @Package: com.nuoian.tools.encrypt
 * @Version: 1.0
 */
public class EncryptionUtil {

private static Key key;

static {
    try{
        //生成DES算法对象
        KeyGenerator generator=KeyGenerator.getInstance(Constants.DES_ALGORITHM);
        //运用SHA1安全策略
        SecureRandom secureRandom=SecureRandom.getInstance(Constants.SHA1PRNG);
        //设置上密钥种子
        secureRandom.setSeed(Constants.DES_KEY.getBytes());
        //初始化基于SHA1的算法对象
        generator.init(secureRandom);
        //生成密钥对象
        key=generator.generateKey();
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
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            //按utf8编码
            byte[] bytes = str.getBytes(Constants.CHARSET_NAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(Constants.DES_ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //byte[]to encode好的String 并返回
            return encoder.encode(doFinal);
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
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //将字符串decode成byte[]
            byte[] bytes = decoder.decodeBuffer(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(Constants.DES_ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
            byte[] doFial = cipher.doFinal(bytes);
            return new String(doFial, Constants.CHARSET_NAME);
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
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(Constants.AES_KEY.getBytes(StandardCharsets.US_ASCII), Constants.AES_ALGORITHM);
            //CBC模式偏移量IV
            IvParameterSpec iv = new IvParameterSpec(Constants.OFFSET.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            //加密后再使用BASE64做转码
            String p = new BASE64Encoder().encode(encrypted);
            return URLEncoder.encode(p,Constants.CHARSET_NAME);
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
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(Constants.AES_KEY.getBytes(StandardCharsets.US_ASCII), Constants.AES_ALGORITHM);
            //CBC模式偏移量IV
            IvParameterSpec iv = new IvParameterSpec(Constants.OFFSET.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解码
            byte[] buffer = new BASE64Decoder().decodeBuffer(data);
            byte[] encrypted = cipher.doFinal(buffer);
            return new String(encrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        if (StringUtil.isBlank(phone)) {
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
        if (StringUtil.isBlank(email)) {
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
        if (StringUtil.isBlank(idCard)) {
            return null;
        }
        return  idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2");
    }
}
