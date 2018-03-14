package com.cehome.apimanager.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;
import com.cehome.apimanager.service.IActionTesterService;

@RestController
@RequestMapping("/apimanager/tester")
public class ActionTesterController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ActionTesterController.class);

	@Autowired
	private IActionTesterService actionTesterService;

	/**
	 * 发送接口请求
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public Map<String, Object> send(@RequestBody ActionTesterReqDto dto) {
		try {
			JSONObject response = actionTesterService.send(dto);
			return toSuccess(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
