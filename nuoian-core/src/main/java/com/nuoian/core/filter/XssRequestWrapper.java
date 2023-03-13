package com.nuoian.core.filter;

import com.nuoian.core.datatype.StringUtils;
import com.nuoian.core.jwt.JwtInfo;
import com.nuoian.core.jwt.JwtPayload;
import com.nuoian.core.jwt.JwtUtils;
import com.nuoian.core.request.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.validator.html.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 14:38
 * @Description: 采用OWASP的一个开源的项目AntiSamy来解决XSS攻击问题。
 * @Package: com.nuoian.core.filter
 * @Version: 1.0
 */
@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> requestMap = super.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : requestMap.entrySet()) {
            Map.Entry me = stringEntry;
            String[] values = (String[]) me.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = xssClean(values[i]);
            }
        }
        return requestMap;
    }

    @Override
    public String[] getParameterValues(String paramString) {
        String[] arrayOfString1 = super.getParameterValues(paramString);
        if (arrayOfString1 == null) {
            return null;
        }
        int i = arrayOfString1.length;
        String[] arrayOfString2 = new String[i];
        for (int j = 0; j < i; j++) {
            arrayOfString2[j] = xssClean(arrayOfString1[j]);
        }
        return arrayOfString2;
    }

    @Override
    public String getParameter(String paramString) {
        String str = super.getParameter(paramString);
        if (str == null) {
            return null;
        }
        return xssClean(str);
    }

    @Override
    public String getHeader(String paramString) {
        String str = super.getHeader(paramString);
        if (str == null) {
            return null;
        }
        return xssClean(str);
    }

    private static Policy policy = null;

    static {
        try {
            InputStream is = XssRequestWrapper.class.getClassLoader().getResource("antisamy-myspace.xml").openStream();
            policy = Policy.getInstance(is);
        } catch (PolicyException e) {
            log.error("policy实例化异常：", e);
        } catch (IOException e) {
            log.error("policy实例化IO异常：", e);
        }
    }

    /**
     * 过滤xss共计内容
     * 自定义过滤特殊字符
     *
     * @param value 需要过滤的输入项
     * @return 过滤后的内容
     */
    private String xssClean(String value) {
        AntiSamy antiSamy = new AntiSamy();
        List<String> errorStr = null;
        try {
            final CleanResults cr = antiSamy.scan(value, policy);
            // 异常信息
            errorStr = cr.getErrorMessages();
            // 安全的HTML输出
            String str = StringEscapeUtils.unescapeHtml4(cr.getCleanHTML());

            // 对其他特殊安全问题的过滤
            StringBuilder sb = new StringBuilder(str.length() + 16);
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                switch (c) {
                    case '>':
                        //全角大于号
                        sb.append('＞');
                        break;
                    case '<':
                        //全角小于号
                        sb.append('＜');
                        break;
                    case '&':
                        //全角
                        sb.append('＆');
                        break;
                    case '\\':
                        //全角斜线
                        sb.append('＼');
                        break;
                    case '#':
                        //全角井号
                        sb.append('＃');
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            return sb.toString();
        } catch (ScanException e) {
            log.error("antiSamy扫描异常：", e);
        } catch (PolicyException e) {
            log.error("policy异常：", e);
        } finally {
            if (errorStr != null && errorStr.size() > 0) {
                String userName = "未知用户";
                HttpServletRequest request = RequestUtils.getRequest();
                if (!StringUtils.isNull(request)) {
                    JwtInfo jwtInfo = JwtUtils.getTokenToHttpServlet(request);
                    JwtPayload payload = JwtUtils.analysisJwtPayload(jwtInfo.getToken());
                    userName = payload.getUserName();
                }

                log.warn("用户：{}在尝试录入非法信息->[{}]",
                        userName,
                        StringUtils.collectionToString(errorStr, ","));
            }
        }
        return value;
    }
}
