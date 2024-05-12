package com.heart.ticket.base.utils;

import com.heart.ticket.base.model.HttpResult;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2023/8/15 10:34.
 */
public class HttpUtils {

    /**
     * http get(参数拼接到url上)
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @return HttpResult
     */
    public static HttpResult get(String url, Map<String, String> headers) throws IOException, ParseException {
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000L))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
                .setResponseTimeout(Timeout.ofMilliseconds(3000L))
                .build();
        HttpGet httpGet = new HttpGet(url);
        // 请求配置
        httpGet.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // http get请求
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                String result = (entity == null) ? "" : EntityUtils.toString(entity);
                return new HttpResult(response.getCode(), result);
            }
        }
    }

    /**
     * http get(参数通过map传递)
     *
     * @param url     请求地址
     * @param queries 请求参数
     * @param headers 请求头参数
     * @return
     */
    public static HttpResult get(String url, Map<String, String> queries, Map<String, String> headers) throws IOException, ParseException, URISyntaxException {
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000L))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
                .setResponseTimeout(Timeout.ofMilliseconds(3000L))
                .build();
        HttpGet httpGet = new HttpGet(url);
        // 请求配置
        httpGet.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 请求参数
        if (queries != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : queries.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            URI uri = new URIBuilder(new URI(url)).addParameters(nameValuePairs).build();
            httpGet.setUri(uri);
        }
        // http get请求
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                String result = (entity == null) ? "" : EntityUtils.toString(entity);
                return new HttpResult(response.getCode(), result);
            }
        }
    }

    /**
     * 将map参数组装成key=value&key=value...形式的Query参数
     *
     * @param map
     * @return
     */
    public static String getQueryParamStr(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        // 删除末尾的 "&"
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * http post form-data
     *
     * @param url     请求地址
     * @param pMap    请求参数，map形式
     * @param headers 请求头
     * @return HttpResult
     */
    public static HttpResult post(String url, Map<String, String> pMap, Map<String, String> headers) throws IOException, ParseException {
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000L))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
                .setResponseTimeout(Timeout.ofMilliseconds(3000L))
                .build();
        HttpPost httpPost = new HttpPost(url);
        // 请求配置
        httpPost.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 请求参数
        if (pMap != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : pMap.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        // http post form-data
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                String result = (entity == null) ? "" : EntityUtils.toString(entity);
                // 确保流被完全消费
                EntityUtils.consume(entity);
                return new HttpResult(response.getCode(), result);
            }
        }
    }

    /**
     * http post json
     *
     * @param url     请求地址
     * @param param   请求参数(json string)
     * @param headers 请求头
     * @return HttpResult
     */
    public static HttpResult post(String url, String param, Map<String, String> headers) throws IOException, ParseException {
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000L))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
                .setResponseTimeout(Timeout.ofMilliseconds(3000L))
                .build();
        HttpPost httpPost = new HttpPost(url);
        // 请求配置
        httpPost.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 请求参数
        httpPost.setEntity(new StringEntity(param, ContentType.APPLICATION_JSON));
        // http post json
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                String result = (entity == null) ? "" : EntityUtils.toString(entity);
                // 确保流被完全消费
                EntityUtils.consume(entity);
                return new HttpResult(response.getCode(), result);
            }
        }
    }

    /**
     * Http请求返回原始响应，用于获取cookie
     *
     * @param url
     * @param param
     * @param headers
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static CloseableHttpResponse httpPost(String url, String param, Map<String, String> headers) throws IOException, ParseException {
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000L))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
                .setResponseTimeout(Timeout.ofMilliseconds(3000L))
                .build();
        HttpPost httpPost = new HttpPost(url);
        // 请求配置
        httpPost.setConfig(requestConfig);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 请求参数
        httpPost.setEntity(new StringEntity(param, ContentType.APPLICATION_JSON));
        // http post json
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                return response;
            }
        }
    }
}
