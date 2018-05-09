package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmOperateLogDao;
import com.cehome.apimanager.model.dto.AmOperateLogQueryReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmOperateLog;
import com.cehome.apimanager.service.IAmOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmOperateLogServiceImpl implements IAmOperateLogService {
    @Autowired
    private AmOperateLogDao operateLogDao;

    @Override
    public void add(AmOperateLogReqDto dto) {
        operateLogDao.add(dto);
    }

    @Override
    public void update(AmOperateLogReqDto dto) {
        operateLogDao.update(dto);
    }

    @Override
    public List<AmOperateLog> list(AmOperateLogQueryReqDto dto) {
        return operateLogDao.list(dto);
    }

    @Override
    public Page<AmOperateLog> findPage(AmOperateLogQueryReqDto dto) {
        return operateLogDao.find(dto);
    }

    @Override
    public AmOperateLog findById(Integer id){
        return operateLogDao.get(id);
    }
}
