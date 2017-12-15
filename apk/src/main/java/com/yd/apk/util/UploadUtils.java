package com.yd.apk.util;

import javax.servlet.http.Part;

public abstract class UploadUtils {

    private static final String FILE_HEADER = "content-disposition";
    private static final String FILE_PREFIX = "filename";

    public static String getFileName(Part part) {
        for (String s : part.getHeader(FILE_HEADER).split(";")) {
            if (s.trim().startsWith(FILE_PREFIX)) {
                return s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
