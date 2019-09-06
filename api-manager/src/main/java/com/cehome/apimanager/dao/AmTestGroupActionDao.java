package com.cehome.apimanager.dao;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.dto.AmTestGroupActionReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupActionResDto;
import com.cehome.apimanager.model.po.AmTestGroupAction;
import org.springframework.stereotype.Repository;

@Repository
public class AmTestGroupActionDao extends BaseDao<AmTestGroupAction> {
    public AmTestGroupActionResDto findByActionIdAndGroupId(AmTestGroupActionReqDto dto) {
        return sqlSessionTemplate.selectOne(AmTestGroupAction.class.getName() + ".findByActionIdAndGroupId", dto);
    }

    public void deleteByGroupId(Integer groupId) {
        sqlSessionTemplate.delete(AmTestGroupAction.class.getName() + ".deleteByGroupId", groupId);
    }
}
