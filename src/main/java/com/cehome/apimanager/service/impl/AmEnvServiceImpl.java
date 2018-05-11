package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmEnvDao;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.dto.AmEnvReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.service.IAmEnvService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmEnvServiceImpl implements IAmEnvService{
    @Autowired
    private AmEnvDao envDao;

    @Autowired
    private IAmOperateLogService operateLogService;

    @Override
    public List<AmEnv> list(AmEnvQueryReqDto dto) {
        return envDao.list(dto);
    }

    @Override
    public void add(AmEnvReqDto dto) {
        envDao.add(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加环境【" + dto.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void update(AmEnvReqDto dto) {
        AmEnv env = envDao.get(dto.getId());
        envDao.update(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改环境【" + dto.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                if(!env.equals(dto)){
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(env, dto));
                }
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public Page<AmEnv> findPage(AmEnvQueryReqDto dto) {
        return envDao.find(dto);
    }

    @Override
    public AmEnv findById(Integer id) {
        return envDao.get(id);
    }

    @Override
    public void delete(AmEnvReqDto dto) {
        envDao.delete(dto.getId());
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ENV.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除环境【" + dto.getEnvName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogService.add(operateLogReqDto);
            }
        });
    }
}
