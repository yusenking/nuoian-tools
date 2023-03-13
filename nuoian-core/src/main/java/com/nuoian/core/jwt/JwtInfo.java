package com.nuoian.core.jwt;

import lombok.Data;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 10:25
 * @Description: Request获取的JWT信息
 * @Package: com.nuoian.core.jwt
 * @Version: 1.0
 */
@Data
public class JwtInfo {

    /**
     * request获取的原始Token  带 Bearer前缀
     */
    private String body;

    /**
     * request获取的Token
     */
    private String token;

    /**
     * code
     */
    private Integer code;
}
