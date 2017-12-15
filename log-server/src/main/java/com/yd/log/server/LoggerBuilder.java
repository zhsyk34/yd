package com.yd.log.server;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.yd.log.config.LoggerConfig;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.qos.logback.classic.Level.ERROR;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

abstract class LoggerBuilder {

    static final LoggerContext CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();

    private static final Map<String, Logger> ORIGINAL_LOGGERS;

    private static final Map<String, Optional<Logger>> BUILD_LOGGER_CACHE = new HashMap<>();

    private static final Comparator<String> LOGGER_NAME_COMPARATOR = (n1, n2) -> {
        final String root = "ROOT";
        if (n1.equals(root)) {
            return 1;
        }
        if (n2.equals(root)) {
            return -1;
        }
        return n2.length() - n1.length();
    };

    static {
        configureContext(CONTEXT);
        ORIGINAL_LOGGERS = CONTEXT.getLoggerList().stream().collect(toMap(Logger::getName, identity(), (l1, l2) -> l2, () -> new TreeMap<>(LOGGER_NAME_COMPARATOR)));
    }

    private static void configureContext(LoggerContext context) {
        JoranConfigurator configurator = new JoranConfigurator();
        context.reset();
        configurator.setContext(context);
        try {
            configurator.doConfigure(new File(LoggerConfig.CONFIG_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static synchronized Logger build(String name) {
        Optional<Logger> cache = BUILD_LOGGER_CACHE.get(name);
        if (cache != null) {
            return cache.orElse(null);
        }

        Logger logger = init(name);
        BUILD_LOGGER_CACHE.put(name, Optional.ofNullable(logger));

        return logger;
    }

    private static Logger init(String name) {
        String prefix = extractPrefix(name);
        if (prefix == null || prefix.isEmpty()) {
            return null;
        }

        String expectName = name.substring(prefix.length() + "[]".length());
        String matchName = Optional.ofNullable(matchOriginalName(expectName)).orElse("");

        Logger original = CONTEXT.getLogger(matchName);

        Logger logger = CONTEXT.getLogger(name);

        logger.setLevel(Optional.ofNullable(original.getLevel()).orElse(ERROR));
        logger.setAdditive(original.isAdditive());

        Appender<ILoggingEvent> appender = AppenderBuilder.instance(prefix).build();
        logger.addAppender(appender);

        return logger;
    }

    private static String extractPrefix(String input) {
        Matcher matcher = Pattern.compile("\\[(\\S+)]").matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String matchOriginalName(String name) {
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
