package com.yd.ecabinet.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class NetworkUtils {

    public static String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
