package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmTestGroupActionDao;
import com.cehome.apimanager.model.dto.AmTestGroupActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupActionReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupActionResDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.po.AmTestGroupAction;
import com.cehome.apimanager.service.IAmTestGroupActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AmTestGroupActionServiceImpl implements IAmTestGroupActionService {
    @Autowired
    private AmTestGroupActionDao testGroupActionDao;

    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public void delete(AmTestGroupActionReqDto dto) {
        testGroupActionDao.delete(dto.getId());
    }

    @Override
    public Page<AmTestGroupAction> findPage(AmTestGroupActionQueryReqDto dto) {
        Page<AmTestGroupAction> testGroupActionPage = testGroupActionDao.find(dto);
        List<AmTestGroupAction> datas = testGroupActionPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return testGroupActionPage;
        }
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        List<AmTestGroupAction> result = new ArrayList<>();
        for(AmTestGroupAction testGroupAction : datas){
            AmTestGroupActionResDto testGroupActionResDto = new AmTestGroupActionResDto();
            BeanUtils.copyProperties(testGroupAction, testGroupActionResDto);
            if(testGroupAction.getCreateUser() != null){
                testGroupActionResDto.setCreateUserName(userDicMap.get(testGroupAction.getCreateUser() + ""));
            }
            if(testGroupAction.getUpdateUser() != null){
                testGroupActionResDto.setUpdateUserName(userDicMap.get(testGroupAction.getUpdateUser() + ""));
            }
            result.add(testGroupActionResDto);
        }
        testGroupActionPage.setDatas(result);
        return testGroupActionPage;
    }

    @Override
    public void add(AmTestGroupActionReqDto dto) {
        AmTestGroupActionResDto testGroupActionResDto = testGroupActionDao.findByActionIdAndGroupId(dto);
        if(testGroupActionResDto != null){
            dto.setUpdateUser(dto.getUpdateUser());
            dto.setUpdateTime(new Date());
            dto.setCreateUser(null);
            testGroupActionDao.update(dto);
        } else {
            dto.setCreateTime(new Date());
            testGroupActionDao.add(dto);
        }
    }

    @Override
    public void update(AmTestGroupActionReqDto dto) {
        dto.setUpdateTime(new Date());
        testGroupActionDao.update(dto);
    }

    @Override
    public void deleteByGroupId(AmTestGroupReqDto dto) {
        testGroupActionDao.deleteByGroupId(dto.getId());
    }
}

