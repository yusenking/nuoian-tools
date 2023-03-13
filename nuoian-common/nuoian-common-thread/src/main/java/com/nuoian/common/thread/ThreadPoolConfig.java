package com.nuoian.common.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 16:20
 * @Description: 线程池配置
 * @Package: com.nuoian.common.thread
 * @Version: 1.0
 */
@Slf4j
@EnableAsync
@Configuration
@RefreshScope
public class ThreadPoolConfig {
    /**
     * 核心线程数
     */
    @Value("${nuoian.thread.core-pool-size}")
    public Integer corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${nuoian.thread.max-pool-size}")
    public Integer maxPoolSize;

    /**
     * 队列大小
     */
    @Value("${nuoian.thread.queue-capacity}")
    public Integer queueCapacity;

    /**
     * 线程保持活力时间
     */
    @Value("${nuoian.thread.keep-alive-seconds}")
    public Integer keepAliveSeconds;

    /**
     * 等待结束时间
     */
    @Value("${nuoian.thread.await-termination-seconds}")
    public Integer awaitTerminationSeconds;

    /**
     * 线程前缀名
     */
    @Value("${nuoian.thread.thread-name-prefix}")
    public String threadNamePrefix;

    /**
     * 是否允许超时
     */
    @Value("${nuoian.thread.allow-core-thread-time-out}")
    public Boolean allowCoreThreadTimeOut;

    /**
     * 线程关闭时等待任务完成
     */
    @Value("${nuoian.thread.wait-for-tasks-to-complete-on-shutdown}")
    public Boolean waitForTasksToCompleteOnShutdown;


    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(corePoolSize);
        //最大线程池大小
        executor.setMaxPoolSize(maxPoolSize);
        //任务队列数量
        executor.setQueueCapacity(queueCapacity);
        //线程空闲时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //等待时间
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        //线程池名称前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        //是否允许超时
        executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        //线程池关闭时需等待所有的子任务执行完成才销毁对应的bean
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);

        /*
          设置拒绝策略
          AbortPolicy
          -- 默认，当任务添加到线程池中被拒绝时(固定线程池、最大线程数以及任务队列都放满)，它将抛出 RejectedExecutionException 异常。
          CallerRunsPolicy
          -- 当任务添加到线程池中被拒绝时(固定线程池、最大线程数以及任务队列都放满)，由调用者（一般为main主线程）处理被拒绝的任务。
          DiscardOldestPolicy
          -- 当任务添加到线程池中被拒绝时(固定线程池、最大线程数以及任务队列都放满)，线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝的任务添加到等待队列中（最后面）。
          DiscardPolicy
          -- 当任务添加到线程池中被拒绝时(固定线程池、最大线程数以及任务队列都放满)，线程池将丢弃被拒绝的任务。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        executor.initialize();
        log.info("启动线程池完成，当前线程池前缀为：『{}』", threadNamePrefix);
        return executor;
    }
}
