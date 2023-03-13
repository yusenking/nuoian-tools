package com.nuoian.common.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/29 17:34
 * @Description: xxljob 配置
 * @Package: com.nuoian.common.xxljob
 * @Version: 1.0
 */
@Slf4j
@Component
public class XxlJobConfig {

    @Value("${nuoian.job.accessToken}")
    private String accessToken;

    @Value("${nuoian.job.admin.address}")
    private String adminAddress;

    @Value("${nuoian.job.executor.appname}")
    private String appname;

    @Value("${nuoian.job.executor.address}")
    private String address;

    @Value("${nuoian.job.executor.ip}")
    private String ip;

    @Value("${nuoian.job.executor.port}")
    private Integer port;

    @Value("${nuoian.job.executor.logpath}")
    private String logpath;

    @Value("${nuoian.job.executor.logretentiondays}")
    private Integer logretentiondays;

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(adminAddress);
        executor.setAppname(appname);
        executor.setAddress(address);
        executor.setIp(ip);
        executor.setPort(port);
        executor.setAccessToken(accessToken);
        executor.setLogPath(logpath);
        executor.setLogRetentionDays(logretentiondays);
        log.info("xxl-job 配置加载完成，当前执行器端口为：{}", port);
        return executor;
    }
}
