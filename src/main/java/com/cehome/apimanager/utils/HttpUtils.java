package com.cehome.apimanager.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * http工具类
 * 
 * @author sunlei
 *
 */
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static final String CHARSET = "UTF-8";
	private static final String APPLICATION_JSON = "application/json;charset=" + CHARSET;
	private HttpUtils() {
	}

	public static HttpUtils getInstance() {
		return new HttpUtils();
	}
	
	public HttpEntity execute(String url, JSONObject headers, JSONObject nameValuePair) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			if(headers != null && !headers.isEmpty()){
				for(String key : headers.keySet()){
					Header header = new BasicHeader(key, headers.getString(key));
					httpPost.addHeader(header);
				}
			}
			List<NameValuePair> nvps = new ArrayList<>();
			if(nameValuePair != null && !nameValuePair.isEmpty()){
				for(String key : nameValuePair.keySet()){
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
		HttpClient httpclient = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			if(headers != null && !headers.isEmpty()){
				for(String key : headers.keySet()){
					Header header = new BasicHeader(key, headers.getString(key));
					httpPost.addHeader(header);
				}
			}
			
			StringEntity stringEntity = new StringEntity(requestBody,CHARSET);
			stringEntity.setContentType(APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);
			return response.getEntity();
		} catch (Exception e) {
			logger.error("sendRequest error!", e);
		}
		return null;
	}
}
