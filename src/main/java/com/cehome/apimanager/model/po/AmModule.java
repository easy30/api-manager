package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 模块
 * 
 * @author sunlei
 *
 */
public class AmModule implements Serializable {
	private static final long serialVersionUID = 2547463251379365668L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 模块描述
	 */
	private String moduleDesc;
	/**
	 * 模块所属项目
	 */
	private Integer projectId;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
