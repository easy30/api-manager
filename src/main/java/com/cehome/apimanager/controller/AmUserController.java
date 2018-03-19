package com.cehome.apimanager.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmUserQueryReqDto;
import com.cehome.apimanager.model.dto.AmUserReqDto;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmUserService;

@RestController
@RequestMapping("/apimanager/user")
public class AmUserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmUserController.class);

	@Autowired
	private IAmUserService userService;

	/**
	 * 注册用户
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("register")
	public Map<String, Object> register(AmUserReqDto dto) {
		try {
			userService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 用户登录校验
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("login")
	public Map<String, Object> login(AmUserReqDto dto) {
		try {
			userService.login(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	public Map<String, Object> update(AmUserReqDto dto) {
		try {
			userService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(AmUserReqDto dto) {
		try {
			userService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页获取用户列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmUserQueryReqDto dto) {
		try {
			Page<AmUser> page = userService.findPage(dto);
			return toSuccess(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(Integer id) {
		try {
			AmUser user = userService.findById(id);
			return toSuccess(user);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
