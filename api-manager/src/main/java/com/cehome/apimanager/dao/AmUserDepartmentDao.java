package com.cehome.apimanager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.model.po.AmUserDepartment;

@Repository
public class AmUserDepartmentDao extends BaseDao<AmUserDepartment> {

	public void batchAdd(List<AmUserDepartment> userDepartmentList) {
		sqlSessionTemplate.insert(getClassName() + ".batchAdd", userDepartmentList);
	}

	public void deleteByUserId(Integer userId) {
		sqlSessionTemplate.delete(getClassName() + ".deleteByUserId", userId);
	}
	
	public void deleteByDepId(Integer depId) {
		sqlSessionTemplate.delete(getClassName() + ".deleteByDepId", depId);
	}
	
}
