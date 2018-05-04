package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmEnvDao;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.dto.AmEnvReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.service.IAmEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmEnvServiceImpl implements IAmEnvService{
    @Autowired
    private AmEnvDao envDao;

    @Override
    public List<AmEnv> list(AmEnvQueryReqDto dto) {
        return envDao.list(dto);
    }

    @Override
    public void add(AmEnvReqDto dto) {
        envDao.add(dto);
    }

    @Override
    public void update(AmEnvReqDto dto) {
        envDao.update(dto);
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
    }
}
