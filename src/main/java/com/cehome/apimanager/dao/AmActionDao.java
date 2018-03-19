package com.cehome.apimanager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.po.AmAction;

@Repository
public class AmActionDao extends BaseDao<AmAction>{

	public List<AmAction> findUrlList() {
		return sqlSessionTemplate.selectList(getClassName() + ".findUrlList");
	}
}
