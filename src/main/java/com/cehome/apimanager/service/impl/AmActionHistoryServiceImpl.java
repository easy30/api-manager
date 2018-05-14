package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionHistoryDao;
import com.cehome.apimanager.model.dto.AmActionHistoryQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionHistoryReqDto;
import com.cehome.apimanager.model.po.AmActionHistory;
import com.cehome.apimanager.service.IAmActionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmActionHistoryServiceImpl implements IAmActionHistoryService {
    @Autowired
    private AmActionHistoryDao actionHistoryDao;

    @Override
    public void add(AmActionHistoryReqDto dto) {
        actionHistoryDao.add(dto);
    }

    @Override
    public void update(AmActionHistoryReqDto dto) {
        actionHistoryDao.update(dto);
    }

    @Override
    public AmActionHistory findById(Integer id) {
        return actionHistoryDao.get(id);
    }

    @Override
    public void delete(AmActionHistoryReqDto dto) {
        actionHistoryDao.delete(dto.getId());
    }

    @Override
    public Page<AmActionHistory> findPage(AmActionHistoryQueryReqDto dto) {
        return actionHistoryDao.find(dto);
    }

    @Override
    public List<AmActionHistory> list(AmActionHistoryQueryReqDto dto) {
        return actionHistoryDao.list(dto);
    }
}
