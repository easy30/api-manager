package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmModuleDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmModuleQueryReqDto;
import com.cehome.apimanager.model.dto.AmModuleReqDto;
import com.cehome.apimanager.model.dto.AmModuleResDto;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.model.po.AmModule;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 模块业务接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmModuleServiceImpl implements IAmModuleService {

	@Autowired
	private AmModuleDao moduleDao;

	@Autowired
	private IAmActionService actionService;

	@Override
	public void add(AmModuleReqDto dto) {
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		moduleDao.add(dto);
	}

	@Override
	public void update(AmModuleReqDto dto) {
		dto.setUpdateTime(new Date());
		moduleDao.update(dto);
	}

	@Override
	public AmModuleResDto findById(Integer id) {
		AmModule amModule = moduleDao.get(id);
		if (amModule == null) {
			return null;
		}
		AmModuleResDto amModuleResDto = new AmModuleResDto();
		BeanUtils.copyProperties(amModule, amModuleResDto);
		return amModuleResDto;
	}

	@Override
	public void delete(AmModuleReqDto dto) {
		AmActionQueryReqDto actionQueryReqDto = new AmActionQueryReqDto();
		actionQueryReqDto.setModuleId(dto.getId());
		List<AmAction> actionList = actionService.list(actionQueryReqDto);
		if(actionList != null && !actionList.isEmpty()){
			throw new BizValidationException("模块下存在其他接口，不能删除！");
		}
		moduleDao.delete(dto.getId());
	}

	@Override
	public Page<AmModule> findPage(AmModuleQueryReqDto dto) {
		return moduleDao.find(dto);
	}

	@Override
	public List<AmModule> list(AmModuleQueryReqDto dto) {
		return moduleDao.list(dto);
	}

}
