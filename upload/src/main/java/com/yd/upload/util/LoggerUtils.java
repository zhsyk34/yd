package com.yd.upload.util;

import com.yd.upload.config.StoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("WeakerAccess")
public abstract class LoggerUtils {

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(StoreConfig.NAME + "." + name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
