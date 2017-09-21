package com.yd.process;

import lombok.RequiredArgsConstructor;

import static com.yd.config.Config.SCRIPTS_DIR;

@RequiredArgsConstructor(staticName = "of")
public class DownloadShell implements Shell {

    private final String version;

    @Override
    public boolean execute() {
        ShellProcessorUtils instance = ShellProcessorUtils.instance();
        instance.describe("下载supermarket.jar新版本[" + version + "]");
        return instance.process(SCRIPTS_DIR + "download.sh", version);
    }
}
