package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmDepartmentQueryReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentResDto;
import com.cehome.apimanager.model.po.AmDepartment;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmDepartmentService;
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
@RequestMapping("/apimanager/department")
public class AmDepartmentController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AmDepartmentController.class);

	@Autowired
	private IAmDepartmentService departmentService;

	/**
	 * 添加部门
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("add")
	public Map<String, Object> add(HttpSession session, AmDepartmentReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			departmentService.add(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 更新部门
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("update")
	public Map<String, Object> update(HttpSession session, AmDepartmentReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			departmentService.update(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据主键返回部门
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findById")
	public Map<String, Object> findById(AmDepartmentQueryReqDto dto) {
		try {
			AmDepartmentResDto amDepartmentResDto = departmentService.findById(dto);
			return toSuccess(amDepartmentResDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 根据id删除部门
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("delete")
	public Map<String, Object> delete(HttpSession session, AmDepartmentReqDto dto) {
		try {
			AmUser loginUser = WebUtils.getLoginUser(session);
			dto.setOperateUser(loginUser.getId());
			departmentService.delete(dto);
			return toSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}

	/**
	 * 分页查询部门列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("findPage")
	public Map<String, Object> findPage(AmDepartmentQueryReqDto dto) {
		try {
			Page<AmDepartment> page = departmentService.findPage(dto);
			return toSuccess(page);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
	
	/**
	 * 获取部门列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("list")
	public Map<String, Object> list(AmDepartmentQueryReqDto dto) {
		try {
			List<AmDepartment> list = departmentService.list(dto);
			return toSuccess(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return toFail(e.getMessage());
		}
	}
}
