package com.cehome.apimanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmProjectDao;
import com.cehome.apimanager.model.dto.AmProjectQueryReqDto;
import com.cehome.apimanager.model.dto.AmProjectReqDto;
import com.cehome.apimanager.model.dto.AmProjectResDto;
import com.cehome.apimanager.model.po.AmProject;
import com.cehome.apimanager.service.IAmProjectService;

/**
 * 项目业务接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmProjectServiceImpl implements IAmProjectService {

	@Autowired
	private AmProjectDao projectDao;

	@Override
	public void add(AmProjectReqDto dto) {
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		projectDao.add(dto);
	}

	@Override
	public void update(AmProjectReqDto dto) {
		dto.setUpdateTime(new Date());
		projectDao.update(dto);
	}

	@Override
	public AmProjectResDto findById(AmProjectQueryReqDto dto) {
		AmProject amProject = projectDao.get(dto.getId());
		if(amProject == null){
			return null;
		}
		AmProjectResDto amProjectResDto = new AmProjectResDto();
		BeanUtils.copyProperties(amProject, amProjectResDto);
		return amProjectResDto;
	}

	@Override
	public void delete(AmProjectReqDto dto) {
		projectDao.delete(dto.getId());
	}

	@Override
	public Page<AmProject> findPage(AmProjectQueryReqDto dto) {
		return projectDao.find(dto);
	}
	
	@Override
	public List<AmProject> list(AmProjectQueryReqDto dto) {
		return projectDao.list(dto);
	}
}
