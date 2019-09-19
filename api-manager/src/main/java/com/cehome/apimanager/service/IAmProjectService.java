package com.cehome.apimanager.service;

import java.util.List;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmProjectQueryReqDto;
import com.cehome.apimanager.model.dto.AmProjectReqDto;
import com.cehome.apimanager.model.dto.AmProjectResDto;
import com.cehome.apimanager.model.po.AmProject;

/**
 * 项目业务接口
 * 
 * @author sunlei
 *
 */
public interface IAmProjectService {
	/**
	 * 添加项目
	 * 
	 * @param dto
	 */
	void add(AmProjectReqDto dto);

	/**
	 * 更新项目
	 * 
	 * @param dto
	 */
	void update(AmProjectReqDto dto);

	/**
	 * 根据主键获取项目
	 * 
	 * @param dto
	 * @return
	 */
	AmProjectResDto findById(Integer id);

	/**
	 * 删除项目
	 * 
	 * @param dto
	 */
	void delete(AmProjectReqDto dto);

	/**
	 * 分页获取项目列表
	 * 
	 * @param dto
	 * @return
	 */
	Page<AmProject> findPage(AmProjectQueryReqDto dto);

	/**
	 * 获取项目列表
	 * 
	 * @param dto
	 * @return
	 */
	List<AmProject> list(AmProjectQueryReqDto dto);
}
