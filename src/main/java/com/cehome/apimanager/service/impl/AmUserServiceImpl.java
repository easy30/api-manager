package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmUserDao;
import com.cehome.apimanager.dao.AmUserDepartmentDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.AmUserQueryReqDto;
import com.cehome.apimanager.model.dto.AmUserReqDto;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.model.po.AmUserDepartment;
import com.cehome.apimanager.service.IAmUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户模块接口实现
 * 
 * @author sunlei
 *
 */
@Service
public class AmUserServiceImpl implements IAmUserService {
	@Autowired
	private AmUserDao userDao;

	@Autowired
	private AmUserDepartmentDao userDepartmentDao;
	
	@Override
	public void add(AmUserReqDto dto) {
		AmUser user = userDao.findByAccount(dto.getAccount());
		if(user != null){
			throw new BizValidationException("账号" + dto.getAccount() + "已存在！");
		}
		String password = dto.getPassword();
		String md5Hex = DigestUtils.md5Hex(password.getBytes());
		dto.setPassword(md5Hex);
		dto.setCreateTime(new Date());
		userDao.add(dto);
		
		String depIds = dto.getDepIds();
		if(!StringUtils.isEmpty(depIds)){
			String[] depIdArray = depIds.split(",");
			List<AmUserDepartment> userDepartmentList = new ArrayList<>();
			for(String depIdS : depIdArray){
				AmUserDepartment userDepartment = new AmUserDepartment();
				userDepartment.setUserId(dto.getId());
				userDepartment.setDepId(Integer.valueOf(depIdS));
				userDepartmentList.add(userDepartment);
			}
			userDepartmentDao.batchAdd(userDepartmentList);
		}
	}

	@Override
	public void update(AmUserReqDto dto) {
//		AmUser user = userDao.findByAccount(dto.getAccount());
//		if(user != null){
//			throw new BizValidationException("账号" + dto.getAccount() + "已存在！");
//		}
		dto.setUpdateTime(new Date());
		userDao.update(dto);
		
		String depIds = dto.getDepIds();
		if(!StringUtils.isEmpty(depIds)){
			String[] depIdArray = depIds.split(",");
			List<AmUserDepartment> userDepartmentList = new ArrayList<>();
			for(String depIdS : depIdArray){
				AmUserDepartment userDepartment = new AmUserDepartment();
				userDepartment.setUserId(dto.getId());
				userDepartment.setDepId(Integer.valueOf(depIdS));
				userDepartmentList.add(userDepartment);
			}
			userDepartmentDao.deleteByUserId(dto.getId());
			userDepartmentDao.batchAdd(userDepartmentList);
		}
	}

	@Override
	public void delete(AmUserReqDto dto) {
		userDepartmentDao.deleteByUserId(dto.getId());
		userDao.delete(dto.getId());
	}

	@Override
	public Page<AmUser> findPage(AmUserQueryReqDto dto) {
		return userDao.find(dto);
	}

	@Override
	public AmUser findById(Integer id) {
		return userDao.get(id);
	}

	@Override
	public AmUser login(AmUserReqDto dto) {
		String password = dto.getPassword();
		AmUser user = userDao.findByAccount(dto.getAccount());
		if(user == null){
			throw new BizValidationException("账号" + dto.getAccount() + "不存在！");
		}
		String md5Hex = DigestUtils.md5Hex(password.getBytes());
		dto.setPassword(md5Hex);
		user = userDao.findByAccountAndPass(dto);
		if(user == null){
			throw new BizValidationException("密码错误！");
		}
		return user;
	}
	
	@Override
	public List<AmUser> list(AmUserQueryReqDto dto) {
		return userDao.list(dto);
	}
}
