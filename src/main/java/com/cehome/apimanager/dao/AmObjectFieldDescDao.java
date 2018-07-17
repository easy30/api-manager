package com.cehome.apimanager.dao;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.po.AmObjectFieldDesc;
import org.springframework.stereotype.Repository;

@Repository
public class AmObjectFieldDescDao extends BaseDao<AmObjectFieldDesc>{
    public AmObjectFieldDesc findByClassWholeName(String className) {
        return sqlSessionTemplate.selectOne(AmObjectFieldDesc.class.getName() + ".findByClassWholeName", className);
    }
}
