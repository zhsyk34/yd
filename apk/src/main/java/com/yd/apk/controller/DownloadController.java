package com.yd.apk.controller;

import com.yd.apk.exception.*;
import com.yd.apk.util.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.*;

import static com.yd.apk.constant.Config.*;
import static java.nio.charset.StandardCharsets.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

@RestController
public class DownloadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/download/{type}")
    public void download(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path path = Paths.get(DIR, type).normalize();

        if (!Files.exists(path)) {
            throw WebException.from(HttpStatus.NOT_FOUND);
        }

        Path target = FileUtils.getNewestFile(path).orElseThrow(() -> WebException.from(HttpStatus.NOT_FOUND));
        logger.info("the newest version file for {} is {}", type, target);

        this.transfer(target, request, response);
    }

    @GetMapping("/download/{type}/{version:.+}")
    public void download(@PathVariable("type") String type, @PathVariable(value = "version", required = false) String version, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path path = Paths.get(DIR, type, version + SUFFIX).normalize();
        logger.debug("request download file from path:{}", path.toAbsolutePath());

        //validate
        boolean exists = Files.exists(path);

        if (!exists) {
            logger.error("file {} not exists!", path.toAbsolutePath());

            throw WebException.from(HttpStatus.NOT_FOUND);
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
