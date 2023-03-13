package com.nuoian.core.request;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.core.datatype.StringUtils;
import com.nuoian.core.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @Author: 吴宇森
 * @Date: 2020/07/08 13:35
 * @Description: RestTemplate公共类
 * @Package: com.nuoian.consumer.common.utils
 * @Version: 1.0
 */
@Slf4j
@Component
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 功能描述:
     * 〈发送请求〉
     * @param path 请求地址
     * @param method 请求类型
     * @param params 请求参数
     * @param headers 请求头
     * @param callable 类
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:12
     */
    private <T> T  resultDatas(String path,HttpMethod method,MultiValueMap<String, Object> params,HttpHeaders headers,Class<T> callable){
        ResponseEntity<String> responseEntity = restTemplate.exchange(path, method, new HttpEntity<>(params, headers), String.class);
        String json = responseEntity.getBody();
        if(StringUtils.isBlank(json)){
            return null;
        }
        return JSONObject.parseObject(json, callable);
    }

    /**
     * 功能描述:
     * 〈发送请求〉
     * @param path 请求地址
     * @param method 请求类型
     * @param params 请求参数
     * @param headers 请求头
     * @param callable 类
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:12
     */
    private <T> T  resultDatas(String path,HttpMethod method,JSONObject params,HttpHeaders headers,Class<T> callable){
        ResponseEntity<String> responseEntity = restTemplate.exchange(path, method, new HttpEntity<>(params.toJSONString(), headers), String.class);
        String json = responseEntity.getBody();
        if(StringUtils.isBlank(json)){
            return null;
        }
        return JSONObject.parseObject(json, callable);
    }

    /**
     * 功能描述:
     * 〈组装请求头信息〉
     * @param request 请求信息
     * @param type 请求头类型
     * @return org.springframework.http.HttpHeaders
     * @author 吴宇森
     * @date 2023/3/10 15:11
     */
    private HttpHeaders assembleHeaders(HttpServletRequest request,MediaType type){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headers.add(headerNames.nextElement(), request.getHeader(headerNames.nextElement()));
        }
        return headers;
    }

    /**
     * 功能描述:
     * 〈POST FROM 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:10
     */
    public <T> T post(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotNull(datas)) {
            datas.keySet().forEach(res -> params.add(res, datas.get(res)));
        }
        return resultDatas(url,HttpMethod.POST,params,assembleHeaders(request,MediaType.APPLICATION_FORM_URLENCODED),callable);
    }

    /**
     * 功能描述:
     * 〈POST JSON 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:10
     */
    public <T> T postToJson(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        return resultDatas(url,HttpMethod.POST,datas,assembleHeaders(request,MediaType.APPLICATION_JSON),callable);
    }

    /**
     * 功能描述:
     * 〈GET 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:09
     */
    public <T> T get(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        return resultDatas(url,HttpMethod.GET,datas,assembleHeaders(request,MediaType.APPLICATION_JSON),callable);
    }

    /**
     * 功能描述:
     * 〈DELETE FROM 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:09
     */
    public <T> T delete(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotNull(datas)) {
            datas.keySet().forEach(res -> params.add(res, datas.get(res)));
        }
        return resultDatas(url,HttpMethod.DELETE,params,assembleHeaders(request,MediaType.APPLICATION_FORM_URLENCODED),callable);
    }

    /**
     * 功能描述:
     * 〈DELETE JSON 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:08
     */
    public <T> T deleteToJson(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        return resultDatas(url,HttpMethod.DELETE,datas,assembleHeaders(request,MediaType.APPLICATION_JSON),callable);
    }

    /**
     * 功能描述:
     * 〈PUT FROM 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:08
     */
    public <T> T update(String url, JSONObject datas, Class<T> callable, HttpServletRequest request) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotNull(datas)) {
            datas.keySet().forEach(res -> params.add(res, datas.get(res)));
        }
        return resultDatas(url,HttpMethod.PUT,params,assembleHeaders(request,MediaType.APPLICATION_FORM_URLENCODED),callable);
    }

    /**
     * 功能描述:
     * 〈PUT JSON 请求〉
     * @param url 请求地址
     * @param datas 请求参数
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:07
     */
    public <T> T updateToJson(String url, JSONObject datas,Class<T> callable,  HttpServletRequest request) {
        return resultDatas(url,HttpMethod.PUT,datas,assembleHeaders(request,MediaType.APPLICATION_JSON),callable);
    }


    /**
     * 功能描述:
     * 〈POST 请求上传文件〉
     * @param url 请求地址
     * @param file 文件
     * @param callable 类
     * @param request 请求信息
     * @return T
     * @author 吴宇森
     * @date 2023/3/10 15:06
     */
    public <T> T postUploadFile(String url, MultipartFile file, Class<T> callable, HttpServletRequest request) throws IOException {
        HttpHeaders fileHeader = new HttpHeaders();
        fileHeader.setContentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())));
        fileHeader.setContentDispositionFormData("file", file.getOriginalFilename());
        HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(new ByteArrayResource(file.getBytes()), fileHeader);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", fileEntity);
        return resultDatas(url,HttpMethod.PUT,params,assembleHeaders(request,MediaType.MULTIPART_FORM_DATA),callable);
    }


    /**
     * 功能描述: 
     * 〈POST 下载文件〉
     * @param url 请求地址
     * @param datas 请求数据
     * @param request 请求信息
     * @param response 返回体
     * @author 吴宇森
     * @date 2023/3/10 14:43
     */
    public void postDownload(String url, JSONObject datas, HttpServletRequest request, HttpServletResponse response) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(datas.toJSONString(), assembleHeaders(request,MediaType.APPLICATION_JSON));
        ResponseEntity<byte []> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte [].class);
        try {
            response.setContentType("application/force-download");
            //设置reponse响应头，真实文件名重命名，就是在这里设置，设置编码
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", responseEntity.getHeaders().getFirst("Content-Disposition"));
            response.getOutputStream().write(responseEntity.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
