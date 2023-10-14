package com.juzi.design.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;

/**
 * @author codejuzi
 */
public class HttpClientUtils {

    public static JSONObject execute(String url, HttpMethod httpMethod) {
        HttpRequestBase request = null;
        try {
            HttpClient client = HttpClients.createDefault();
            if (HttpMethod.GET == httpMethod) {
                request = new HttpGet(url);
            } else {
                request = new HttpPost(url);
            }
            HttpEntity entity = client.execute(request).getEntity();
            return JSONUtil.toBean(EntityUtils.toString(entity), JSONObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Request failed, url = " + url);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }
}
