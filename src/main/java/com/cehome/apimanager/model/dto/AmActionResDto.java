package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmAction;

public class AmActionResDto extends AmAction {
	private static final long serialVersionUID = -3106636895944003212L;
	private String requestHeadData;
	private String requestData;

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
