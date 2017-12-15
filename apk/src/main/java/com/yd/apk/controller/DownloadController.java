package com.yd.apk.controller;

import com.yd.apk.exception.WebException;
import com.yd.apk.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.yd.apk.constant.Config.DIR;
import static com.yd.apk.constant.Config.SUFFIX;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
public class DownloadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/download/{type}")
    public void download(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("请求下载 {} 的最新版本", type);
        Path path = Paths.get(DIR, type).normalize();

        if (!Files.exists(path)) {
            throw WebException.from(HttpStatus.NOT_FOUND.value(), "文件不存在");
        }

        Path target = FileUtils.getNewestFile(path).orElseThrow(() -> WebException.from(HttpStatus.NOT_FOUND.value(), "文件不存在"));
        logger.info("{} 的最新版本是 {}", type, target);

        this.transfer(target, request, response);
    }

    @GetMapping("/download/{type}/{version:.+}")
    public void download(@PathVariable("type") String type, @PathVariable(value = "version") String version, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path path = Paths.get(DIR, type, version + SUFFIX).normalize();
        logger.debug("请求下载 {}", path.toAbsolutePath());

        //validate
        boolean exists = Files.exists(path);

        if (!exists) {
            logger.error("文件 {} 不存在", path.toAbsolutePath());

            throw WebException.from(HttpStatus.NOT_FOUND.value(), "文件不存在");
        }

        this.transfer(path, request, response);
    }

    private void transfer(Path path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(path.toUri());

        //mime & length
        ServletContext context = request.getServletContext();
        String mimeType = Optional.ofNullable(context.getMimeType(file.getCanonicalPath())).orElse(APPLICATION_OCTET_STREAM_VALUE);
        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());

        //forces download
        response.setHeader(CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", new String(file.getName().getBytes(UTF_8), UTF_8)));

        //write
        FileChannel channel = new FileInputStream(file).getChannel();
        channel.transferTo(0, file.length(), Channels.newChannel(response.getOutputStream()));
    }
}
