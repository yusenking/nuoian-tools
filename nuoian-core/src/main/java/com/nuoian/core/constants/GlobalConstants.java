package com.nuoian.core.constants;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 14:52
 * @Description: 全局常量
 * @Package: com.nuoian.core.constants
 * @Version: 1.0
 */
public class GlobalConstants {
    /**
     * 数字-2
     */
    public static final Integer LOSE_NUMBER_2 = -2;
    /**
     * 数字-1
     */
    public static final Integer LOSE_NUMBER_1 = -1;

    /**
     * 数字0
     */
    public static final Integer NUMBER_0 = 0;

    /**
     * 数字1
     */
    public static final Integer NUMBER_1 = 1;

    /**
     * 数字3
     */
    public static final Integer NUMBER_2 = 2;

    /**
     * 数字3
     */
    public static final Integer NUMBER_3 = 3;

    /**
     * 数字4
     */
    public static final Integer NUMBER_4 = 4;

    /**
     * 数字5
     */
    public static final Integer NUMBER_5 = 5;

    /**
     * 数字6
     */
    public static final Integer NUMBER_6 = 6;

    /**
     * 数字7
     */
    public static final Integer NUMBER_7 = 7;

    /**
     * 数字8
     */
    public static final Integer NUMBER_8 = 8;

    /**
     * 数字9
     */
    public static final Integer NUMBER_9 = 9;

    /**
     * 数字10
     */
    public static final Integer NUMBER_10 = 10;

    /**
     * 数字11
     */
    public static final Integer NUMBER_11 = 11;

    /**
     * 数字12
     */
    public static final Integer NUMBER_12 = 12;

    /**
     * 数字13
     */
    public static final Integer NUMBER_13 = 13;

    /**
     * 数字23
     */
    public static final Integer NUMBER_23 = 23;

    /**
     * 数字24
     */
    public static final Integer NUMBER_24 = 24;

    /**
     * 数字59
     */
    public static final Integer NUMBER_59 = 59;

    /**
     * 数字60
     */
    public static final Integer NUMBER_60 = 60;

    /**
     * 数字100
     */
    public static final Integer NUMBER_100 = 100;

    /**
     * 数字200
     */
    public static final Integer NUMBER_200 = 200;

    /**
     * 数字256
     */
    public static final Integer NUMBER_256 = 256;

    /**
     * 数字200
     */
    public static final Integer NUMBER_201 = 201;

    /**
     * 数字999
     */
    public static final Integer NUMBER_999 = 999;

    /**
     * 数字1000
     */
    public static final Integer NUMBER_1000 = 1000;

    /**
     * 数字1024
     */
    public static final Integer NUMBER_1024 = 1024;

    /**
     * 数字8192
     */
    public static final Integer NUMBER_8192 = 8192;

    /**
     * 1小时毫秒数
     */
    public static final Long HOUR_MS = 3600000L;

    /**
     * 字符集名称
     */
    public static final String CHARSET_NAME="UTF-8";

    public static final String CHARSET_UTF_OPTS="OPTS UTF8";

    public static final String ON="ON";

    public static final String CODE_STR="code";
    public static final String BODY_STR="body";
    public static final String TOKEN_STR="token";

    /**
     * 算法
     */
    public static final String DES_ALGORITHM="DES";

    /**
     * 安全策略
     */
    public static final String SHA1PRNG="SHA1PRNG";
    /**
     * 加密盐值
     */
    public static final String DES_KEY="nuoian";

    /**
     * 算法
     */
    public static final String AES_ALGORITHM = "AES";

    /**
     * 默认的加密算法，CBC模式
     */
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 密匙，必须16位
     */
    public static final String AES_KEY = "gi5gCJXu6iUFLbWN";

    /**
     * 偏移量
     */
    public static final String OFFSET = "eIhxJdbDuNM7G6v0";

    /**
     * 验证码规则
     */
    public static final String AUTH_CODE_ARR = "0123456789";
    public static final String AUTH_CODE_ARR2 = "123456789";

    /**
     * 数字及字符串
     */
    public static final String LETTER_NUMBER="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * 换行符
     */
    public static final String LINE_BREAK="\n";

    /**
     * 逗号
     */
    public static final String COMMA=",";

    /**
     * 横杠
     */
    public static final String SYMBOL="-";

    /**
     * 模糊路径匹配符
     */
    public static final String PATH_MATCH_CHARACTER="/**";

    /**
     * 空格符
     */
    public static final String SPACE=" ";

    /**
     * 空字符串
     */
    public static final String BLANK = "";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 等于号
     */
    public static final String EQUAL="=";

    /**
     * 左圆括号
     */
    public static final String PARENTHESIS_LEFT="(";

    /**
     * 右圆括号
     */
    public static final String PARENTHESIS_RIGHT=")";

    /**
     * 单引号
     */
    public static final String SINGLE_QUOTES="'";

    /**
     * 双引号
     */
    public static final String DOUBLE_QUOTATION_MARKS="\"";

    /**
     * 百分号
     */
    public static final String PER_CENT="%";

    /**
     * 点
     */
    public static final String SPOT = "\\.";

    /**
     * null字符串
     */
    public static final String NULL_STRING="null";

    /**
     * 没有权限数据
     */
    public static final String AUTH_NOT_DATA="auth_not_data";

    /**
     * 中文数字
     */
    public static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * 中文数字单位
     */
    public static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

    /**
     * 特殊字符：负
     */
    public static final String CN_NEGATIVE = "负";

    /**
     * 特殊字符：点
     */
    public static final String CN_POINT = "点";

    /** 密码输入错误3次提示 */
    public static final String ERROR_PWD_NUM="密码输入错误3次，请于1小时后重试";

    /**
     * 用户不存在
     */
    public static final String USER_INEXISTENCE="用户不存在";

    /** 账号或密码错误返回消息 */
    public static final String ID_PASSWORD_ERROR="账号或密码错误，错误3次后将限制1小时后重试";

    /** 账号失效 */
    public static final String USER_INVALID="该账户已失效";

    /** 暂无数据返回消息 */
    public static final String NO_DATA="暂无数据";

    /**
     * 密码错误3次 1小时
     */
    public final static Long PWD_ERROR_EXPIRE_TIME = 60 *60L;

    /**
     * JWT超时时间 1小时
     */
    public static final Long JWT_TOMEOUT=12*60*60*1000L;


    /**
     * ACCESS_TOKEN Redis缓存超时时间 30分钟
     */
    public static final Long ACCESS_TOKEN_REDIS_TOMEOUT=30*60L;

    /**
     * REFRESH_TOKEN Redis缓存超时时间 24小时
     */
    public static final Long REFRESH_TOKEN_REDIS_TOMEOUT=24*60*60L;

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Token前缀
     */
    public static final String HEADER = "Authorization";

    /**
     * ACCESS_TOKEN Redis缓存键
     */
    public static final String ACCESS_TOKEN_REDIS_KEY = "AUTH:ACCESS_TOKEN:";

    /**
     * REFRESH_TOKEN Redis缓存键
     */
    public static final String REFRESH_TOKEN_REDIS_KEY = "AUTH:REFRESH_TOKEN:";

    /**
     * redis缓存角色对应API键
     */
    public static final String REDIS_CACHE_API_ROLES_KEY = "AUTH:API_ROLE";

    /**
     * redis缓存所有权限API键
     */
    public static final String REDIS_CACHE_ALL_API_KEY = "AUTH:ALL_API";

    /**
     * 密码错误次数 Pwd_Error_Time_{用户IP}
     */
    public final static String USER_PWD_ERROR_TIME_KEY = "SYSCONFIG:NUOIAN_USER_PWD_ERROR_TIME_";

    /**
     * 字典缓存键
     */
    public static final String DICT_CACHE_KEY = "SYSCONFIG:DICT_CACHE";

    /**
     * 用户下级缓存键
     */
    public static final String USER_SUBORDINATE_KEY = "SYSCONFIG:USER_SUBORDINATE";

    /**
     * 角色下级缓存键
     */
    public static final String ROLE_SUBORDINATE_KEY="SYSCONFIG:ROLE_SUBORDINATE";

    /**
     * 导出统计数据参数键
     */
    public static final String EXPORT_LOG_STATISTICS_KEY="SYSCONFIG:EXPORT_LOG_STATISTICS_PARAMS";

    /**
     * 低代码权限缓存键
     */
    public static final String LOWCODE_PERMISSION_KEY="LOWCODE:PERMISSION";

    /**
     * 低代码数据库表前缀
     */
    public static final String LOWCODE_TABLE_PREFIX="t_lc_";

    /**
     * 系统用户 - 密码安全期(天)
     */
    public static final int USER_PASSWORD_SAFETY = 90;

    /**
     * 发送短信显示模板
     */
    public static final String SMS_MESSAGE_TEMPLATE = "尊敬的用户，您的${codeType}是${code}，请及时使用。如非本人操作，请忽略本条短信;";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 安全监管设备日志Redis缓存
     */
    public static String DEVICE_ALARM_REDIS_KEY = "SAFETY_DEVICE_ALARM_INFO";

    /**
     * n8n 自动执行状态
     */
    public static final String N8N_AUTOMATIC_STATUS="n8n.automatic.status_";

    public static final String N8N_LOOP_USER_KEY="N8N_LOOP_USER_KEY_";

    public static final String SAFETY_STATISTICS_DATA="SAFETY_STATISTICS_DATA";

    /**
     * n8n 节点名
     */
    public static final String N8N_NODES_NAME="执行预案";

    /**
     * n8n 节点类型
     */
    public static final String N8N_NODES_TYPE="n8n-nodes-base.webhook";

    /**
     * 超级管理员角色编码
     */
    public static final String ADMIN_ROLE_CODE = "SUPERADMIN";

    /**
     *  不可重复点击的锁前缀
     * */
    public static final String NO_REPEAT_LOCK_PREFIX = "iflow:no_repeat_lock:";

    /**
     * 报警数据缓存时长：24小时 - 单位秒
     */
    public static int DEVICE_ALARM_CACHE_TIME = 86400;

    /**
     * API监控缓存Redis键
     */
    public static final String API_MONITOR_KEY="NUOIAN:MONITOR";
}
