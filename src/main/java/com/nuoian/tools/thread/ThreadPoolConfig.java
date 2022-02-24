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
    public ThreadPoolConfig(ThreadProperties threadProperties) {
        this.threadProperties = threadProperties;
    }

    @Bean
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadProperties.getKeepAliveSeconds());
        executor.setAwaitTerminationSeconds(threadProperties.getAwaitTerminationSeconds());
        executor.setThreadNamePrefix(threadProperties.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(threadProperties.getWaitForTasksToCompleteOnShutdown());
        //添加线程失败则通过主线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        log.info("启动线程池完成");
        return executor;
    }
}
