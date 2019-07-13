package com.springboot.framework.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author huangpengfei
 * @version 1.0
 * @date 2019/7/13 23:26
 */
@Data
@ConfigurationProperties(prefix = "cos")
@Component
public class COSConfig {
    private static Logger logger = LoggerFactory.getLogger(COSConfig.class);

    private String secretId;
    private String secretKey ;
    private String uploadEndpoint;
    private String downloadEndpoint;
    private String bucketName;
    private String storagePath;
    private long downloadUrlExpiration;
    private String styleName;


    @PostConstruct
    public void init() {
        logger.debug(JSON.toJSONString(this));
    }
}
