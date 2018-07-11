package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmDepartment;

public class AmDepartmentResDto extends AmDepartment {
	private static final long serialVersionUID = 7299211865280041201L;
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
