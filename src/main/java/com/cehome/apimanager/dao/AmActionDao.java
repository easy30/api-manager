package com.cehome.apimanager.dao;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.po.AmAction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AmActionDao extends BaseDao<AmAction>{

	public List<AmAction> findUrlList() {
		return sqlSessionTemplate.selectList(getClassName() + ".findUrlList");
	}

	public List<Map<String, Integer>> countGroupByProject(AmActionQueryReqDto dto) {
		return sqlSessionTemplate.selectList(AmAction.class.getName() + ".countGroupByProject");
	}
}
