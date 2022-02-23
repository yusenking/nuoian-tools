package com.nuoian.tools.thread;

import com.nuoian.tools.context.ThreadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 17:37
 * @Description: 线程池配置
 * @Package: com.nuoian.tools.thread
 * @Version: 1.0
 */
@Slf4j
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    private final ThreadProperties threadProperties;

    /**
     * 核心线程数
     */
    private Integer CORE_POOL_SIZE;

    /**
     * 最大线程数
     */
    private Integer MAX_POOL_SIZE;

    /**
     * 队列大小
     */
    private Integer QUEUE_CAPACITY;

    /**
     * 线程保持活力时间
     */
    private Integer KEEP_ALIVE_SECONDS;

    /**
     * 等待结束时间
     */
    private Integer AWAIT_TERMINATION_SECONDS;

    /**
     * 线程前缀名
     */
    private String THREAD_NAME_PREFIX;

    /**
     * 线程关闭时等待任务完成
     */
    private Boolean WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN;

    public ThreadPoolConfig(ThreadProperties threadProperties) {
        this.threadProperties = threadProperties;
    }

    @PostConstruct
    public void initProperties(){
        CORE_POOL_SIZE=threadProperties.getCorePoolSize();
        MAX_POOL_SIZE=threadProperties.getMaxPoolSize();
        QUEUE_CAPACITY=threadProperties.getQueueCapacity();
        KEEP_ALIVE_SECONDS=threadProperties.getKeepAliveSeconds();
        AWAIT_TERMINATION_SECONDS=threadProperties.getAwaitTerminationSeconds();
        THREAD_NAME_PREFIX=threadProperties.getThreadNamePrefix();
        WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN=threadProperties.getWaitForTasksToCompleteOnShutdown();
        log.info("线程池参数加载完成");
    }

    @Bean
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN);
        //添加线程失败则通过主线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        log.info("启动线程池完成");
        return executor;
    }
}
