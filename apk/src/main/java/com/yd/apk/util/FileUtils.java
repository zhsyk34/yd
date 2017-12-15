package com.yd.apk.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

@Slf4j
public abstract class FileUtils {

    public static Path createPath(Path path) throws IOException {
        path = path.normalize();
        if (!Files.exists(path)) {
            Path parent = path.getParent();
            if (!Files.exists(parent)) {
                logger.debug("dir {} not exists,create now.", parent.toAbsolutePath());
                Files.createDirectories(parent);
            }

            logger.debug("create file {}.", path.getFileName());
            Files.createFile(path);
        }

        return path;
    }

    public static Optional<Path> getNewestFile(Path path) throws IOException {
        return Files.walk(path, 1, FOLLOW_LINKS)
//                .filter(Files::isDirectory)
                .filter(((Predicate<Path>) (Files::isDirectory)).negate())
                .max(Comparator.comparing(p -> {
                    try {
                        return Files.getLastModifiedTime(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return FileTime.from(Instant.MIN);
                    }
                }));
    }
}
