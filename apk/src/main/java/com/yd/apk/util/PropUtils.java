package com.yd.apk.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public abstract class PropUtils {

    private static final String PROFILE = "apk.properties";

    private static final Map<String, String> map = new HashMap<>();

    static {
//        Resource resource = new FileSystemResource(PROFILE);
        Resource resource = new ClassPathResource(PROFILE);

        if (resource.exists()) {
            try {
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);

                properties.stringPropertyNames().forEach(key -> map.put(key, properties.getProperty(key)));

                logger.info("配置文件{}加载完毕", PROFILE);
            } catch (IOException e) {
                logger.error("加载配置文件{}出错...", PROFILE, e);
            }
        } else {
            logger.error("配置文件{}不存在...", PROFILE);
        }
    }

    public static String getString(String key) {
        return map.get(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

}
