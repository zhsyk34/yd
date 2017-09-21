package com.yd.process;

import lombok.NoArgsConstructor;

import static com.yd.config.Config.SCRIPTS_DIR;

@NoArgsConstructor(staticName = "instance")
public class StopShell implements Shell {
    @Override
    public boolean execute() {
        ShellProcessorUtils instance = ShellProcessorUtils.instance();
        instance.describe("停止supermarket.jar");
        return instance.process(SCRIPTS_DIR + "stop.sh");
    }
}
