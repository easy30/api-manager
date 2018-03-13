package com.cehome.apimanager.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmProjectQueryReqDto;
import com.cehome.apimanager.model.dto.AmProjectReqDto;
import com.cehome.apimanager.model.dto.AmProjectResDto;
import com.cehome.apimanager.model.po.AmProject;
import com.cehome.apimanager.service.IAmProjectService;

@RestController
@RequestMapping("/apimanager/project")
public class AmProjectController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmProjectController.class);
	
	@Autowired
	private IAmProjectService projectService;
	
	/**
	 * 添加项目
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("add")
	public Map<String, Object> add(AmProjectReqDto dto) {
		try {
			projectService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新项目
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	public Map<String, Object> update(AmProjectReqDto dto) {
		try {
			projectService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据主键返回项目
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(AmProjectQueryReqDto dto) {
		try {
			AmProjectResDto amProjectResDto = projectService.findById(dto);
			return toSuccess(amProjectResDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id删除项目
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(AmProjectReqDto dto) {
		try {
			projectService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页查询项目列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmProjectQueryReqDto dto) {
		try {
			Page<AmProject> page = projectService.findPage(dto);
			return toSuccess(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
	
	/**
	 * 获取项目列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("list")
	public Map<String, Object> list(AmProjectQueryReqDto dto) {
		try {
			List<AmProject> list = projectService.list(dto);
			return toSuccess(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
