package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmOperateLogQueryReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmOperateLog;

import java.util.List;

public interface IAmOperateLogService {
    void add(AmOperateLogReqDto dto);

    void update(AmOperateLogReqDto dto);

    List<AmOperateLog> list(AmOperateLogQueryReqDto dto);

    Page<AmOperateLog> findPage(AmOperateLogQueryReqDto dto);

    AmOperateLog findById(Integer id);
}
