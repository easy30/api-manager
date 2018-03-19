package com.cehome.apimanager.service;

import java.util.List;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmModuleQueryReqDto;
import com.cehome.apimanager.model.dto.AmModuleReqDto;
import com.cehome.apimanager.model.dto.AmModuleResDto;
import com.cehome.apimanager.model.po.AmModule;

/**
 * 模块业务接口
 * 
 * @author sunlei
 *
 */
public interface IAmModuleService {

	void add(AmModuleReqDto dto);

	void update(AmModuleReqDto dto);

	AmModuleResDto findById(Integer id);

	void delete(AmModuleReqDto dto);

	Page<AmModule> findPage(AmModuleQueryReqDto dto);

	List<AmModule> list(AmModuleQueryReqDto dto);

}
