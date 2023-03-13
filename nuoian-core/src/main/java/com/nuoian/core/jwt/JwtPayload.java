package com.nuoian.core.jwt;

import lombok.Data;

import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/9 10:00
 * @Description: JWT有效载荷
 * @Package: com.nuoian.core.jwt
 * @Version: 1.0
 */
@Data
public class JwtPayload {

    /**
     * 租户ID
     */
    private String lesseeId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 归属组织ID
     */
    private Integer osId;

    /**
     * 企业名称
     */
    private String organName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色集合
     */
    private List<String> roles;

    /**
     * 当前角色
     */
    private Long nowRole;

    /**
     * 当前角色编码
     */
    private String  nowRoleCode;
}
