package com.yd.manager.util;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class EncryptUtils {

    public static byte[] encode(byte[] bs) {
        return Base64.getEncoder().encode(bs);
    }

    public static byte[] encode(String s) {
        return encode(s.getBytes(UTF_8));
    }

    public static String encodeToString(byte[] bs) {
        return Base64.getEncoder().encodeToString(bs);
    }

    public static String encodeToString(String s) {
        return encodeToString(s.getBytes(UTF_8));
    }

    public static byte[] decode(byte[] bs) {
        return Base64.getDecoder().decode(bs);
    }

    public static byte[] decode(String s) {
        return Base64.getDecoder().decode(s);
    }

    public static String decodeToString(String s) {
        return new String(decode(s), UTF_8);
    }
}
