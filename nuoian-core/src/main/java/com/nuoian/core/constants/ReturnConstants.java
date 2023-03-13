package com.nuoian.core.constants;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 14:54
 * @Description: 返回信息常量
 * @Package: com.nuoian.core.constants
 * @Version: 1.0
 */
public class ReturnConstants {
    /**
     * 操作成功状态码
     */
    public static final Integer CODE_SUCCESS=200;

    /**
     * 操作成功状态码文本值
     */
    public static final String CODE_SUCCESS_TEXT = "200";

    /**
     * 操作失败状态码
     */
    public static final Integer CODE_ERROR=500;

    /**
     * 操作失败状态码文本值
     */
    public static final String CODE_ERROR_TEXT = "500";

    /**
     * 操作成功返回信息
     */
    public static final String RETURN_SUCCESS="操作成功";

    /**
     * 操作失败返回信息
     */
    public static final String RETURN_ERROR="操作失败，请重试";

    /**
     * 客户端认证失败
     */
    public static final String CLIENT_AUTH_FAIL="客户端认证失败";

    /**
     * 无数据返回
     */
    public static final String NONE_DATA="暂无数据";

    /**
     * 无效的请求参数返回
     */
    public static final String INVALID_REQUEST_PARAM="无效的请求参数";
    /**
     * 该账户不存在
     */
    public static final String ACCOUNT_NOT_EXIST="该账户不存在";

    /**
     * 该账户已被锁定
     */
    public static final String ACCOUNT_NON_LOCKED="该账户已被锁定";

    /**
     * 该账户已被禁用
     */
    public static final String ACCOUNT_IS_ENABLED="该账户已被禁用";

    /**
     * 该账户已过期
     */
    public static final String ACCOUNT_NON_EXPIRED="该账户已过期";

    /** 旧密码验证失败返回消息 */
    public static final String QLD_PASSWORD_ERROR="旧密码验证失败，请重试";

    /** 手机号已存在返回消息 */
    public static final String MOBILE_EXIST="手机号已存在";

    /** 手机号不存在返回消息 */
    public static final String MOBILE_INEXISTENCE="手机号不存在";

    /** 用户账号已存在 */
    public static final String ACCOUNT_ALREADY="用户账号已存在";

    public static final String UPLOAD_FILE_NOT_NULL ="文件不能为空，请重新上传";

    /** 小程序通过code获取openid失败返回信息 */
    public static final String CODE_GET_OPENID_ERROR="通过code获取openid请求发送失败";

    /** 小程序登录获取openid等数据失败返回信息 */
    public static final String OPENID_SESSIONKEY_ERROR="openId和session_key获取失败";
}
