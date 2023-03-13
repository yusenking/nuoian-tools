package com.nuoian.common.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuoian.core.datatype.StringUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author :吴宇森
 * @Date :2022-08-29 10:03:15
 * @Description :SysBusinessMonitor 表实体类
 * @Package :com.haplink.common.database.common
 * @Version : 1.0
 */
@Data
public class SysBusinessMonitor {
    private static volatile SysBusinessMonitor sysBusinessMonitor;
    /**
     * id
     */
    private Integer id;
    /**
     * 操作用户名
     */
    private String userName;
    /**
     * 请求时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date requestDate;
    /**
     * 返回结果时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;
    /**
     * 执行方法耗时（ms）
     */
    private Integer executionTime;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 是否执行成功
     */
    private Boolean success;

    /**
     * 异常堆栈信息
     */
    private String errorStack;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updated;

    private SysBusinessMonitor() {
    }

    /**
     * 功能描述:
     * 〈懒汉式双重检查〉
     *
     * @author: 吴宇森
     * @date: 2022-08-29 10:03:15
     */
    public static SysBusinessMonitor getInstance() {
        if (StringUtils.isNull(sysBusinessMonitor)) {
            synchronized (SysBusinessMonitor.class) {
                if (StringUtils.isNull(sysBusinessMonitor)) {
                    sysBusinessMonitor = new SysBusinessMonitor();
                }
            }
        }
        return sysBusinessMonitor;
    }

}

