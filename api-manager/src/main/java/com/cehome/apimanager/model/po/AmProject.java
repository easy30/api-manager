package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 项目
 * 
 * @author sunlei
 *
 */
public class AmProject extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5800508584386595622L;
	/**
	 * 项目编号
	 */
	private Integer id;
	/**
	 * 项目名称
	 */
	@FiledDesc(desc = "项目名称")
	private String projectName;
	/**
	 * 项目描述
	 */
	@FiledDesc(desc = "项目描述")
	private String projectDesc;
	/**
	 * 所属部门编号
	 */
	@FiledDesc(desc = "所属部门")
	private Integer depId;
	/**
	 * 创建人
	 */
	private Integer createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改人
	 */
	private Integer updateUser;
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

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AmProject amProject = (AmProject) o;
		return Objects.equals(projectName, amProject.projectName) &&
				Objects.equals(projectDesc, amProject.projectDesc) &&
				Objects.equals(depId, amProject.depId);
	}

	@Override
	public int hashCode() {

		return Objects.hash(projectName, projectDesc, depId);
	}
}
