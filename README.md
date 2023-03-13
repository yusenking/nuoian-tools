# nuoian-tools

## 常用工具集合

```yaml
nuoian:
    job:
        accessToken: 56718e188ac2496d8c69a4cfbd21a5fc
    admin:
        address: 管理控制台地址
    executor:
        appname: 执行器名
        address:
        ip:
        port: 执行器端口号
        logpath: 日志地址
        logretentiondays: 30 日志储存时间
    thread:
        core-pool-size: 16 核心线程数
        max-pool-size: 64 最大线程数
        queue-capacity: 999 队列大小
        keep-alive-seconds: 60 线程保持活力时间
        await-termination-seconds: 60 等待结束时间
        thread-name-prefix: nuoian-async- 线程前缀名
        allow-core-thread-time-out: true 是否允许超时
        wait-for-tasks-to-complete-on-shutdown: true 线程关闭时等待任务完成
			 
```

