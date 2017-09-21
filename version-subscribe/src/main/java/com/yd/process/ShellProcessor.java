package com.yd.process;

public interface ShellProcessor {

    boolean process(String command, String... args);

    boolean process(String command);

    void describe(String description);
}
