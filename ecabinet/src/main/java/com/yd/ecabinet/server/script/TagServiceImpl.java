package com.yd.ecabinet.server.script;

import com.yd.ecabinet.config.StoreConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {
    private final StoreConfig storeConfig;

    @Override
    public String read() {
        String command = "python  " + storeConfig.getScriptUrl();

        logger.debug("开始执行python脚本:{}", command);

        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            String result = this.readToString(process.getInputStream());

            logger.debug("脚本执行结果:{}", result);
            process.destroy();
            return result;
        } catch (Exception e) {
            logger.error("调用python脚本失败", e);
            return null;
        }
    }

    private String readToString(InputStream in) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            return buffer.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
