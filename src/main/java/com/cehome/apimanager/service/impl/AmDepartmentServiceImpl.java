package com.cehome.apimanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmDepartmentDao;
import com.cehome.apimanager.dao.AmUserDepartmentDao;
import com.cehome.apimanager.model.dto.AmDepartmentQueryReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentReqDto;
import com.cehome.apimanager.model.dto.AmDepartmentResDto;
import com.cehome.apimanager.model.po.AmDepartment;
import com.cehome.apimanager.service.IAmDepartmentService;

/**
 * 部门业务接口实现类
 * 
 * @author sunlei
 *
 */
@Service
public class AmDepartmentServiceImpl implements IAmDepartmentService {
	@Autowired
	private AmDepartmentDao departmentDao;
	
	@Autowired
	private AmUserDepartmentDao userDepartmentDao;
	
	public Integer add(AmDepartmentReqDto dto) {
		AmDepartment entity = new AmDepartment();
		BeanUtils.copyProperties(dto, entity);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		departmentDao.add(entity);
		return entity.getId();
	}

	public void update(AmDepartmentReqDto dto) {
		AmDepartment entity = new AmDepartment();
		BeanUtils.copyProperties(dto, entity);
		entity.setUpdateTime(new Date());
		departmentDao.update(entity);
	}

	public AmDepartmentResDto findById(AmDepartmentQueryReqDto dto) {
		AmDepartment amDepartment = departmentDao.get(dto.getId());
		if(amDepartment == null){
			return null;
		}
		AmDepartmentResDto amDepartmentResDto = new AmDepartmentResDto();
		BeanUtils.copyProperties(amDepartment, amDepartmentResDto);
		return amDepartmentResDto;
	}

	@Override
	public void delete(AmDepartmentReqDto dto) {
		userDepartmentDao.deleteByDepId(dto.getId());
		departmentDao.delete(dto.getId());
	}

	@Override
	public Page<AmDepartment> findPage(AmDepartmentQueryReqDto dto) {
		return departmentDao.find(dto);
	}
	
	@Override
	public List<AmDepartment> list(AmDepartmentQueryReqDto dto) {
		return departmentDao.list(dto);
	}
}
