package com.yd.apk.util;

import org.slf4j.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.*;
import java.util.*;
import java.util.function.*;

import static java.nio.file.FileVisitOption.*;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public abstract class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

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

    public static Path createPath(String path) throws IOException {
        return createPath(Paths.get(path));
    }

    public static Optional<Path> getNewestFile(Path path) throws IOException {
        return Files.walk(path, 1, FOLLOW_LINKS)
                .filter(((Predicate<Path>) (Files::isDirectory)).negate())
                .max(Comparator.comparing(p -> {
                    try {
                        return Files.getLastModifiedTime(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return FileTime.from(Instant.MIN);
                    }
                }));

//        Files.walkFileTree(path, EnumSet.of(FOLLOW_LINKS), 3, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println(file);
//                return FileVisitResult.CONTINUE;
//            }
//        });
    }
}
