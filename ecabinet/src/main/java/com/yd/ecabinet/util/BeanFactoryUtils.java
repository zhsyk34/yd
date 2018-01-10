package com.yd.ecabinet.util;

import com.yd.ecabinet.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BeanFactoryUtils {

    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfig.class);

    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }
}
