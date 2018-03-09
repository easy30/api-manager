package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口
 * 
 * @author sunlei
 *
 */
public class AmAction implements Serializable {
	private static final long serialVersionUID = 8747150818253864391L;
	/**
	 * 接口编号
	 */
	private Integer id;
	/**
	 * 接口名称
	 */
	private String name;
	/**
	 * 接口描述
	 */
	private String actionDesc;
	/**
	 * 接口所属模块
	 */
	private String module;
	/**
	 * 接口请求类型
	 */
	private Integer requestType;
	/**
	 * 接口请求url
	 */
	private String requestUrl;
	/**
	 * 接口状态
	 */
	private Integer status;
	/**
	 * 接口请求头定义
	 */
	private String requestHeadDefinition;
	/**
	 * 接口请求参数定义
	 */
	private String requestDefinition;
	/**
	 * 接口返回结果定义
	 */
	private String responseDefinition;
	/**
	 * 接口请求头mock模板定义
	 */
	private String requestHeadMock;
	/**
	 * 接口请求参数mock模板定义
	 */
	private String requestMock;
	/**
	 * 接口响应模板定义
	 */
	private String responseMock;
	/**
	 * 创建人
	 */
	private Integer createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private Integer updateUser;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequestHeadDefinition() {
		return requestHeadDefinition;
	}

	public void setRequestHeadDefinition(String requestHeadDefinition) {
		this.requestHeadDefinition = requestHeadDefinition;
	}

	public String getRequestDefinition() {
		return requestDefinition;
	}

	public void setRequestDefinition(String requestDefinition) {
		this.requestDefinition = requestDefinition;
	}

	public String getResponseDefinition() {
		return responseDefinition;
	}

	public void setResponseDefinition(String responseDefinition) {
		this.responseDefinition = responseDefinition;
	}

	public String getRequestHeadMock() {
		return requestHeadMock;
	}

	public void setRequestHeadMock(String requestHeadMock) {
		this.requestHeadMock = requestHeadMock;
	}

	public String getRequestMock() {
		return requestMock;
	}

	public void setRequestMock(String requestMock) {
		this.requestMock = requestMock;
	}

	public String getResponseMock() {
		return responseMock;
	}

	public void setResponseMock(String responseMock) {
		this.responseMock = responseMock;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
