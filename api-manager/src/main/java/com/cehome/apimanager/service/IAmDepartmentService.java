package com.cehome.apimanager.service;

import java.util.List;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmDepartmentQueryReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentResDto;
import com.cehome.apimanager.model.po.AmDepartment;

/**
 * 部门业务接口
 * 
 * @author sunlei
 *
 */
public interface IAmDepartmentService {
	/**
	 * 增加部门
	 * 
	 * @param dto
	 */
	Integer add(AmDepartmentReqDto dto);

	/**
	 * 更新部门
	 * 
	 * @param dto
	 */
	void update(AmDepartmentReqDto dto);

	/**
	 * 根据id返回部门信息
	 * 
	 * @param dto
	 * @return
	 */
	AmDepartmentResDto findById(AmDepartmentQueryReqDto dto);

	/**
	 * 根据id删除部门
	 * 
	 * @param dto
	 */
	void delete(AmDepartmentReqDto dto);

	/**
	 * 分页返回部门列表
	 * 
	 * @param dto
	 * @return
	 */
	Page<AmDepartment> findPage(AmDepartmentQueryReqDto dto);

	/**
	 * 获取部门列表
	 * 
	 * @param dto
	 * @return
	 */
	List<AmDepartment> list(AmDepartmentQueryReqDto dto);
}
