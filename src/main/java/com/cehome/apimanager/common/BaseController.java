package com.cehome.apimanager.common;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	private static final String RESULT_CODE = "code";
	private static final String RESULT_DATA = "data";
	private static final String RESULT_MSG = "msg";
	private static final String DEFAULT_MSG = "服务处理异常！";
	
	protected Map<String, Object> toSuccess(Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put(RESULT_CODE, 0);
		result.put(RESULT_DATA, value);
		return result;
	}

	protected Map<String, Object> toSuccess() {
		return toSuccess(null);
	}

	protected Map<String, Object> toFail(String errorMessage) {
		Map<String, Object> result = new HashMap<>();
		result.put(RESULT_CODE, -1);
		result.put(RESULT_MSG, errorMessage);
		return result;
	}

	protected Map<String, Object> toFail() {
		return toFail(DEFAULT_MSG);
	}
}
