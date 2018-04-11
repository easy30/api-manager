package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.service.IAmActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/action")
public class AmActionController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmActionController.class);

	@Autowired
	private IAmActionService actionService;

	/**
	 * 添加接口文档
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Map<String, Object> add(@RequestBody AmActionReqDto dto) {
		try {
			actionService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新接口文档
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String, Object> update(@RequestBody AmActionReqDto dto) {
		try {
			actionService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据主键返回接口文档
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(Integer id) {
		try {
			AmActionResDto amActionResDto = actionService.findById(id);
			return toSuccess(amActionResDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id删除接口文档
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(AmActionReqDto dto) {
		try {
			actionService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页查询接口文档列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmActionQueryReqDto dto) {
		try {
			Page<AmAction> page = actionService.findPage(dto);
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
	public Map<String, Object> list(AmActionQueryReqDto dto) {
		try {
			List<AmAction> list = actionService.list(dto);
			return toSuccess(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
