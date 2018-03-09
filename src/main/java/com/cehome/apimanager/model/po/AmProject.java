package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目
 * 
 * @author sunlei
 *
 */
public class AmProject implements Serializable {
	private static final long serialVersionUID = -5800508584386595622L;
	/**
	 * 项目编号
	 */
	private Integer id;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目描述
	 */
	private String projectDesc;
	/**
	 * 所属部门编号
	 */
	private Integer depId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
