package com.yd.ecabinet.service;

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
public class PythonTagService implements TagService {

    private final StoreConfig storeConfig;

    private static String read(InputStream in) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            return buffer.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String read() {
        String command = "python  " + storeConfig.getScript();
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            String result = read(process.getInputStream());
            process.destroy();
            return result;
        } catch (Exception e) {
            logger.error("调用失败", e);
            return null;
        }
    }
}
