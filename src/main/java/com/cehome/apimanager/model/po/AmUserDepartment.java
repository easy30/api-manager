package com.cehome.apimanager.model.po;

import java.io.Serializable;

public class AmUserDepartment implements Serializable {
	private static final long serialVersionUID = -3191793747869050453L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 部门id
	 */
	private Integer depId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

}
