package com.yd.process;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@NoArgsConstructor(staticName = "instance")
public class ShellProcessorUtils implements ShellProcessor {

    private final Logger logger = LoggerFactory.getLogger(ShellProcessorUtils.class);

    @Getter
    protected String description;

    @Override
    public boolean process(String command, String... args) {
        List<String> builder = new ArrayList<>();

        builder.add(command);

        if (args != null && args.length > 0) {
            builder.addAll(Arrays.asList(args));
        }

        return this.handle(builder);
    }

    @Override
    public boolean process(String command) {
        return this.handle(Collections.singletonList(command));
    }

    @Override
    public void describe(String description) {
        this.description = description;
    }

    private boolean handle(List<String> command) {
        Process process = null;

        logger.info("开始处理脚本:{}", Optional.ofNullable(description).orElse(command.get(0)));

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);

            process = builder.start();
            process.waitFor();

            return true;
        } catch (IOException | InterruptedException e) {
            logger.error("处理脚本出错", e);
            return false;
        } finally {
            Optional.ofNullable(process).ifPresent(Process::destroy);
        }
    }
}
