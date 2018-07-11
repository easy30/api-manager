package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmModule;

public class AmModuleResDto extends AmModule {
	private static final long serialVersionUID = 6832274562820750513L;
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
}
