package com.cehome.apimanager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.service.IAmActionService;

/**
 * 动作业务接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmActionServiceImpl implements IAmActionService {

	@Override
	public void add(AmActionReqDto dto) {
		
	}

	@Override
	public void update(AmActionReqDto dto) {
		
	}

	@Override
	public AmActionResDto findById(AmActionQueryReqDto dto) {
		return null;
	}

	@Override
	public void delete(AmActionReqDto dto) {
		
	}

	@Override
	public Page<AmAction> findPage(AmActionQueryReqDto dto) {
		return null;
	}

	@Override
	public List<AmAction> list(AmActionQueryReqDto dto) {
		return null;
	}

}
