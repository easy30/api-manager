package com.cehome.apimanager.dao;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.po.DbConfig;
import org.springframework.stereotype.Repository;

@Repository
public class DbConfigDao extends BaseDao<DbConfig> {
    public DbConfig findByDbName(String dbName) {
        return sqlSessionTemplate.selectOne(DbConfig.class.getName() + ".findByDbName", dbName);
    }
}
