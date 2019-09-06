package com.cehome.apimanager.dao;

import org.springframework.stereotype.Repository;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.dto.AmUserReqDto;
import com.cehome.apimanager.model.po.AmUser;

@Repository
public class AmUserDao extends BaseDao<AmUser> {

	public AmUser findByAccount(String account) {
		return sqlSessionTemplate.selectOne(getClassName() + ".findByAccount", account);
	}

	public AmUser findByAccountAndPass(AmUserReqDto dto) {
		return sqlSessionTemplate.selectOne(getClassName() + ".findByAccountAndPass", dto);
	}

	public void changePassword(AmUserReqDto dto) {
		sqlSessionTemplate.update(getClassName() + ".changePassword", dto);
	}
}
