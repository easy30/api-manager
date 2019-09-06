package com.cehome.apimanager.model.po;

import com.cehome.apimanager.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口测试用例
 * 
 * @author sunlei
 *
 */
public class AmActionCase extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 8906274330372177061L;
	/**
	 * 用例编号
	 */
	private Integer id;
	/**
	 * 接口编号
	 */
	private Integer actionId;
	/**
	 * 用例描述
	 */
	private String actionCaseDesc;
	/**
	 * 用例请求头数据
	 */
	private String requestHeadData;
	/**
	 * 用例请求数据
	 */
	private String requestData;
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

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionCaseDesc() {
		return actionCaseDesc;
	}

	public void setActionCaseDesc(String actionCaseDesc) {
		this.actionCaseDesc = actionCaseDesc;
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
