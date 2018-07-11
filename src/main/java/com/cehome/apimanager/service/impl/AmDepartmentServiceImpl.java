package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmDepartmentDao;
import com.cehome.apimanager.dao.AmUserDepartmentDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.*;
import com.cehome.apimanager.model.po.AmDepartment;
import com.cehome.apimanager.model.po.AmProject;
import com.cehome.apimanager.service.IAmDepartmentService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.service.IAmProjectService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private IAmProjectService projectService;

	@Autowired
	private AmUserDepartmentDao userDepartmentDao;

	@Autowired
	private IAmOperateLogService operateLogService;

	@Autowired
	private CacheProvider cacheProvider;

	public Integer add(AmDepartmentReqDto dto) {
		AmDepartment entity = new AmDepartment();
		BeanUtils.copyProperties(dto, entity);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setCreateUser(dto.getOperateUser());
		departmentDao.add(entity);

		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.DEPARTMENT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
				operateLogReqDto.setOperateDesc("增加部门【" + dto.getDepName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setEntityId(entity.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
		return entity.getId();
	}

	public void update(AmDepartmentReqDto dto) {
		AmDepartment department = departmentDao.get(dto.getId());
		AmDepartment entity = new AmDepartment();
		BeanUtils.copyProperties(dto, entity);
		entity.setUpdateTime(new Date());
		entity.setUpdateUser(dto.getOperateUser());
		departmentDao.update(entity);

		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.DEPARTMENT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
				operateLogReqDto.setOperateDesc("修改部门【" + dto.getDepName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				if(!department.equals(dto)){
					operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(department, dto));
				}
				operateLogReqDto.setEntityId(entity.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
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
		AmProjectQueryReqDto projectQueryDto = new AmProjectQueryReqDto();
		projectQueryDto.setDepId(dto.getId());
		List<AmProject> projectList = projectService.list(projectQueryDto);
		if(projectList != null && !projectList.isEmpty()){
			throw new BizValidationException("部门下存在其他项目，不能删除！");
		}
		userDepartmentDao.deleteByDepId(dto.getId());
		AmDepartment department = departmentDao.get(dto.getId());
		departmentDao.delete(dto.getId());

		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.DEPARTMENT.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
				operateLogReqDto.setOperateDesc("删除部门【" + department.getDepName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setEntityId(dto.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
	}

	@Override
	public Page<AmDepartment> findPage(AmDepartmentQueryReqDto dto) {
		Page<AmDepartment> departmentPage = departmentDao.find(dto);
		List<AmDepartment> datas = departmentPage.getDatas();
		if(CollectionUtils.isEmpty(datas)){
			return departmentPage;
		}
		List<AmDepartment> result = new ArrayList<>();
		Map<String, String> userDicMap = cacheProvider.getUserDicMap();
		for(AmDepartment department : datas){
			AmDepartmentResDto departmentResDto = new AmDepartmentResDto();
			BeanUtils.copyProperties(department, departmentResDto);
			if(department.getCreateUser() != null){
				departmentResDto.setCreateUserName(userDicMap.get(department.getCreateUser() + ""));
			}
			if(department.getUpdateUser() != null){
				departmentResDto.setUpdateUserName(userDicMap.get(department.getUpdateUser() + ""));
			}
			result.add(departmentResDto);
		}
		departmentPage.setDatas(result);
		return departmentPage;
	}
	
	@Override
	public List<AmDepartment> list(AmDepartmentQueryReqDto dto) {
		return departmentDao.list(dto);
	}
}
