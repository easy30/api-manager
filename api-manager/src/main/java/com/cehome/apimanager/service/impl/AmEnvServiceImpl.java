package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmEnvDao;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.dto.AmEnvReqDto;
import com.cehome.apimanager.model.dto.AmEnvResDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.service.IAmEnvService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AmEnvServiceImpl implements IAmEnvService{
    @Autowired
    private AmEnvDao envDao;

    @Autowired
    private IAmOperateLogService operateLogService;

    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public List<AmEnv> list(AmEnvQueryReqDto dto) {
        return envDao.list(dto);
    }

    @Override
    public void add(AmEnvReqDto dto) {
        dto.setCreateUser(dto.getOperateUser());
        envDao.add(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加环境【" + dto.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void update(AmEnvReqDto dto) {
        AmEnv env = envDao.get(dto.getId());
        dto.setUpdateUser(dto.getOperateUser());
        envDao.update(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改环境【" + dto.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                if(!env.equals(dto)){
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(env, dto));
                }
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public Page<AmEnv> findPage(AmEnvQueryReqDto dto) {
        Page<AmEnv> envPage = envDao.find(dto);
        List<AmEnv> datas = envPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return envPage;
        }
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        List<AmEnv> result = new ArrayList<>();
        for(AmEnv env : datas){
            AmEnvResDto envResDto = new AmEnvResDto();
            BeanUtils.copyProperties(env, envResDto);
            if(env.getCreateUser() != null){
                envResDto.setCreateUserName(userDicMap.get(env.getCreateUser() + ""));
            }
            if(env.getUpdateUser() != null){
                envResDto.setUpdateUserName(userDicMap.get(env.getUpdateUser() + ""));
            }
            result.add(envResDto);
        }
        envPage.setDatas(result);
        return envPage;
    }

    @Override
    public AmEnv findById(Integer id) {
        return envDao.get(id);
    }

    @Override
    public void delete(AmEnvReqDto dto) {
        AmEnv entity = envDao.get(dto.getId());
        envDao.delete(dto.getId());
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除环境【" + entity.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }
}
