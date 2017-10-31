package com.yd.log.server;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.yd.log.config.Config;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yd.log.config.Config.DIR;
import static java.io.File.separator;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

abstract class LoggerBuilder {

    static final LoggerContext CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();

    private static final Map<String, Logger> ORIGINAL_LOGGERS;

    private static final Map<String, Optional<Logger>> BUILD_LOGGER_MAP = new HashMap<>();

    static {
        configureContext(CONTEXT);
        ORIGINAL_LOGGERS = CONTEXT.getLoggerList().stream().collect(toMap(Logger::getName, identity(), (l1, l2) -> l2, () -> new TreeMap<>(Comparator.comparing(String::length).reversed())));
    }

    private static void configureContext(LoggerContext context) {
        JoranConfigurator configurator = new JoranConfigurator();
        context.reset();
        configurator.setContext(context);
        try {
            configurator.doConfigure(new File(Config.CONFIG_FILE));
//            configurator.doConfigure(new File("D:\\workspace\\yd\\log-server\\src\\main\\resources\\logback.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static synchronized Logger getLogger(String name) {
        Optional<Logger> cache = BUILD_LOGGER_MAP.get(name);
        if (cache != null) {
            return cache.orElse(null);
        }

        Logger logger = build(name);
        BUILD_LOGGER_MAP.put(name, Optional.ofNullable(logger));

        return logger;
    }

    private static Logger build(String name) {
        String prefix = prefix(name);
        if (prefix == null || prefix.isEmpty()) {
            return null;
        }

        String expectName = name.substring(prefix.length() + "[]".length());
        String matchName = Optional.ofNullable(matchName(expectName)).orElse("");

        Logger original = CONTEXT.getLogger(matchName);

        Logger logger = CONTEXT.getLogger(name);
        logger.setLevel(original.getLevel());
        logger.setAdditive(original.isAdditive());

        Appender<ILoggingEvent> appender = AppenderBuilder.instance(DIR + separator + prefix).build();
        logger.addAppender(appender);

        return logger;
    }

    private static String prefix(String input) {
        Matcher matcher = Pattern.compile("\\[(\\S+)]").matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String matchName(String name) {
        for (Map.Entry<String, Logger> entry : ORIGINAL_LOGGERS.entrySet()) {
            String key = entry.getKey();
            if (name.contains(key)) {
                return key;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private static class LoggerMatchUtils {
        private static Field child = null;

        static {
            try {
                child = Logger.class.getDeclaredField("childrenList");
                child.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        static boolean isBottom(Logger logger) {
            if (child == null) {
                return false;
            }
            try {
                @SuppressWarnings("unchecked")
                List<Logger> childrenList = (List<Logger>) child.get(logger);
                return childrenList == null || childrenList.isEmpty();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
