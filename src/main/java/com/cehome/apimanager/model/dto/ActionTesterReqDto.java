package com.cehome.apimanager.model.dto;

import java.io.Serializable;

public class ActionTesterReqDto implements Serializable {
	private static final long serialVersionUID = -2605480638104880624L;
	private Integer requestType;
	private String requestUrl;
	private String requestHeadData;
	private String requestData;

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestHeadData() {
		return requestHeadData;
	}

	public void setRequestHeadData(String requestHeadData) {
		this.requestHeadData = requestHeadData;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

}
