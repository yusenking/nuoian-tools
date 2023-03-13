package com.nuoian.common.utils.file;

import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.NumberUtils;
import com.nuoian.core.datatype.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:41
 * @Description: 文件处理工具
 * @Package: com.nuoian.common.utils.file
 * @Version: 1.0
 */
@Slf4j
@Component
public class FileUtils {

    /**
     * 功能描述:
     * 〈Base64文件上传〉
     * @param base64      base64字符串
     * @param mfiles      文件名
     * @param returnfiles 访问url
     * @param path        文件上传路径
     * @return web访问的url
     * @author: 吴宇森
     * @date: 2022/4/2 16:17
     */
    public static String base64FileUpload(String base64, String mfiles, String returnfiles, String path) {
        try {
            MultipartFile mf = Base64ToMultipartFile.base64ToMultipart(base64);
            String filename = path + mfiles;
            File file = new File(filename);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    log.warn("创建失败");
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            assert mf != null;
            os.write(mf.getBytes());
            os.flush();
            os.close();
            return returnfiles + mfiles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈删除服务上的文件〉
     * @param file 文件
     * @return: void
     * @author: 吴宇森
     * @date: 2022/2/22 17:43
     */
    public static void deleteServerFile(File file) {
        if (file.exists() && file.isFile() && file.delete()) {
            log.info("文件【" + file.getName() + "】删除成功");
        } else {
            log.info("文件【" + file.getName() + "】删除失败");
        }
    }

    /**
     * 功能描述:
     * 〈InputStream转换成File〉
     * @param is 输入流
     * @param fileName 文件名
     * @return: java.io.File
     * @author: 吴宇森
     * @date: 2022/4/2 16:16
     */
    public static File inputStreamToFile(InputStream is, String fileName) {
        File tF = null;
        try {
            tF = new File(fileName);
            inputStreamToFile(tF, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tF;
    }

    private static void inputStreamToFile(File file, InputStream is) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            int i = GlobalConstants.NUMBER_0;
            byte[] b = new byte[GlobalConstants.NUMBER_8192];
            while ((i = is.read(b, 0, GlobalConstants.NUMBER_8192)) != -1) {
                os.write(b, 0, i);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件转base64字符串
     *
     * @param file 文件
     * @return base64字符串
     */
    public static String imgFileToBase64(File file) {
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printBase64Binary(data);

    }

    /**
     * 功能描述:
     * 〈文件输入流转base64文件〉
     *
     * @param in 流
     * @return : java.lang.String
     * @author : 吴宇森
     * @date : 2020/08/21 14:04
     */
    public static String inputStreamToBase64(InputStream in) {
        byte[] data = null;
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printBase64Binary(data);
    }

    /**
     * 功能描述:
     * 〈压缩图片〉
     * @param files 文件
     * @return: org.springframework.web.multipart.MultipartFile
     * @author: 吴宇森
     * @date: 2022/4/2 16:15
     */
    public static MultipartFile compressImage(MultipartFile files) {
        String fileName = StringUtils.mixtureCodeForBidAndSmall(8) + ".png";
        File rfile = new File(fileName);
        File file = null;
        try {
            file=inputStreamToFile(files.getInputStream(),files.getOriginalFilename());
            Thumbnails.of(file)
                    .size(1080, 1980)
                    //.scale(1f)
                    .outputQuality(0.25f)
                    .toFile(rfile);
            return fileToMultipartFile(rfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != file) {
                deleteServerFile(file);
            }
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈缩图片 200k以下〉
     * @param files 文件
     * @return: org.springframework.web.multipart.MultipartFile
     * @author: 吴宇森
     * @date: 2022/4/2 16:15
     */
    public static MultipartFile compressImageByHk(MultipartFile files) {
        String fileName = StringUtils.mixtureCodeForBidAndSmall(8) + ".png";
        File rfile = new File(fileName);
        File file = null;
        try {
            file=inputStreamToFile(files.getInputStream(),files.getOriginalFilename());
            Thumbnails.of(file)
                    .size(600, 400)
                    //.scale(1f)
                    .outputQuality(0.25f)
                    .toFile(rfile);
            return fileToMultipartFile(rfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != file) {
                deleteServerFile(file);
            }
        }
        return null;
    }

    /**
     * 功能描述 :
     * 〈File转Multipart〉
     *
     * @param file 1
     * @return : java.io.File
     * @author : 吴宇森
     * @date : 2020/10/16 14:00
     */
    public static MultipartFile fileToMultipartFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            return new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 功能描述:
     * 〈通过文件地址获取MultipartFile文件〉
     * @param url 文件地址
     * @return: org.springframework.web.multipart.MultipartFile
     * @author: 吴宇森
     * @date: 2022/2/22 17:54
     */
    public static MultipartFile httpGetFile(String url){
        String fileName = url.substring(url.lastIndexOf("."),url.length());
        File file = null;
        URL urlFile;
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url",fileName);
            // 下载
            urlFile = new URL(url);
            inputStream = urlFile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[GlobalConstants.NUMBER_8192];
            while ((bytesRead = inputStream.read(buffer,0,GlobalConstants.NUMBER_8192))!=-1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=os){
                    os.close();
                }
                if(null!=inputStream){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return fileToMultipartFile(file);
    }


    /**
     * 功能描述:
     * 〈获取文件大小(GB/MB/KB)〉
     * @param size 1
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/22 17:53
     */
    public static String getSize(long size) {
        int mx=GlobalConstants.NUMBER_1024 * GlobalConstants.NUMBER_1024 * GlobalConstants.NUMBER_1024;
        int mxi=GlobalConstants.NUMBER_1024 * GlobalConstants.NUMBER_1024;
        StringBuilder bytes = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= mx) {
            double i = NumberUtils.doubleDivide(size , NumberUtils.doubleMultiply(NumberUtils.doubleMultiply(1024.0 , 1024.0) , 1024.0),5);
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= mxi) {
            double i = NumberUtils.doubleDivide(size , NumberUtils.doubleMultiply(1024.0 , 1024.0),5);
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= GlobalConstants.NUMBER_1024) {
            double i = NumberUtils.doubleDivide(size , 1024.0,5);
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < GlobalConstants.NUMBER_1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    /**
     * 功能描述:
     * 〈MultipartFile 转 File〉
     * @param file MultipartFile
     * @return: java.io.File
     * @author: 吴宇森
     * @date: 2022/4/2 16:11
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (null!=file&& file.getSize() >0) {
            InputStream ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 功能描述:
     * 〈获取流文件〉
     * @param ins 文件流
     * @param file 文件
     * @return: void
     * @author: 吴宇森
     * @date: 2022/2/22 17:46
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[GlobalConstants.NUMBER_8192];
            while ((bytesRead = ins.read(buffer, 0, GlobalConstants.NUMBER_8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:
     * 〈删除本地临时文件〉
     * @param file 文件
     * @return: void
     * @author: 吴宇森
     * @date: 2022/4/2 16:12
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * 功能描述:
     * 〈逐行读取文件〉
     * @param path 文件位置
     * @return: java.util.List<java.lang.String>
     * @author: 吴宇森
     * @date: 2022/4/2 16:12
     */
    public static List<String> readFileLineByLine(String path){
        BufferedReader br=null;
        List<String> datas=new ArrayList<>();
        try{
            br=new BufferedReader(new FileReader(path));
            String data=null;
            while (!StringUtils.isNull(data=br.readLine())){
                datas.add(data);
            }
        }catch (IOException e){
            log.error("逐行读取文件失败：",e);
        }finally {
            if(!StringUtils.isNull(br)){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    public static String uploadFile(MultipartFile img,String realPath,String virtualPath) {
        String fileName = img.getOriginalFilename();
        if (!"".equals(fileName)) {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            DateFormat dfT = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            realPath += "/" + dfT.format(calendar.getTime());

            assert fileName != null;
            fileName = df.format(calendar.getTime()) + ((int) (Math.random() * 9000) + 1000) + fileName.substring(fileName.lastIndexOf("."));
            File targetFile = new File(realPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            //保存
            try {
                File upFile = new File(realPath, fileName);
                img.transferTo(upFile);
                return "/" + virtualPath + "/" + dfT.format(calendar.getTime()) + "/" + fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈上传文件到本地〉
     * @param files 文件
     * @param fileName 文件名
     * @param fileFolder 文件夹
     * @return boolean
     * @author 吴宇森
     * @date 2023/1/11 15:20
     */
    public static boolean uploadFileToLocal(MultipartFile files,String fileName,String fileFolder){
        String filename = fileFolder +"/"+ fileName;
        log.info("文件名："+filename);
        File file = new File(filename);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                log.warn("创建失败");
            }
        }
        //保存
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            files.transferTo(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
