package com.yd.manager.util;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.stream.Collectors.toList;

@SuppressWarnings("unused")
public abstract class HttpUtils {

    public static String get(String uri, Map<String, Object> map) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(uri);
            Optional.ofNullable(map).ifPresent(params -> params.forEach((k, v) -> builder.setParameter(k, v.toString())));

            try (CloseableHttpResponse response = httpclient.execute(new HttpGet(builder.build()))) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return null;
        }
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

    public static String post(String uri, String data) {
        return post(uri, Collections.singletonList(new BasicHeader("Content-Type", "application/json")), data);
    }

    public static String post(String uri, Collection<Header> headers, String data) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            headers.forEach(httpPost::setHeader);
            httpPost.setEntity(new StringEntity(data));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return null;
        }
    }

}
