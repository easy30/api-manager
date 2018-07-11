package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmAction;

public class AmActionResDto extends AmAction {
	private static final long serialVersionUID = -3106636895944003212L;
	private String requestHeadData;
	private String requestData;
	private String createUserName = "";
	private String updateUserName = "";

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
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
