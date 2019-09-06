package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmDomainDao;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.dto.AmDomainReqDto;
import com.cehome.apimanager.model.dto.AmDomainResDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmDomainService;
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
public class AmDomainServiceImpl implements IAmDomainService {
    @Autowired
    private AmDomainDao domainDao;

    @Autowired
    private IAmOperateLogService operateLogService;

    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public List<AmDomain> list(AmDomainQueryReqDto dto) {
        return domainDao.list(dto);
    }

    @Override
    public AmDomain findById(Integer id) {
        return domainDao.get(id);
    }

    @Override
    public Page<AmDomain> findPage(AmDomainQueryReqDto dto) {
        Page<AmDomain> domainPage = domainDao.find(dto);
        List<AmDomain> datas = domainPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return domainPage;
        }
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        List<AmDomain> result = new ArrayList<>();
        for(AmDomain domain : datas){
            AmDomainResDto domainResDto = new AmDomainResDto();
            BeanUtils.copyProperties(domain, domainResDto);
            if(domain.getCreateUser() != null){
                domainResDto.setCreateUserName(userDicMap.get(domain.getCreateUser() + ""));
            }
            if(domain.getUpdateUser() != null){
                domainResDto.setUpdateUserName(userDicMap.get(domain.getUpdateUser() + ""));
            }
            result.add(domainResDto);
        }
        domainPage.setDatas(result);
        return domainPage;
    }

    @Override
    public void add(AmDomainReqDto dto) {
        dto.setCreateUser(dto.getOperateUser());
        domainDao.add(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.DOMAIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加服务【" + dto.getDomainName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void update(AmDomainReqDto dto) {
        AmDomain domain = domainDao.get(dto.getId());
        dto.setUpdateUser(dto.getOperateUser());
        domainDao.update(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.DOMAIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改服务地址【" + dto.getDomainName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                if(!domain.equals(dto)){
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(domain, dto));
                }
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void delete(AmDomainReqDto dto) {
        AmDomain domain = domainDao.get(dto.getId());
        domainDao.delete(dto.getId());
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.DOMAIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除服务【" + domain.getDomainName() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }
}
