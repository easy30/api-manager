package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;
import com.cehome.apimanager.service.IAmActionTesterService;
import com.cehome.apimanager.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * 接口测试
 * 
 * @author sunlei
 *
 */
@Service
public class AmActionTesterServiceImpl implements IAmActionTesterService {

	@Override
	public JSONObject send(ActionTesterReqDto dto) { 
		Integer requestType = dto.getRequestType();
		String requestHeadData = dto.getRequestHeadData();
		String requestData = dto.getRequestData();
		String requestUrl = dto.getRequestUrl();
		String domainName = dto.getDomainName();
		String url = "http://" + domainName + (requestUrl.charAt(0) == '/' ? requestUrl : "/" + requestUrl);
		HttpUtils httpUtils = HttpUtils.getInstance();
		HttpEntity responseEntity = null;
		String responseText = "";
		try {
			if (CommonMeta.RequestType.GET.getCode() == requestType) {
				responseEntity = httpUtils.execute(url, JSON.parseObject(requestHeadData), JSON.parseObject(requestData));
			} else if (CommonMeta.RequestType.POST.getCode() == requestType) {
				responseEntity = httpUtils.execute(url, JSON.parseObject(requestHeadData), requestData);
			}
			responseText = EntityUtils.toString(responseEntity, "UTF-8");
		} catch (Exception e) {
			throw new BizValidationException("服务处理异常！", e);
		}
		return JSON.parseObject(responseText);
	}
}
