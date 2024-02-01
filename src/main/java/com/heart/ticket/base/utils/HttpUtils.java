package com.heart.ticket.base.utils;

import com.heart.ticket.base.model.HttpResult;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * About:
 * Other:
 * Created: wfli on 2023/8/15 10:34.
 * Editored:
 */
public class HttpUtils {


    /**
     * http get
     *
     * @param url 请求地址
     * @param kv  请求参数，key=value&key=value...形式
     * @return HttpResult
     */
    public static HttpResult get(String url, String kv) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //  创建请求配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间
                .setConnectTimeout(Timeout.of(3000, TimeUnit.MILLISECONDS))
                // 设置响应超时时间
                .setResponseTimeout(3000, TimeUnit.MILLISECONDS)
                // 设置从连接池获取链接的超时时间
                .setConnectionRequestTimeout(3000, TimeUnit.MILLISECONDS)
                .build();

        if (!StringUtils.isEmpty(kv)) {
            url = url + "&" + kv;
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * http get
     *
     * @param url  请求地址
     * @param pMap 请求参数，map形式
     * @return HttpResult
     */
    public HttpResult get(String url, Map<String, Object> pMap) {


        return null;
    }

    /**
     * map转kv
     *
     * @param pMap
     * @return
     */
    private String buildParam(Map<String, Object> pMap) {
        if (pMap == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : pMap.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("&");
        }
        String param = stringBuilder.toString();
        if (param.length() > 0) {
            param = param.substring(0, param.length() - 1);
        }
        return param;
    }

    /**
     * http post json
     *
     * @param url  请求地址
     * @param p    请求参数，json字符串
     * @param hMap 请求头
     * @return HttpResult
     */
    public HttpResult postJson(String url, String p, Map<String, Object> hMap) {
        return null;
    }

    /**
     * http post json
     *
     * @param url  请求地址
     * @param pMap 请求参数，map形式
     * @param hMap 请求头
     * @return HttpResult
     */
    public HttpResult postJson(String url, Map<String, Object> pMap, Map<String, Object> hMap) {

        return null;
    }

    /**
     * http post form
     *
     * @param url  请求地址
     * @param pMap 请求参数，map形式
     * @param hMap 请求头
     * @return HttpResult
     */
    public HttpResult post(String url, Map<String, Object> pMap, Map<String, Object> hMap) {

        return null;
    }
}
