package com.cehome.apimanager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 
 * @author sunlei
 *
 */
public class AmUser implements Serializable {
	private static final long serialVersionUID = 6379063724660025179L;
	/**
	 * 用户编号
	 */
	private Integer id;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 登录账号
	 */
	private String account;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 角色编号
	 */
	private Integer roleId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}
}
