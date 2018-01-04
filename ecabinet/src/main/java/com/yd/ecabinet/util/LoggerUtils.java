package com.yd.ecabinet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("WeakerAccess")
public abstract class LoggerUtils {

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);//TODO
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
