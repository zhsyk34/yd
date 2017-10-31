package com.yd.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class PropUtils {

    private static final String PROFILE = "config.properties";

    private static final Map<String, String> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(PropUtils.class);

    static {
        File file = new File("./" + PROFILE);
//        File file = new File("D:\\workspace\\yd\\log-server\\src\\main\\resources\\config.properties");

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
