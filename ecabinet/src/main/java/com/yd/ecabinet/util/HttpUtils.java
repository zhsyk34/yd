package com.yd.ecabinet.util;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public abstract class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String uri, Map<String, Object> map) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(uri);
            Optional.ofNullable(map).ifPresent(params -> params.forEach((k, v) -> builder.setParameter(k, v.toString())));

            try (CloseableHttpResponse response = httpclient.execute(new HttpGet(builder.build()))) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String get(String uri) {
        return get(uri, null);
    }

    public static String postForm(String uri, Map<String, Object> map) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);

            List<BasicNameValuePair> params = map.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue().toString())).collect(toList());
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return null;
        }

    }

    public static String postJson(String uri, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            logger.error("uri:{}请求出错", e);
        }

        return null;
    }

}
