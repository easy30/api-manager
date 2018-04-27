package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmDomainDao;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.dto.AmDomainReqDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmDomainServiceImpl implements IAmDomainService {
    @Autowired
    private AmDomainDao domainDao;

    @Override
    public List<AmDomain> list(AmDomainQueryReqDto dto) {
        return domainDao.list(dto);
    }

    @Override
    public AmDomain findById(Integer id) {
        return domainDao.get(id);
    }

    @Override
    public Page<AmDomain> findPage(AmDomainQueryReqDto dto) {
        return domainDao.find(dto);
    }

    @Override
    public void add(AmDomainReqDto dto) {
        domainDao.add(dto);
    }

    @Override
    public void update(AmDomainReqDto dto) {
        domainDao.update(dto);
    }

    @Override
    public void delete(AmDomainReqDto dto) {
        domainDao.delete(dto.getId());
    }
}
