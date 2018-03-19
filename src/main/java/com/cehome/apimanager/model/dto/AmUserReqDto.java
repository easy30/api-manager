package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmUser;

public class AmUserReqDto extends AmUser {
	private static final long serialVersionUID = 247303649607546457L;
	/**
	 * 部门编号，多个用逗号分开
	 */
	private String depIds;

	public String getDepIds() {
		return depIds;
	}

	public void setDepIds(String depIds) {
		this.depIds = depIds;
	}

}
