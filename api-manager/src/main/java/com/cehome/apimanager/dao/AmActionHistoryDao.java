package com.cehome.apimanager.dao;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.po.AmActionHistory;
import org.springframework.stereotype.Repository;

@Repository
public class AmActionHistoryDao extends BaseDao<AmActionHistory> {
    public AmActionHistory findLastModifyRecordByActionId(Integer actionId) {
        return sqlSessionTemplate.selectOne(AmActionHistory.class.getName() + ".findLastModifyRecordByActionId", actionId);
    }
}
