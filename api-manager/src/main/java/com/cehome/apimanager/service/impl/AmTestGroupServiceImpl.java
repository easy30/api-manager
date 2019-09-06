package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmTestGroupDao;
import com.cehome.apimanager.model.dto.AmTestGroupQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupResDto;
import com.cehome.apimanager.model.po.AmTestGroup;
import com.cehome.apimanager.service.IAmTestGroupActionService;
import com.cehome.apimanager.service.IAmTestGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AmTestGroupServiceImpl implements IAmTestGroupService {
    @Autowired
    private AmTestGroupDao testGroupDao;

    @Autowired
    private IAmTestGroupActionService testGroupActionService;

    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public void add(AmTestGroupReqDto dto) {
        testGroupDao.add(dto);
    }

    @Override
    public void update(AmTestGroupReqDto dto) {
        testGroupDao.update(dto);
    }

    @Override
    public AmTestGroupResDto findById(AmTestGroupQueryReqDto dto) {
        AmTestGroup amTestGroup = testGroupDao.get(dto.getId());
        if(amTestGroup == null){
            return null;
        }
        AmTestGroupResDto testGroupResDto = new AmTestGroupResDto();
        BeanUtils.copyProperties(amTestGroup, testGroupResDto);
        return testGroupResDto;
    }

    @Override
    public void delete(AmTestGroupReqDto dto) {
        testGroupActionService.deleteByGroupId(dto);
        testGroupDao.delete(dto.getId());
    }

    @Override
    public Page<AmTestGroup> findPage(AmTestGroupQueryReqDto dto) {
        Page<AmTestGroup> testGroupPage = testGroupDao.find(dto);
        List<AmTestGroup> datas = testGroupPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return testGroupPage;
        }
        List<AmTestGroup> result = new ArrayList<>();
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        for(AmTestGroup testGroup : datas){
            AmTestGroupResDto testGroupResDto = new AmTestGroupResDto();
            BeanUtils.copyProperties(testGroup, testGroupResDto);
            if(testGroup.getCreateUser() != null){
                testGroupResDto.setCreateUserName(userDicMap.get(testGroup.getCreateUser() + ""));
            }
            if(testGroup.getUpdateUser() != null){
                testGroupResDto.setUpdateUserName(userDicMap.get(testGroup.getUpdateUser() + ""));
            }
            result.add(testGroupResDto);
        }
        testGroupPage.setDatas(result);
        return testGroupPage;
    }

    @Override
    public List<AmTestGroup> list(AmTestGroupQueryReqDto dto) {
        return testGroupDao.list(dto);
    }
}
