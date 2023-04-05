package com.xw.ggkt.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${qiniuyun.cos.file.secretid}")
    private String secretId;

    @Value("${qiniuyun.cos.file.secretkey}")
    private String secretKey;

    @Value("${qiniuyun.cos.file.bucketname}")
    private String bucketName;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = secretId;
        ACCESS_KEY_SECRET = secretKey;
        BUCKET_NAME = bucketName;
    }
}
