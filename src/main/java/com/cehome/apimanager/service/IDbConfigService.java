package com.cehome.apimanager.service;

import com.cehome.apimanager.model.dto.DbConfigQueryReqDto;
import com.cehome.apimanager.model.po.DbConfig;

import java.util.List;

public interface IDbConfigService {
    List<DbConfig> list(DbConfigQueryReqDto dto);

    DbConfig findByDbName(String dbName);
}
