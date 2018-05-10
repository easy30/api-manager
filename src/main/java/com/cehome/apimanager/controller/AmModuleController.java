package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmModuleQueryReqDto;
import com.cehome.apimanager.model.dto.AmModuleReqDto;
import com.cehome.apimanager.model.dto.AmModuleResDto;
import com.cehome.apimanager.model.po.AmModule;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmModuleService;
import com.cehome.apimanager.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/module")
public class AmModuleController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmModuleController.class);
	
	@Autowired
	private IAmModuleService moduleService;
	
	/**
	 * 添加模块
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("add")
	public Map<String, Object> add(HttpSession session, AmModuleReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			moduleService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新模块
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	public Map<String, Object> update(HttpSession session, AmModuleReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			moduleService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据主键返回模块
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(Integer id) {
		try {
			AmModuleResDto amModuleResDto = moduleService.findById(id);
			return toSuccess(amModuleResDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id删除模块
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(HttpSession session, AmModuleReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			moduleService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页查询模块列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmModuleQueryReqDto dto) {
		try {
			Page<AmModule> page = moduleService.findPage(dto);
			return toSuccess(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
	
	/**
	 * 获取模块列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("list")
	public Map<String, Object> list(AmModuleQueryReqDto dto) {
		try {
			List<AmModule> list = moduleService.list(dto);
			return toSuccess(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
