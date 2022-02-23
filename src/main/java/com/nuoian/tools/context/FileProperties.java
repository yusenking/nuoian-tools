package com.nuoian.tools.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 18:04
 * @Description: 文件配置参数
 * @Package: com.nuoian.tools.context
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(
        prefix = "nuoian.file"
)
public class FileProperties {

    public FileProperties() {
    }

    private String visitPath;
    private final FileProperties.Ftp ftp = new FileProperties.Ftp();

    public FileProperties.Ftp getFtp() {
        return this.ftp;
    }

    public String getVisitPath() {
        return visitPath;
    }

    public void setVisitPath(String visitPath) {
        this.visitPath = visitPath;
    }

    public static class Ftp{
        private String user;
        private String password;
        private String host="127.0.0.1";
        private Integer port=21;

        public Ftp() {
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

}
