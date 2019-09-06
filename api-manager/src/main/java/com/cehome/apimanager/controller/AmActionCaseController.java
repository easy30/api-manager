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
import com.cehome.apimanager.model.dto.AmActionCaseQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseResDto;
import com.cehome.apimanager.model.po.AmActionCase;
import com.cehome.apimanager.service.IAmActionCaseService;

@RestController
@RequestMapping("/apimanager/actioncase")
public class AmActionCaseController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmActionCaseController.class);
	
	@Autowired
	private IAmActionCaseService actionCaseService;
	
	/**
	 * 添加测试用例
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("add")
	public Map<String, Object> add(AmActionCaseReqDto dto) {
		try {
			actionCaseService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新测试用例
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	public Map<String, Object> update(AmActionCaseReqDto dto) {
		try {
			actionCaseService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据主键返回测试用例
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(AmActionCaseQueryReqDto dto) {
		try {
			AmActionCaseResDto amActionCaseResDto = actionCaseService.findById(dto);
			return toSuccess(amActionCaseResDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id删除测试用例
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(AmActionCaseReqDto dto) {
		try {
			actionCaseService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页查询测试用例列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmActionCaseQueryReqDto dto) {
		try {
			Page<AmActionCase> page = actionCaseService.findPage(dto);
			return toSuccess(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
	
	/**
	 * 获取测试用例列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("list")
	public Map<String, Object> list(AmActionCaseQueryReqDto dto) {
		try {
			List<AmActionCase> list = actionCaseService.list(dto);
			return toSuccess(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
