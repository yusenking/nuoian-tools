package com.nuoian.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONObject;
import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 16:25
 * @Description: Http请求工具类
 * @Package: com.haplink.core.http
 * @Version: 1.0
 */
@Slf4j
public class HttpUtils {

    /**
     * 功能描述:
     * 〈Get请求〉
     *
     * @param url   请求地址
     * @param isSsl 是否忽略SSL证书
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/2 16:27
     */
    public static String httpGetRequest(String url, Boolean isSsl) {
        log.info("Get请求地址：" + url);
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            if (isSsl) {
                //配置，发送https请求时，忽略ssl证书认证（否则会报错没有证书）
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                }).build();
                //创建httpClient
                client = HttpClients.custom().setSSLContext(sslContext).
                        setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            } else {
                client = HttpClients.createDefault();
            }
            HttpGet get = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(30000)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(30000)
                    // 允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            get.setConfig(config);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            response = client.execute(get);
            log.info("Get请求返回：" + response.toString());
            if (!StringUtils.isNull(response) && response.getStatusLine().getStatusCode() == GlobalConstants.NUMBER_200) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("Get请求异常:", e);
        } finally {
            if (null != client) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈Post请求〉
     *
     * @param url       请求地址
     * @param reqHeader 请求头
     * @param paramMap  请求数据
     * @param isSsl     是否忽略SSL证书
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/2 16:31
     */
    public static String httpPostRequest(String url, JSONObject reqHeader, JSONObject paramMap, Boolean isSsl) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        String returnStr = null;
        try {
            if (isSsl) {
                //配置，发送https请求时，忽略ssl证书认证（否则会报错没有证书）
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                }).build();
                //创建httpClient
                client = HttpClients.custom().setSSLContext(sslContext).
                        setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            } else {
                client = HttpClients.createDefault();
            }
            HttpPost post = new HttpPost(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(30000)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(30000)
                    //允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            post.setConfig(config);
            post.setHeader("Accept", "*/*");
            post.setHeader("Content-Encoding", "gzip, deflate, br");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.8,fi;q=0.6,en;q=0.4");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            if (!StringUtils.isNull(reqHeader)) {
                for (String s : reqHeader.keySet()) {
                    post.setHeader(s, reqHeader.get(s).toString());
                }
            }
            post.setEntity(new StringEntity(paramMap.toJSONString(), ContentType.create("application/json", "utf-8")));
            response = client.execute(post);
            if (response != null && (
                    response.getStatusLine().getStatusCode() == GlobalConstants.NUMBER_200||
                    response.getStatusLine().getStatusCode() == GlobalConstants.NUMBER_201
            )) {
                returnStr = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("Post请求异常：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!StringUtils.isNull(client)) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnStr;
    }

    /**
     * 功能描述:
     * 〈发送Http请求〉
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方法
     * @param outputStr     参数
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/2 16:28
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    /**
     * 功能描述:
     * 〈form表单提交〉
     *
     * @param url       请求地址
     * @param reqHeader 请求头
     * @param paramMap  请求参数
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/2 16:29
     */
    public static String httpPostForm(String url, JSONObject reqHeader, JSONObject paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = null;
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig config = RequestConfig.custom()
                // 设置连接主机服务超时时间
                .setConnectTimeout(30000)
                // 设置连接请求超时时间
                .setConnectionRequestTimeout(30000)
                // 设置读取数据连接超时时间
                .setSocketTimeout(30000)
                //允许重定向
                .setRedirectsEnabled(true)
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(config);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        if (!StringUtils.isNull(reqHeader)) {
            for (Map.Entry<String, Object> entry : reqHeader.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        // 封装post请求参数
        if (!StringUtils.isNull(paramMap) && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == GlobalConstants.NUMBER_200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            log.info("httpPostForm", e);
        } finally {
            // 关闭资源
            if (!StringUtils.isNull(httpResponse)) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈上传文件〉
     *
     * @param url          1
     * @param file         2
     * @param headerParams 3
     * @param params       4
     * @return: java.lang.String
     * @author: LeiZiLin
     * @date: 2021/8/26 15:08
     */
    public static JSONObject uploadFile(String url, MultipartFile file, String fileParamName, Map<String, String> headerParams, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            // 添加header
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            for (Map.Entry<String, String> p : params.entrySet()) {
                builder.addTextBody(p.getKey(), p.getValue());
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // httpClient对象执行post请求,并返回响应参数对象
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == GlobalConstants.NUMBER_200) {
                return JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 功能描述:
     * 〈Get请求并下载文件〉
     *
     * @param url      1
     * @param filename 2
     * @param folder   3
     * @return: void
     * @author: 吴宇森
     * @date: 2022/2/22 18:26
     */
    public static void httpGetFile(String url, String filename, String folder) {
        log.info("Get请求地址：" + url);
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(30000)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(30000)
                    // 允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            get.setConfig(config);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            if (!StringUtils.isNull(httpEntity)) {
                String path = folder + filename;
                InputStream is = httpEntity.getContent();
                File file = new File(path);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] b = new byte[4096];
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    fileOutputStream.write(b, 0, len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                is.close();
                log.info("{} 下载成功", filename);
            }
        } catch (Exception e) {
            log.error("Get请求异常:", e);
        } finally {
            if (!StringUtils.isNull(client)) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能描述:
     * 〈get下载文件〉
     *
     * @param url            1
     * @param filename       2
     * @param folder         3
     * @param requestTimeout 4
     * @param socketTimeout  5
     * @return: boolean
     * @author: 吴宇森
     * @date: 2022/2/22 18:25
     */
    public static boolean httpGetFile(String url, String filename, String folder, int requestTimeout, int socketTimeout) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(requestTimeout)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(socketTimeout)
                    // 允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            get.setConfig(config);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            if (!StringUtils.isNull(httpEntity)) {
                String path = folder + filename;
                InputStream is = httpEntity.getContent();
                File file = new File(path);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] b = new byte[4096];
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    fileOutputStream.write(b, 0, len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                is.close();
                log.info("{} 下载成功", filename);

                return true;
            }
        } catch (Exception e) {
            log.error("Get请求异常:", e);
        } finally {
            if (!StringUtils.isNull(client)) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 功能描述:
     * 〈n8n 的Get请求方法〉
     * @param url 请求地址
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2021/10/27 16:28
     */
    public static String n8nHttpGetRequest(String url) {
        String pwd="haplink:Eai38eBMDSlsQPO";
        log.info("n8nGet请求地址："+url);
        String returnStr = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = wrapClient();
            HttpGet get = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(30000)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(30000)
                    // 允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            get.setConfig(config);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            get.setHeader("Authorization","Basic "+StringUtils.stringToBase64(pwd));
            response = client.execute(get);
            log.info("Get请求返回："+response.toString());
            if (null!=response && response.getStatusLine().getStatusCode() == 200) {
                returnStr = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            returnStr = null;
            log.error("Get请求异常:");
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != client) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnStr;
    }

    /**
     * 功能描述:
     * 〈n8n 的Post请求方法〉
     * @param url 请求地址
     * @author: 吴宇森
     * @date: 2021/10/27 16:27
     */
    public static void n8nHttpPostRequest(String url) {
        String pwd="haplink:Eai38eBMDSlsQPO";
        log.info("n8nPost请求地址："+url);
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = wrapClient();
            HttpPost post = new HttpPost(url);
            RequestConfig config = RequestConfig.custom()
                    // 设置连接主机服务超时时间
                    .setConnectTimeout(30000)
                    // 设置连接请求超时时间
                    .setConnectionRequestTimeout(30000)
                    // 设置读取数据连接超时时间
                    .setSocketTimeout(30000)
                    // 允许重定向
                    .setRedirectsEnabled(true)
                    .build();
            post.setConfig(config);
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            post.setHeader("Authorization","Basic "+ StringUtils.stringToBase64(pwd));
            response = client.execute(post);
            log.info("n8nPost请求返回："+response.toString());
        } catch (Exception e) {
            log.error("n8nPost请求异常:");
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != client) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static CloseableHttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);
            return HttpClients.custom()
                    .setSSLSocketFactory(ssf).build();
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public static final String X_FORWARDED_FOR = "x-forwarded-for";
    public static final String UNKNOWN = "unknown";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String X_REAL_IP = "X-Real-IP";

    /**
     * 功能描述:
     * 〈根据request获取用户IP〉
     *
     * @param request 1
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/12 18:38
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (LOCALHOST_IP.equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(GlobalConstants.COMMA).length > 1) {
            ip = ip.split(GlobalConstants.COMMA)[0];
        }
        return ip;
    }

    /**
     * 功能描述:
     * 〈根据request获取用户IP〉
     *
     * @param request 1
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/12 18:38
     */
    public static String getIpAddrForServer(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst(X_FORWARDED_FOR);
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(GlobalConstants.COMMA) != -1) {
                ip = ip.split(GlobalConstants.COMMA)[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_X_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(X_REAL_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        if (LOCALHOST_IP.equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(GlobalConstants.COMMA).length > 1) {
            ip = ip.split(GlobalConstants.COMMA)[0];
        }
        return ip;
    }
    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        return httpClient;
    }
    /**
     * Post String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * post form
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }

    /**
     * get
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }
    /**
     * Put String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }
}
