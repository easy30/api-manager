package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.dao.AmEnvDao;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
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
}
