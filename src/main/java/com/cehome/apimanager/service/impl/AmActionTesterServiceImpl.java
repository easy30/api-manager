package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmActionTesterService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.utils.HttpUtils;
import com.cehome.apimanager.utils.MockUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 接口测试
 * 
 * @author sunlei
 *
 */
@Service
public class AmActionTesterServiceImpl implements IAmActionTesterService {
	@Autowired
	private IAmActionService actionService;

	@Autowired
	private IAmDomainService domainService;

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

	@Override
	public JSONObject sendByActionId(ActionTesterReqDto dto) {
		JSONObject responseInfo = new JSONObject();
		responseInfo.put("requestUrl", dto.getRequestUrl());
		Integer envId = dto.getEnvId();
		Integer actionId = dto.getActionId();
		AmActionResDto actionResDto = actionService.findById(actionId);
		if(actionResDto == null){
			responseInfo.put("code", -1);
			responseInfo.put("result", "接口不存在，接口编号【" + actionId + "】");
			return responseInfo;
		}
		AmDomain domain = domainService.findById(actionResDto.getDomainId());
		AmDomainQueryReqDto domainQueryReqDto = new AmDomainQueryReqDto();
		domainQueryReqDto.setEnvId(envId);
		domainQueryReqDto.setDomainName(domain.getDomainName().split("\\.")[0]);
		List<AmDomain> domainList = domainService.list(domainQueryReqDto);
		if(domainList == null || domainList.isEmpty()){
			responseInfo.put("code", -1);
			responseInfo.put("result", "域名不存在，环境变量编号【" + envId + "】 域名编号【" + actionResDto.getDomainId() + "】");
			return responseInfo;
		}
		AmDomain firstDomain = domainList.get(0);
		Integer requestType = actionResDto.getRequestType();
		String requestUrl = actionResDto.getRequestUrl();
		String domainName = firstDomain.getDomainName();
		String requestHeadMock = actionResDto.getRequestHeadMock();
		String requestMock = actionResDto.getRequestMock();
		String requestData = MockUtils.buildMockData(requestMock);
		String requestHeadData = MockUtils.buildMockData(requestHeadMock);
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
			responseInfo.put("result", responseText);
			responseInfo.put("requestHeadData", requestHeadData);
			responseInfo.put("requestData", requestData);
			responseInfo.put("wholeUrl", url);
			JSONObject responseObject = JSON.parseObject(responseText);
			responseInfo.put("code", StringUtils.isEmpty(responseObject.getString("ret")) ? responseObject.getString("code") : responseObject.getString("ret"));
		} catch (Exception e) {
			responseInfo.put("code", -1);
			return responseInfo;
		}
		return responseInfo;
	}
}
