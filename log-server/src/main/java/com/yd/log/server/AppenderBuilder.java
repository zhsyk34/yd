package com.yd.log.server;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static com.yd.log.config.Config.*;
import static com.yd.log.server.LoggerBuilder.CONTEXT;
import static java.io.File.separator;

@RequiredArgsConstructor(staticName = "instance")
@Setter
class AppenderBuilder {
    //base
    private final String dir;

    Appender<ILoggingEvent> build() {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(CONTEXT);

        //default config
        appender.setAppend(true);
        appender.setPrudent(false);

        //file
        appender.setFile(dir + separator + CURRENT + SUFFIX);

        //rollingPolicy
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setFileNamePattern(dir + separator + ROLL_PATTERN + SUFFIX);
        rollingPolicy.setMaxHistory(MAX_HISTORY);
        rollingPolicy.setContext(CONTEXT);
        rollingPolicy.setParent(appender);
        rollingPolicy.start();

        appender.setRollingPolicy(rollingPolicy);

        //encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern(MSG_PATTERN);
        encoder.setContext(CONTEXT);
        encoder.setParent(appender);
        encoder.start();

        appender.setEncoder(encoder);

        //start
        appender.start();

        return appender;
    }

}
