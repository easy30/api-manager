package com.cehome.apimanager.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http工具类
 *
 * @author sunlei
 */
public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final String CHARSET = "UTF-8";
    private static final String APPLICATION_JSON = "application/json;charset=" + CHARSET;
    private static CookieStore cookieStore = new BasicCookieStore();
    private static List<Header> headers = new ArrayList<>();
    private static Map<String, String> tokenPool = new ConcurrentHashMap<>();
    private static HttpUtils httpUtils = null;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    public HttpEntity execute(String domain, String url, JSONObject headers, JSONObject nameValuePair) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        CloseableHttpClient httpclient = httpClientBuilder.build();
        try {
            String queryString = getQueryString(nameValuePair);
            HttpGet httpGet = new HttpGet(url + queryString);
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    Header header = new BasicHeader(key, headers.getString(key));
                    httpGet.addHeader(header);
                }
            }
            String token = tokenPool.get(domain);
            if(!StringUtils.isEmpty(token)){
                httpGet.setHeader("Authorization", token);
            }
            HttpResponse response = httpclient.execute(httpGet);
            return response.getEntity();
        } catch (Exception e) {
            logger.error("sendRequest error!", e);
        }
        return null;
    }

    public HttpEntity execute(String domain, String url, JSONObject headers, String requestBody) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        CloseableHttpClient httpclient = httpClientBuilder.build();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    Header header = new BasicHeader(key, headers.getString(key));
                    httpPost.addHeader(header);
                }
            }
            String token = tokenPool.get(domain);
            if(!StringUtils.isEmpty(token)){
                httpPost.setHeader("Authorization", token);
            }
            StringEntity stringEntity = new StringEntity(requestBody, CHARSET);
            stringEntity.setContentType(APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpclient.execute(httpPost);
            return response.getEntity();
        } catch (Exception e) {
            logger.error("sendRequest error!", e);
        }
        return null;
    }

    public boolean loginForCookie(String domainName, String requestUrl, Integer requestType, String requestBody) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = httpClientBuilder.build();
        try {
            if (requestUrl.charAt(0) != '/') {
                requestUrl = "/" + requestUrl;
            }
            String url = "http://" + domainName + requestUrl;
            HttpResponse response = null;
            if (CommonMeta.RequestType.GET.getCode() == requestType) {
                String queryString = getQueryString(JSON.parseObject(requestBody));
                HttpGet httpGet = new HttpGet(url + queryString);
                response = httpclient.execute(httpGet);
            } else {
                HttpPost httpPost = new HttpPost(url);
                StringEntity stringEntity = new StringEntity(requestBody, CHARSET);
                stringEntity.setContentType(APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
                response = httpclient.execute(httpPost);
            }
            // 判断接口的认证类型
            setAuthInfo(response, domainName);
        } catch (Exception e) {
            logger.error("sendRequest error!", e);
            return false;
        }
        return true;
    }

    private void setAuthInfo(HttpResponse httpResponse, String domain) {
        Header firstHeader = httpResponse.getFirstHeader("Set-Cookie");
        if(firstHeader != null){
            String setCookie = firstHeader.getValue();
            String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
            BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
            cookie.setDomain(domain);
            cookieStore.addCookie(cookie);
        } else {
            try {
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject resultObject = JSON.parseObject(result);
                String code = resultObject.getString("code");
                if(!StringUtils.isEmpty(code) && code.equals("0")){
                    String token = resultObject.getJSONObject("data").getString("token");
                    tokenPool.put(domain, token);
                }
            } catch (IOException e) {
                logger.error("sendRequest error!", e);
            }
        }
    }

    private String getQueryString(JSONObject nameValuePair) {
        if (nameValuePair == null || nameValuePair.isEmpty()) {
            return "";
        }

        int capacity = nameValuePair.size() * 30; //设置表单长度30字节*N个请求参数
        //参数不为空，在URL后面添加head（“？”）
        StringBuilder buffer = new StringBuilder(capacity);
        buffer.append("?");
        //取出Map里面的请求参数，添加到表单String中。每个参数之间键值对之间用“=”连接，参数与参数之间用“&”连接
        Iterator<Map.Entry<String, Object>> it = nameValuePair.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            buffer.append(entry.getKey());
            buffer.append('=');
            buffer.append(entry.getValue());
            if (it.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    private void setHeader(HttpResponse httpResponse){

    }
    public void clearCookieStore() {
        cookieStore.clear();
    }
}
