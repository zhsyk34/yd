package com.yd.log.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.yd.log.config.LoggerConfig.PROFILE;

public abstract class LoggerConfigParser {

    private static final Map<String, String> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(LoggerConfigParser.class);

    static {
        File file = new File(PROFILE);

        if (file.exists()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(file));

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
