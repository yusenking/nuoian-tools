package com.nuoian.common.database.config;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * @Author: 吴宇森
 * @Date: 2022/4/6 17:05
 * @Description: 重写数据库路由
 * @Package: com.nuoian.common.database.config
 * @Version: 1.0
 */
public class MyAbstractRoutingDataSource  extends AbstractRoutingDataSource{
    @Override
    protected Object determineCurrentLookupKey() {
        String dbType=DbContextHolder.getDbType();
        if(dbType.equals(DbContextHolder.WRITE)){
            // 当前使用写入库
            return dbType;
        }
        // 当前使用读取库
        return DbContextHolder.READ;
    }
}
