package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmTestGroupQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupResDto;
import com.cehome.apimanager.model.po.AmTestGroup;

import java.util.List;

public interface IAmTestGroupService {
    void add(AmTestGroupReqDto dto);

    void update(AmTestGroupReqDto dto);

    AmTestGroupResDto findById(AmTestGroupQueryReqDto dto);

    void delete(AmTestGroupReqDto dto);

    Page<AmTestGroup> findPage(AmTestGroupQueryReqDto dto);

    List<AmTestGroup> list(AmTestGroupQueryReqDto dto);
}
