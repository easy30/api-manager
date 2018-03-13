package com.cehome.apimanager.service;

import java.util.List;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionCaseQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseResDto;
import com.cehome.apimanager.model.po.AmActionCase;

/**
 * 接口测试用例业务接口
 * 
 * @author sunlei
 *
 */
public interface IAmActionCaseService {

	/**
	 * 添加测试用例
	 * 
	 * @param dto
	 */
	void add(AmActionCaseReqDto dto);

	/**
	 * 更新用例
	 * 
	 * @param dto
	 */
	void update(AmActionCaseReqDto dto);

	/**
	 * 根据用例id获取用例信息
	 * 
	 * @param dto
	 * @return
	 */
	AmActionCaseResDto findById(AmActionCaseQueryReqDto dto);

	/**
	 * 根据id删除用例
	 * 
	 * @param dto
	 */
	void delete(AmActionCaseReqDto dto);

	/**
	 * 分页查询测试用例列表
	 * 
	 * @param dto
	 * @return
	 */
	Page<AmActionCase> findPage(AmActionCaseQueryReqDto dto);

	/**
	 * 返回测试用例列表
	 * 
	 * @param dto
	 * @return
	 */
	List<AmActionCase> list(AmActionCaseQueryReqDto dto);

}
