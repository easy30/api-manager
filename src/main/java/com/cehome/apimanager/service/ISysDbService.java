package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.SysDbQueryReqDto;
import com.cehome.apimanager.model.dto.SysDbReqDto;
import com.cehome.apimanager.model.dto.SysDbResDto;

public interface ISysDbService {
    Page<SysDbResDto> findTables(SysDbQueryReqDto sysDbQueryReqDto);

    void makeObjectDesc(SysDbReqDto sysDbReqDto);
}
