package com.nuoian.common.utils.file;

import com.nuoian.core.constants.GlobalConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:36
 * @Description: base64转MultipartFile
 * @Package: com.nuoian.common.utils.file
 * @Version: 1.0
 */
public class Base64ToMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String header;

    public Base64ToMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes(){
        return imgContent;
    }

    @Override
    public InputStream getInputStream(){
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    /**
     * 功能描述:
     * 〈base64转文件〉
     * @param base64 base64字符串
     * @return: org.springframework.web.multipart.MultipartFile
     * @author: 吴宇森
     * @date: 2022/2/22 17:41
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            byte[] b = DatatypeConverter.parseBase64Binary(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new Base64ToMultipartFile(b, baseStrs[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char LAST_2_BYTE = (char) Integer.parseInt("00000011", 2);
    private static final char LAST_4_BYTE = (char) Integer.parseInt("00001111", 2);
    private static final char LAST_6_BYTE = (char) Integer.parseInt("00111111", 2);
    private static final char LEAD_6_BYTE = (char) Integer.parseInt("11111100", 2);
    private static final char LEAD_4_BYTE = (char) Integer.parseInt("11110000", 2);
    private static final char LEAD_2_BYTE = (char) Integer.parseInt("11000000", 2);
    private static final char[] ENCODE_TABLE = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /**
     * 功能描述:
     * 〈字节流转为base64〉
     * @param from byte数组
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/22 17:41
     */
    public static String encodeToBase64(byte[] from) {
        StringBuilder to = new StringBuilder((int) ((double) from.length * 1.34D) + 3);
        int num = 0;
        char currentByte = 0;

        int i;
        for (i = 0; i < from.length; ++i) {
            for (num %= GlobalConstants.NUMBER_8; num < GlobalConstants.NUMBER_8; num += GlobalConstants.NUMBER_6) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & LEAD_6_BYTE);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & LAST_6_BYTE);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & LAST_4_BYTE);
                        currentByte = (char) (currentByte << 2);
                        if (i + 1 < from.length) {
                            currentByte = (char) (currentByte | (from[i + 1] & LEAD_2_BYTE) >>> GlobalConstants.NUMBER_6);
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & LAST_2_BYTE);
                        currentByte = (char) (currentByte << GlobalConstants.NUMBER_4);
                        if (i + 1 < from.length) {
                            currentByte = (char) (currentByte | (from[i + 1] & LEAD_4_BYTE) >>> GlobalConstants.NUMBER_4);
                        }
                    case 3:
                    case 1:
                    case 5:
                    default:
                        break;
                }

                to.append(ENCODE_TABLE[currentByte]);
            }
        }

        if (to.length() % GlobalConstants.NUMBER_4 != 0) {
            for (i = GlobalConstants.NUMBER_4 - to.length() % GlobalConstants.NUMBER_4; i > 0; --i) {
                to.append("=");
            }
        }

        return to.toString();
    }
}
