package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmObjectFieldDescDao;
import com.cehome.apimanager.model.dto.AmObjectFieldDescQueryReqDto;
import com.cehome.apimanager.model.dto.AmObjectFieldDescReqDto;
import com.cehome.apimanager.model.dto.AmObjectFieldDescResDto;
import com.cehome.apimanager.model.po.AmObjectFieldDesc;
import com.cehome.apimanager.service.IAmObjectFieldDescService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AmObjectFieldDescServiceImpl implements IAmObjectFieldDescService{
    @Autowired
    private AmObjectFieldDescDao objectFieldDescDao;

    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public List<AmObjectFieldDesc> list(AmObjectFieldDescQueryReqDto dto) {
        return objectFieldDescDao.list(dto);
    }

    @Override
    public void add(AmObjectFieldDescReqDto dto) {
        dto.setCreateTime(new Date());
        objectFieldDescDao.add(dto);
    }

    @Override
    public void update(AmObjectFieldDescReqDto dto) {
        dto.setUpdateTime(new Date());
        objectFieldDescDao.update(dto);
    }

    @Override
    public Page<AmObjectFieldDesc> findPage(AmObjectFieldDescQueryReqDto dto) {
        Page<AmObjectFieldDesc> objectFieldDescPage = objectFieldDescDao.find(dto);
        List<AmObjectFieldDesc> datas = objectFieldDescPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return objectFieldDescPage;
        }
        List<AmObjectFieldDesc> result = new ArrayList<>();
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();

        for(AmObjectFieldDesc objectFieldDesc : datas){
            AmObjectFieldDescResDto objectFieldDescResDto = new AmObjectFieldDescResDto();
            BeanUtils.copyProperties(objectFieldDesc, objectFieldDescResDto);
            if(objectFieldDesc.getCreateUser() != null){
                objectFieldDescResDto.setCreateUserName(userDicMap.get(objectFieldDesc.getCreateUser() + ""));
            }
            if(objectFieldDesc.getUpdateUser() != null){
                objectFieldDescResDto.setUpdateUserName(userDicMap.get(objectFieldDesc.getUpdateUser() + ""));
            }
            result.add(objectFieldDescResDto);
        }
        objectFieldDescPage.setDatas(result);
        return objectFieldDescPage;
    }

    @Override
    public AmObjectFieldDesc findById(Integer id) {
        return objectFieldDescDao.get(id);
    }

    @Override
    public void delete(AmObjectFieldDescReqDto dto) {
        objectFieldDescDao.delete(dto.getId());
    }

    @Override
    public AmObjectFieldDesc findByClassWholeName(String className) {
        return objectFieldDescDao.findByClassWholeName(className);
    }

    @Override
    public List<String> listObjectNames(AmObjectFieldDescQueryReqDto dto) {
        List<String> objectNames = new ArrayList<>();
        List<AmObjectFieldDesc> list = objectFieldDescDao.list(dto);
        if(CollectionUtils.isEmpty(list)){
            return objectNames;
        }
        for(AmObjectFieldDesc objectFieldDesc : list){
            objectNames.add(objectFieldDesc.getClassWholeName());
        }
        return objectNames;
    }
}

