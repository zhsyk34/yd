package com.yd.process;

import lombok.NoArgsConstructor;

import static com.yd.config.Config.SCRIPTS_DIR;

@NoArgsConstructor(staticName = "instance")
public class StartShell implements Shell {
    @Override
    public boolean execute() {
        ShellProcessorUtils instance = ShellProcessorUtils.instance();
        instance.describe("启动supermarket.jar");
        return instance.process(SCRIPTS_DIR + "start.sh");
    }
}
