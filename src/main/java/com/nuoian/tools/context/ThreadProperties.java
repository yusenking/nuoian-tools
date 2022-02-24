package com.nuoian.tools.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 18:17
 * @Description: 线程配置参数
 * @Package: com.nuoian.tools.context
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(
        prefix = "nuoian.thread"
)
public class ThreadProperties {
    public ThreadProperties() {
    }

    /**
     * 核心线程数
     */
    private Integer corePoolSize=16;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize=64;

    /**
     * 队列大小
     */
    private Integer queueCapacity=999;

    /**
     * 线程保持活力时间
     */
    private Integer keepAliveSeconds=60;

    /**
     * 等待结束时间
     */
    private Integer awaitTerminationSeconds=60;

    /**
     * 线程前缀名
     */
    private String threadNamePrefix="nuoian-async-";

    /**
     * 线程关闭时等待任务完成
     */
    private Boolean waitForTasksToCompleteOnShutdown=true;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public Integer getAwaitTerminationSeconds() {
        return awaitTerminationSeconds;
    }

    public void setAwaitTerminationSeconds(Integer awaitTerminationSeconds) {
        this.awaitTerminationSeconds = awaitTerminationSeconds;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public Boolean getWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public void setWaitForTasksToCompleteOnShutdown(Boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }
}
