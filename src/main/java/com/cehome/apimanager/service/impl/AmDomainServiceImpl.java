package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.dao.AmDomainDao;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmDomainServiceImpl implements IAmDomainService{
    @Autowired
    private AmDomainDao domainDao;

    @Override
    public List<AmDomain> list(AmDomainQueryReqDto dto) {
        return domainDao.list(dto);
    }
}
