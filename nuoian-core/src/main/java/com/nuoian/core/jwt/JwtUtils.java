package com.nuoian.core.jwt;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.core.constants.GlobalConstants;
import com.nuoian.core.datatype.StringUtils;
import com.nuoian.core.date.DateUtils;
import com.nuoian.core.encrypt.EncryptionUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.operator.MacCalculatorProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/12 19:18
 * @Description: JWT工具
 * @Package: com.nuoian.core.jwt
 * @Version: 1.0
 */
@Slf4j
public class JwtUtils {

    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    /**
     * JWT 生成密钥使用的密码
     */
    private static final String JWT_RULE = "ZQ6xM-PxRc-T";

    /**
     * 密钥：通过生成 JWT_ALG 和 JWT_RULE 加密生成
     */
    private static final Key SECRET = generateKey(JWT_ALG, JWT_RULE);

    /**
     * 默认jwt所面向的分组用户
     */
    public static final String DEFAULT_SUB = "Nuoian-sys";

    /**
     * 签发者
     */
    public static final String SIGN_AND_ISSUE = "Nuoian";

    /**
     * 功能描述:
     * 〈使用指定密钥生成规则，生成JWT加解密密钥〉
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return: javax.crypto.SecretKey
     * @author: 吴宇森
     * @date: 2022/8/30 16:09
     */
    private static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64.decodeBase64(rule);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, alg.getJcaName());
    }

    /**
     * 功能描述:
     * 〈生成JWT字符串〉
     *
     * @param payload  用户自定义数据
     * @param aud      jwt 接收方
     * @param tid      jwt 唯一身份标识
     * @param duration jwt 有效时间，单位：秒
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 11:31
     */
    public static String buildJwt(JwtPayload payload, String aud, String tid, Long duration) {
        Date nbf = DateUtils.getNowDateTime();
        // jwt的签发时间
        long iat = System.currentTimeMillis();
        // jwt的过期时间，这个过期时间必须要大于签发时间
        long exp = 0;
        if (duration != null) {
            exp = (nbf == null ? (iat + duration * 1000) : (nbf.getTime() + duration * 1000));
        }
        String token = Jwts.builder()
                .claim("data", EncryptionUtils.aesEncrypt(JSONObject.toJSONString(payload)))
                //加密方式
                .signWith(JWT_ALG, SECRET)
                //主题
                .setSubject(DEFAULT_SUB)
                //接收方
                .setAudience(aud)
                //唯一标识
                .setId(tid)
                //签发者信息
                .setIssuer(SIGN_AND_ISSUE)
                //生效日期
                .setNotBefore(nbf)
                //签发时间
                .setIssuedAt(new Date(iat))
                //过期时间
                .setExpiration(new Date(exp))
                .compact();
        return GlobalConstants.TOKEN_PREFIX + token;
    }

    /**
     * 功能描述:
     * 〈解析并验证token〉
     *
     * @param jwt token
     * @param aud 被签发者信息
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 14:58
     */
    public static boolean checkJwt(String jwt, String aud, boolean isOnly) {
        Claims claims = getClaimsFromToken(jwt);
        boolean flag = StringUtils.isNull(claims) || !DEFAULT_SUB.equals(claims.getSubject());
        //判断是否需要唯一标识
        if (isOnly) {
            flag = StringUtils.isNull(claims) || !claims.getAudience().equals(aud) || !DEFAULT_SUB.equals(claims.getSubject());
        }
        return !flag;
    }


    /**
     * 从 token 中获取 JWT 的 payload 部分
     *
     * @param token token
     * @return {@link Claims}
     */
    private static Claims getClaimsFromToken(String token) {
        // 解析 JWT 字符串
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 功能描述:
     * 〈获取请求头中的token〉
     *
     * @param request 请求信息
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 17:26
     */
    public static JwtInfo getTokenToServerHttp(ServerHttpRequest request) {
        if (StringUtils.isNull(request)) {
            return null;
        }
        String token = request.getHeaders().getFirst(GlobalConstants.HEADER);
        return getTokenInfo(token);
    }

    /**
     * 功能描述:
     * 〈获取请求头中的token〉
     *
     * @param request 请求信息
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/4/13 17:26
     */
    public static JwtInfo getTokenToHttpServlet(HttpServletRequest request) {
        if (StringUtils.isNull(request)) {
            return null;
        }
        String token = request.getHeader(GlobalConstants.HEADER);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return getTokenInfo(token);
    }

    private static JwtInfo getTokenInfo(String token) {
        JwtInfo res = new JwtInfo();
        res.setCode(0);
        //token不存在
        if (StringUtils.isNull(token) || StringUtils.isBlank(token)) {
            res.setCode(-1);
            return res;
        }
        res.setToken(token.replace(GlobalConstants.TOKEN_PREFIX, GlobalConstants.BLANK));
        res.setBody(token);
        return res;
    }

    /**
     * 功能描述:
     * 〈解析JWT数据〉
     *
     * @param jwt JWT
     * @return: com.haplink.core.jwt.JwtPayload
     * @author: 吴宇森
     * @date: 2022/8/8 18:26
     */
    public static JwtPayload analysisJwtPayload(String jwt) {
        try {
            Claims claims = getClaimsFromToken(jwt);
            String jwtBody = EncryptionUtils.aesDecrypt(claims.get("data", String.class));
            return JSONObject.parseObject(jwtBody, JwtPayload.class);
        }catch (Exception e){
            log.error("解析JWT数据 - 执行异常：{}",e.getMessage());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈解析JWT数据〉
     *
     * @param request1 HttpServletRequest
     * @param request2 ServerHttpRequest
     * @return: com.haplink.core.jwt.JwtPayload
     * @author: 吴宇森
     * @date: 2022/8/8 18:26
     */
    public static JwtPayload analysisBody(HttpServletRequest request1, ServerHttpRequest request2) {
        JwtInfo info = null;
        if (!StringUtils.isNull(request1)) {
            info = getTokenToHttpServlet(request1);
        } else {
            info = getTokenToServerHttp(request2);
        }
        if(StringUtils.isNull(info)){
            return null;
        }
        return analysisJwtPayload(info.getToken());
    }

    /**
     * 功能描述:
     * 〈从请求信息中获取JWT携带的信息〉
     * @param request 请求信息
     * @return com.haplink.core.jwt.JwtPayload
     * @author 吴宇森
     * @date 2023/2/10 16:24
     */
    public static JwtPayload analysisBody(HttpServletRequest request){
        return analysisBody(request,null);
    }

    /**
     * 功能描述:
     * 〈从请求信息中获取JWT携带的信息〉
     * @param request 请求信息
     * @return com.haplink.core.jwt.JwtPayload
     * @author 吴宇森
     * @date 2023/2/10 16:24
     */
    public static JwtPayload analysisBody(ServerHttpRequest request){
        return analysisBody(null,request);
    }
}
