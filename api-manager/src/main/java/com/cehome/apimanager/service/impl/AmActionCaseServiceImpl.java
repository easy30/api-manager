package com.cehome.apimanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionCaseDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.AmActionCaseQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseReqDto;
import com.cehome.apimanager.model.dto.AmActionCaseResDto;
import com.cehome.apimanager.model.po.AmActionCase;
import com.cehome.apimanager.service.IAmActionCaseService;

/**
 * 接口测试用例业务接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmActionCaseServiceImpl implements IAmActionCaseService {

	@Autowired
	private AmActionCaseDao actionCaseDao;
	
	@Override
	public void add(AmActionCaseReqDto dto) {
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		actionCaseDao.add(dto);
	}

	@Override
	public void update(AmActionCaseReqDto dto) {
		dto.setUpdateTime(new Date());
		actionCaseDao.update(dto);
	}

	@Override
	public AmActionCaseResDto findById(AmActionCaseQueryReqDto dto) {
		AmActionCase amActionCase = actionCaseDao.get(dto.getId());
		if(amActionCase == null){
			throw new BizValidationException("测试用例不存在，用例编号【" + dto.getId() + "】");
		}
		AmActionCaseResDto amActionCaseResDto = new AmActionCaseResDto();
		BeanUtils.copyProperties(amActionCase, amActionCaseResDto);
		return amActionCaseResDto;
	}

	@Override
	public void delete(AmActionCaseReqDto dto) {
		actionCaseDao.delete(dto.getId());
	}

	@Override
	public Page<AmActionCase> findPage(AmActionCaseQueryReqDto dto) {
		return actionCaseDao.find(dto);
	}

	@Override
	public List<AmActionCase> list(AmActionCaseQueryReqDto dto) {
		return actionCaseDao.list(dto);
	}

}
