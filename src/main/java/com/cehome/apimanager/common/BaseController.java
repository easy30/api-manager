package com.cehome.apimanager.common;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	private static final String RESULT_CODE = "code";
	private static final String RESULT_DATA = "data";
	private static final String RESULT_MSG = "msg";
	private static final String STACK_TRACE = "stackTrace";
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
		result.put(STACK_TRACE, "");
		return result;
	}

	protected Map<String, Object> toFail(String errorMessage, Exception e) {
		Map<String, Object> result = new HashMap<>();
		result.put(RESULT_CODE, -1);
		result.put(RESULT_MSG, errorMessage);
		result.put(STACK_TRACE, getStackTrace(e));
		return result;
	}

	protected String getStackTrace(Exception e) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(e.getClass().getName() + "\n");
		StackTraceElement[] stackTrace = e.getStackTrace();
		for(StackTraceElement stackTraceElement : stackTrace){
			buffer.append(stackTraceElement.toString() + "\n");
		}
		return buffer.toString();
	}

	protected Map<String, Object> toFail() {
		return toFail(DEFAULT_MSG);
	}
}
