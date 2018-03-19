package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmUserQueryReqDto;
import com.cehome.apimanager.model.dto.AmUserReqDto;
import com.cehome.apimanager.model.po.AmUser;

/**
 * 用户模块业务接口
 * 
 * @author sunlei
 *
 */
public interface IAmUserService {
	/**
	 * 添加用户
	 * 
	 * @param dto
	 */
	void add(AmUserReqDto dto);

	/**
	 * 更新用户
	 * 
	 * @param dto
	 */
	void update(AmUserReqDto dto);

	/**
	 * 删除用户
	 * 
	 * @param dto
	 */
	void delete(AmUserReqDto dto);

	/**
	 * 分页获取用户列表
	 * 
	 * @param dto
	 * @return
	 */
	Page<AmUser> findPage(AmUserQueryReqDto dto);

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	AmUser findById(Integer id);

	/**
	 * 用户登录校验
	 * 
	 * @param dto
	 */
	void login(AmUserReqDto dto);
}
