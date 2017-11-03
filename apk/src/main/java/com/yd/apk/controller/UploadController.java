package com.yd.apk.controller;

import com.yd.apk.domain.*;
import com.yd.apk.util.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;

import static com.yd.apk.constant.Config.*;

@RestController
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/upload/{type}/{version}")
    public Result<Boolean> upload(
            @PathVariable("type") String type,
            @PathVariable("version") String version,
            @RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path path = FileUtils.createPath(Paths.get(DIR, type, version, filename));
        logger.debug("save {} to path:{}", filename, path.normalize().toAbsolutePath());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return Result.success(true);
    }

    @PostMapping("/u1")
    public Result<Boolean> u1(String type, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path path = FileUtils.createPath(Paths.get(DIR, type, filename));

        Files.write(path, file.getBytes());

        return Result.success(true);
    }

    @PostMapping("/u2")
    public Result<Boolean> u2(String type, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path path = FileUtils.createPath(Paths.get(DIR, type, filename));

        file.transferTo(new File(path.toUri()));

        return Result.success(true);
    }

    /*default dir config in web.xml*/
    @PostMapping("/u3")
    public Result<Boolean> u3(Part file) throws IOException {
        String fileName = UploadUtils.getFileName2(file);
        file.write(fileName);

        return Result.success(true);
    }

}  