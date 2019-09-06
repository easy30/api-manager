package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.dao.DbConfigDao;
import com.cehome.apimanager.model.dto.DbConfigQueryReqDto;
import com.cehome.apimanager.model.po.DbConfig;
import com.cehome.apimanager.service.IDbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbConfigServiceImpl implements IDbConfigService {
    @Autowired
    private DbConfigDao dbConfigDao;

    @Override
    public List<DbConfig> list(DbConfigQueryReqDto dto) {
        return dbConfigDao.list(dto);
    }

    @Override
    public DbConfig findByDbName(String dbName) {
        return dbConfigDao.findByDbName(dbName);
    }
}
