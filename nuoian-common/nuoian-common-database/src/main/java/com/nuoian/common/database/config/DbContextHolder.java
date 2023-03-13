package com.nuoian.common.database.config;

import com.nuoian.core.datatype.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/6 17:01
 * @Description: 线程切换读写数据库
 * @Package: com.nuoian.common.database.config
 * @Version: 1.0
 */
@Slf4j
public class DbContextHolder {
    /**
     * 写入
     */
    public static final String WRITE="write";

    /**
     * 读取
     */
    public static final String READ="read";

    private static ThreadLocal<String> contextHolders=new ThreadLocal<>();

    /**
     * 功能描述:
     * 〈设置数据库类型〉
     * @return: void
     * @author: 吴宇森
     * @date: 2022/2/14 18:49
     */
    public static void setDbType(String type){
        if(StringUtils.isBlank(type)){
            log.error("DB类型为空!");
            throw new NullPointerException("DB类型为空");
        }
        contextHolders.set(type);
    }

    /**
     * 功能描述:
     * 〈获取数据库类型〉
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/14 18:50
     */
    public static String getDbType(){
        //设置默认数据库类型为写
        return StringUtils.isBlank(contextHolders.get())?WRITE:contextHolders.get();
    }

    /**
     * 功能描述:
     * 〈情况数据库类型信息〉
     * @return: void
     * @author: 吴宇森
     * @date: 2022/2/14 18:50
     */
    public static void clearDbType(){
        //清除数据库类型信息
        contextHolders.remove();
    }
}
