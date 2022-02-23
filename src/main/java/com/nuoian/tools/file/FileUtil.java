package com.nuoian.tools.file;

import com.nuoian.tools.constants.Constants;
import com.nuoian.tools.context.FileProperties;
import com.nuoian.tools.datatype.NumberUtil;
import com.nuoian.tools.datatype.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.apache.http.entity.ContentType;
import net.coobird.thumbnailator.Thumbnails;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/22 17:41
 * @Description: 文件处理工具
 * @Package: com.nuoian.tools.file
 * @Version: 1.0
 */
@Slf4j
@Component
public class FileUtil {
    private final FileProperties fileProperties;

    private String FTP_USER;

    private String FTP_PASSWORD;

    private String FTP_HOST;

    private Integer FTP_PORT;

    private String FILE_VISIT_PATH;

    public FileUtil(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @PostConstruct
    public void initProperties(){
        FTP_USER= fileProperties.getFtp().getUser();
        FTP_PASSWORD=fileProperties.getFtp().getPassword();
        FTP_HOST=fileProperties.getFtp().getHost();
        FTP_PORT=fileProperties.getFtp().getPort();
        FILE_VISIT_PATH=fileProperties.getVisitPath();
    }

    /**
     * 上传文件
     *
     * @param base64      base64字符串
     * @param mfiles      文件名
     * @param returnfiles 访问url
     * @param path        文件上传路径
     * @return web访问的url
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
            /*//权限
            Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
            perms.add(PosixFilePermission.OTHERS_READ); //设置其他的读取权限
            Files.setPosixFilePermissions(file.toPath(), perms);  //修改文件的权限*/
            /*Runtime.getRuntime().exec("chmod 755 "+filename);*/
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
     * InputStream转换成File
     *
     * @param is 输入流
     * @return 文件
     */
    public static File InputStreamToFile(InputStream is, String fileName) {
        File tF = null;
        try {
            tF = new File(fileName);
            InputStreamToFile(tF, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tF;
    }

    private static void InputStreamToFile(File file, InputStream is) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            int i = 0;
            byte[] b = new byte[8192];
            while ((i = is.read(b, 0, 8192)) != -1) {
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
    public static String ImgFileToBase64(File file) {
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
     * 普通文件使用FTP上传到服务器
     *
     * @param folder   文件夹
     * @param fileName 文件名
     * @param file     文件
     * @param base64   base64字符串；；、‘
     * @return
     */
    public  String uploadFileForFTP(String folder, String fileName, MultipartFile file, String base64) {
        try {
            if (StringUtil.isNull(file) || file.getSize() == 0) {
                if (base64.length() > 0) {
                    file = Base64ToMultipartFile.base64ToMultipart(base64);
                } else {
                    return null;
                }
            }
            log.info("文件大小：" + file.getSize());
            FTPClient ftp = new FTPClient();
            ftp.connect(FTP_HOST, FTP_PORT);
            ftp.login(FTP_USER, FTP_PASSWORD);
            int code = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(code)) {
                String LOCAL_CHARSET = "GBK";
                // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
                    LOCAL_CHARSET = "UTF-8";
                }
                ftp.setControlEncoding(LOCAL_CHARSET);
                StringBuilder sb = new StringBuilder();
                //多级目录 循环创建
                String[] folders = folder.split("/");
                for (String s : folders) {
                    if ("".equals(s)) {
                        continue;
                    }
                    sb.append(s).append("/");
                    //移动到文件夹中
                    if (!ftp.changeWorkingDirectory(s)) {
                        //判断文件夹是否存在，不存在则创建
                        boolean flag = ftp.makeDirectory(s);
                        log.info("创建文件夹：" + s + " " + flag);
                        //移动到文件夹中
                        boolean flag2 = ftp.changeWorkingDirectory(s);
                        log.info("移动文件夹：" + flag2);
                    } else {
                        log.info("文件夹：" + s + " 存在");
                    }
                }
                //设置文件上传类型为二进制类型
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                ftp.enterLocalPassiveMode();
                String wjj = sb.toString();
                String nFileName = new String(fileName.getBytes(LOCAL_CHARSET),
                        StandardCharsets.ISO_8859_1);
                if (ftp.storeFile(nFileName, bis)) {
                    log.info("文件上传成功，文件名：" + fileName);
                    bis.close();
                    ftp.logout();
                    //拼接文件访问路径
                    return FILE_VISIT_PATH + wjj + fileName;
                }
                bis.close();
                ftp.logout();
                return null;
            }
            return null;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈FTP下载指定路径文件〉
     * @param filePath 文件相对路径
     * @param fileName 文件名
     * @return: org.springframework.web.multipart.MultipartFile
     * @author: LeiZiLin
     * @date: 2021/9/7 10:28
     */
    public  MultipartFile downFileForFTP(String filePath, String fileName){
        InputStream inputStream = null;
        try {
            if(filePath==null&&fileName==null){
                log.warn("文件路径及文件名不能为空");
                return null;
            }
            FTPClient ftp = new FTPClient();
            ftp.connect(FTP_HOST, FTP_PORT);
            ftp.login(FTP_USER, FTP_PASSWORD);
            int code = ftp.getReplyCode();
            if(FTPReply.isPositiveCompletion(code)) {
                String LOCAL_CHARSET = "GBK";
                // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                if (FTPReply.isPositiveCompletion(ftp.sendCommand(Constants.CHARSET_UTF_OPTS, Constants.ON))) {
                    LOCAL_CHARSET = Constants.CHARSET_NAME;
                }
                ftp.setControlEncoding(LOCAL_CHARSET);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(filePath);
                inputStream = ftp.retrieveFileStream(fileName);
            }
            if (inputStream != null) {
                log.info("FTP下载文件执行成功！！！");
                return new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(),inputStream);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            log.error("未找到指定路径文件："+filePath);
            fileNotFoundException.printStackTrace();
        } catch (Exception e){
            log.error("FTP下载文件 - 执行异常："+e.getMessage());
            e.printStackTrace();
        }
        log.info("FTP下载文件执行失败！！！");
        return null;
    }

    /**
     * 压缩图片
     *
     * @param files
     * @return
     */
    public static MultipartFile compressImage(MultipartFile files) {
        String fileName = StringUtil.mixtureCodeForBidAndSmall(8) + ".png";
        File rfile = new File(fileName);
        File file = null;
        try {
            file=InputStreamToFile(files.getInputStream(),files.getOriginalFilename());
            Thumbnails.of(file)
                    .size(1080, 1980)
                    //.scale(1f)
                    .outputQuality(0.25f)
                    .toFile(rfile);
            return FileToMultipartFile(rfile);
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
     * 压缩图片 200k以下
     *
     * @param files
     * @return
     */
    public static MultipartFile compressImageByHk(MultipartFile files) {
        String fileName = StringUtil.mixtureCodeForBidAndSmall(8) + ".png";
        File rfile = new File(fileName);
        File file = null;
        try {
            file=InputStreamToFile(files.getInputStream(),files.getOriginalFilename());
            Thumbnails.of(file)
                    .size(600, 400)
                    //.scale(1f)
                    .outputQuality(0.25f)
                    .toFile(rfile);
            return FileToMultipartFile(rfile);
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
    public static MultipartFile FileToMultipartFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过图片地址获取MultipartFile文件
     * @param url 图片地址
     * @return
     */
    public static MultipartFile httpFileByUrl(String url){
        try {
            log.info("开始获取图片文件 - 图片地址："+url);
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(),inStream);
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
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer,0,8192))!=-1){
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
        return FileToMultipartFile(file);
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
        int mx=Constants.NUMBER_1024 * Constants.NUMBER_1024 * Constants.NUMBER_1024;
        int mxi=Constants.NUMBER_1024 * Constants.NUMBER_1024;
        StringBuilder bytes = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= mx) {
            double i = NumberUtil.doubleExcept(size , NumberUtil.doubleRide(NumberUtil.doubleRide(1024.0 , 1024.0) , 1024.0),5);
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= mxi) {
            double i = NumberUtil.doubleExcept(size , NumberUtil.doubleRide(1024.0 , 1024.0),5);
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= Constants.NUMBER_1024) {
            double i = NumberUtil.doubleExcept(size , 1024.0,5);
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < Constants.NUMBER_1024) {
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
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
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
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    public static List<String> readFileLineByLine(String path){
        BufferedReader br=null;
        List<String> datas=new ArrayList<>();
        try{
            br=new BufferedReader(new FileReader(path));
            String data=null;
            while (!StringUtil.isNull(data=br.readLine())){
                datas.add(data);
            }
        }catch (IOException e){
            log.error("逐行读取文件失败：",e);
        }finally {
            if(!StringUtil.isNull(br)){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }
}
