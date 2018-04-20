package com.cehome.apimanager.service;

import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.po.AmDomain;

import java.util.List;

public interface IAmDomainService {
    List<AmDomain> list(AmDomainQueryReqDto dto);
    AmDomain findById(Integer id);
}
