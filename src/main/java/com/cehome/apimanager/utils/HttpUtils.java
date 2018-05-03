package com.cehome.apimanager.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public HttpEntity execute(String url, JSONObject headers, JSONObject nameValuePair) {
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

            List<NameValuePair> nvps = new ArrayList<>();
            if (nameValuePair != null && !nameValuePair.isEmpty()) {
                for (String key : nameValuePair.keySet()) {
                    NameValuePair nvp = new BasicNameValuePair(key, nameValuePair.getString(key));
                    nvps.add(nvp);
                }
            }

            HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, CHARSET);
            httpPost.setEntity(httpEntity);
            HttpResponse response = httpclient.execute(httpPost);
            return response.getEntity();
        } catch (Exception e) {
            logger.error("sendRequest error!", e);
        }
        return null;
    }

    public HttpEntity execute(String url, JSONObject headers, String requestBody) {
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

    public void loginForCookie(String domainName, String requestUrl, Integer requestType, String requestBody) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = httpClientBuilder.build();
        try {
            if (requestUrl.charAt(0) != '/') {
                requestUrl = "/" + requestUrl;
            }
            String url = "http://" + domainName + requestUrl;
            HttpPost httpPost = new HttpPost(url);
            if (CommonMeta.RequestType.GET.getCode() == requestType) {
                JSONObject nameValuePair = JSON.parseObject(requestBody);
                List<NameValuePair> nvps = new ArrayList<>();
                if (nameValuePair != null && !nameValuePair.isEmpty()) {
                    for (String key : nameValuePair.keySet()) {
                        NameValuePair nvp = new BasicNameValuePair(key, nameValuePair.getString(key));
                        nvps.add(nvp);
                    }
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, CHARSET);
                httpPost.setEntity(httpEntity);
            } else {
                StringEntity stringEntity = new StringEntity(requestBody, CHARSET);
                stringEntity.setContentType(APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }
            HttpResponse response = httpclient.execute(httpPost);
            setCookieStore(response, domainName);
        } catch (Exception e) {
            logger.error("sendRequest error!", e);
        }
    }

    private void setCookieStore(HttpResponse httpResponse, String domain) {
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
        cookie.setDomain(domain);
        cookieStore.addCookie(cookie);
    }

    public void clearCookieStore() {
        cookieStore.clear();
    }
}
