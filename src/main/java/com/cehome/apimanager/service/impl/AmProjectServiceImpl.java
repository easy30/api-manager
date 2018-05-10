package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmProjectDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.*;
import com.cehome.apimanager.model.po.AmModule;
import com.cehome.apimanager.model.po.AmProject;
import com.cehome.apimanager.service.IAmModuleService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.service.IAmProjectService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

	@Autowired
	private IAmModuleService moduleService;

	@Autowired
	private IAmOperateLogService operateLogService;

	@Override
	public void add(AmProjectReqDto dto) {
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		projectDao.add(dto);
		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void invoke() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.PROJECT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
				operateLogReqDto.setOperateDesc("增加项目【" + dto.getProjectName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setOperateTime(new Date());
				operateLogService.add(operateLogReqDto);
			}
		});
	}

	@Override
	public void update(AmProjectReqDto dto) {
		AmProject project = projectDao.get(dto.getId());
		dto.setUpdateTime(new Date());
		projectDao.update(dto);
		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void invoke() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.PROJECT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
				operateLogReqDto.setOperateDesc("修改项目【" + dto.getProjectName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setOperateTime(new Date());
				if(!project.equals(dto)){
					operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(project, dto));
				}
				operateLogService.add(operateLogReqDto);
			}
		});
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
		AmModuleQueryReqDto moduleQueryReqDto = new AmModuleQueryReqDto();
		moduleQueryReqDto.setProjectId(dto.getId());
		List<AmModule> moduleList = moduleService.list(moduleQueryReqDto);
		if(moduleList != null && !moduleList.isEmpty()){
			throw new BizValidationException("项目下存在其他模块，不能删除！");
		}
		projectDao.delete(dto.getId());

		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void invoke() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.PROJECT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
				operateLogReqDto.setOperateDesc("删除项目【" + dto.getProjectName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setOperateTime(new Date());
				operateLogService.add(operateLogReqDto);
			}
		});
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
