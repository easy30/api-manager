package com.cehome.apimanager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;
import com.cehome.apimanager.service.IActionTesterService;

/**
 * 接口测试
 * 
 * @author sunlei
 *
 */
@Service
public class ActionTesterServiceImpl implements IActionTesterService {
	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject send(ActionTesterReqDto dto) { Integer requestType = dto.getRequestType();
		String requestHeadData = dto.getRequestHeadData();
		String requestData = dto.getRequestData();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		if(!StringUtils.isEmpty(requestHeadData)){
			JSONObject requestHeadObject = JSON.parseObject(requestHeadData);
			Set<String> keySet = requestHeadObject.keySet();
			for(String key : keySet){
				requestHeaders.add(key, requestHeadObject.getString(key));
			}
		}
		
		Map<String, Object> params = JSON.parseObject(requestData, Map.class);
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestData, requestHeaders);
		ResponseEntity<JSONObject> response = restTemplate.exchange(dto.getRequestUrl(), 
				CommonMeta.RequestType.GET.getCode() == requestType ? HttpMethod.GET : HttpMethod.POST, 
						requestEntity, JSONObject.class, params);
		return response.getBody();
	}
}
