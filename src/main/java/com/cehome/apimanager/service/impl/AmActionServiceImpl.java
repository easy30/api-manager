package com.cehome.apimanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.service.IAmActionService;

/**
 * 动作业务接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmActionServiceImpl implements IAmActionService {

	@Autowired
	private AmActionDao actionDao;

	@Override
	public void add(AmActionReqDto dto) {
		buildMock(dto);
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		actionDao.add(dto);
	}

	@Override
	public void update(AmActionReqDto dto) {
		buildMock(dto);
		dto.setUpdateTime(new Date());
		actionDao.update(dto);
	}

	@Override
	public AmActionResDto findById(AmActionQueryReqDto dto) {
		AmAction amAction = actionDao.get(dto.getId());
		if (amAction == null) {
			throw new BizValidationException("接口不存在，接口编号【" + dto.getId() + "】");
		}
		AmActionResDto amActionResDto = new AmActionResDto();
		BeanUtils.copyProperties(amAction, amActionResDto);
		return amActionResDto;
	}

	@Override
	public void delete(AmActionReqDto dto) {
		actionDao.delete(dto.getId());
	}

	@Override
	public Page<AmAction> findPage(AmActionQueryReqDto dto) {
		return actionDao.find(dto);
	}

	@Override
	public List<AmAction> list(AmActionQueryReqDto dto) {
		return actionDao.list(dto);
	}

	private JSONObject buildMockData(List<JSONObject> columnList) {
		JSONObject mockObject = new JSONObject();
		for (JSONObject column : columnList) {
			int columnType = column.getIntValue("type");
			if (CommonMeta.JavaType.NUMBER.getCode() == columnType 
					|| CommonMeta.JavaType.STRING.getCode() == columnType
					|| CommonMeta.JavaType.BOOLEAN.getCode() == columnType
					|| CommonMeta.JavaType.ARRAY_NUMBER.getCode() == columnType
					|| CommonMeta.JavaType.ARRAY_STRING.getCode() == columnType
					|| CommonMeta.JavaType.ARRAY_BOOLEAN.getCode() == columnType) {
				String name = column.getString("name");
				String rule = StringUtils.isEmpty(column.getString("rule")) ? "" : "|" + column.getString("rule");
				Object value = column.get("value");
				mockObject.put(name + rule, value);
			} else if (CommonMeta.JavaType.OBJECT.getCode() == columnType) {
				String name = column.getString("name");
				List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
				JSONObject columnObject = buildMockData(children);
				mockObject.put(name, columnObject);
			} else if (CommonMeta.JavaType.ARRAY_OBJECT.getCode() == columnType) {
				JSONArray objectArray = new JSONArray();
				String name = column.getString("name");
				List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
				for (JSONObject child : children) {
					List<JSONObject> innerChildren = child.getJSONArray("child").toJavaList(JSONObject.class);
					JSONObject innerColumnObject = buildMockData(innerChildren);
					objectArray.add(innerColumnObject);
				}
				mockObject.put(name, objectArray);
			}
		}
		return mockObject;
	}

	private void buildMock(AmActionReqDto dto) {
		// 请求头mock模板生成
		String requestHeadDefinition = dto.getRequestHeadDefinition();
		if (!StringUtils.isEmpty(requestHeadDefinition)) {
			List<JSONObject> columnList = JSON.parseArray(requestHeadDefinition, JSONObject.class);
			JSONObject requestHeadMockData = buildMockData(columnList);
			dto.setRequestHeadMock(requestHeadMockData.toJSONString());
		}

		// 请求mock模板生成
		String requestDefinition = dto.getRequestDefinition();
		if (!StringUtils.isEmpty(requestDefinition)) {
			List<JSONObject> columnList = JSON.parseArray(requestDefinition, JSONObject.class);
			JSONObject requestMockData = buildMockData(columnList);
			dto.setRequestMock(requestMockData.toJSONString());
		}

		// 返回结果mock模板生成
		String responseDefinition = dto.getResponseDefinition();
		if (!StringUtils.isEmpty(responseDefinition)) {
			List<JSONObject> columnList = JSON.parseArray(responseDefinition, JSONObject.class);
			JSONObject responseMockData = buildMockData(columnList);
			dto.setResponseMock(responseMockData.toJSONString());
		}
	}
}
