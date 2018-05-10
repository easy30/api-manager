package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;
import com.cehome.apimanager.common.FiledDesc;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 部门
 * 
 * @author sunlei
 *
 */
public class AmDepartment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 7293669672083896796L;
	/**
	 * 部门编号
	 */
	private Integer id;
	/**
	 * 部门名称
	 */
	@FiledDesc(desc = "部门名称")
	private String depName;
	/**
	 * 部门描述
	 */
	@FiledDesc(desc = "部门描述")
	private String depDesc;
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

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepDesc() {
		return depDesc;
	}

	public void setDepDesc(String depDesc) {
		this.depDesc = depDesc;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AmDepartment that = (AmDepartment) o;
		return Objects.equals(depName, that.depName) &&
				Objects.equals(depDesc, that.depDesc);
	}

	@Override
	public int hashCode() {

		return Objects.hash(depName, depDesc);
	}
}
