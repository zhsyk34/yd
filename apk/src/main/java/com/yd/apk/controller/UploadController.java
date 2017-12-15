package com.yd.apk.controller;

import com.yd.apk.domain.Result;
import com.yd.apk.util.FileUtils;
import com.yd.apk.util.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.yd.apk.constant.Config.DIR;
import static com.yd.apk.constant.Config.SUFFIX;

@RestController
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/upload/{type}/{version:.+}")
    public Result<Boolean> upload(@PathVariable("type") String type, @PathVariable("version") String version, @RequestParam("file") MultipartFile file) throws IOException {
        Path path = FileUtils.createPath(Paths.get(DIR, type, version + SUFFIX));
        logger.info("上传文件 {} 至 {}", file.getOriginalFilename(), path);

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
        String fileName = UploadUtils.getFileName(file);
        file.write(fileName);

        return Result.success(true);
    }

}  