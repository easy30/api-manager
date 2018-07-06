package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmTestGroupDao;
import com.cehome.apimanager.model.dto.AmTestGroupQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupResDto;
import com.cehome.apimanager.model.po.AmTestGroup;
import com.cehome.apimanager.service.IAmTestGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmTestGroupServiceImpl implements IAmTestGroupService {
    @Autowired
    private AmTestGroupDao testGroupDao;

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
        testGroupDao.delete(dto.getId());
    }

    @Override
    public Page<AmTestGroup> findPage(AmTestGroupQueryReqDto dto) {
        return testGroupDao.find(dto);
    }

    @Override
    public List<AmTestGroup> list(AmTestGroupQueryReqDto dto) {
        return testGroupDao.list(dto);
    }
}
