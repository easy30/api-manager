package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmProject;

public class AmProjectResDto extends AmProject {
	private static final long serialVersionUID = 520433647934926678L;
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
