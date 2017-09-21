package com.yd.ecabinet.util;

import com.yd.ecabinet.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BeanFactory {

    private static final AnnotationConfigApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfig.class);

    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    public static void destroy() {
        CONTEXT.close();
    }
}
