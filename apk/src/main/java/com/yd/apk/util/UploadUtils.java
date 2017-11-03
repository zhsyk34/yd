package com.yd.apk.util;

import org.springframework.util.*;

import javax.servlet.http.*;

public abstract class UploadUtils {

    private static final String FILE_HEADER = "content-disposition";
    private static final String FILE_PREFIX = "filename";

    public static String getFileName(Part part) {
        if (part == null)
            return null;

        String name = part.getHeader(FILE_HEADER);
        return StringUtils.hasText(name) ? name : null;

//        return StringUtils.substringBetween(name, "filename=\"", "\"");
    }

    public static String getFileName2(Part part) {
        for (String s : part.getHeader(FILE_HEADER).split(";")) {
            if (s.trim().startsWith(FILE_PREFIX)) {
                return s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
