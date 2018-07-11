package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmModuleDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.*;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.model.po.AmModule;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmModuleService;
import com.cehome.apimanager.service.IAmOperateLogService;
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

	@Autowired
	private IAmOperateLogService operateLogService;

	@Autowired
	private CacheProvider cacheProvider;

	@Override
	public void add(AmModuleReqDto dto) {
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		dto.setCreateUser(dto.getOperateUser());
		moduleDao.add(dto);
		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.MODULE.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
				operateLogReqDto.setOperateDesc("增加模块【" + dto.getModuleName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setEntityId(dto.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
	}

	@Override
	public void update(AmModuleReqDto dto) {
		AmModule module = moduleDao.get(dto.getId());
		dto.setUpdateTime(new Date());
		dto.setUpdateUser(dto.getOperateUser());
		moduleDao.update(dto);
		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.MODULE.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
				operateLogReqDto.setOperateDesc("修改项目【" + dto.getModuleName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				if(!module.equals(dto)){
					operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(module, dto));
				}
				operateLogReqDto.setEntityId(dto.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
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
		AmModule module = moduleDao.get(dto.getId());
		moduleDao.delete(dto.getId());

		ThreadUtils.execute(new ThreadUtils.Task() {
			@Override
			public void doTask() {
				AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
				operateLogReqDto.setModuleCode(CommonMeta.Module.MODULE.getCode());
				operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
				operateLogReqDto.setOperateDesc("删除模块【" + module.getModuleName() + "】");
				operateLogReqDto.setOperateUser(dto.getOperateUser());
				operateLogReqDto.setEntityId(dto.getId());
				operateLogService.add(operateLogReqDto);
			}
		});
	}

	@Override
	public Page<AmModule> findPage(AmModuleQueryReqDto dto) {
		Page<AmModule> modulePage = moduleDao.find(dto);
		List<AmModule> datas = modulePage.getDatas();
		if(CollectionUtils.isEmpty(datas)){
			return modulePage;
		}
		List<AmModule> result = new ArrayList<>();
		Map<String, String> userDicMap = cacheProvider.getUserDicMap();
		for(AmModule module : datas){
			AmModuleResDto moduleResDto = new AmModuleResDto();
			BeanUtils.copyProperties(module, moduleResDto);
			if(module.getCreateUser() != null){
				moduleResDto.setCreateUserName(userDicMap.get(module.getCreateUser() + ""));
			}
			if(module.getUpdateUser() != null){
				moduleResDto.setUpdateUserName(userDicMap.get(module.getUpdateUser() + ""));
			}
			result.add(moduleResDto);
		}

		modulePage.setDatas(result);
		return modulePage;
	}

	@Override
	public List<AmModule> list(AmModuleQueryReqDto dto) {
		return moduleDao.list(dto);
	}

}
