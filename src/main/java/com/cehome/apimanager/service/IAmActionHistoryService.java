package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionHistoryQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionHistoryReqDto;
import com.cehome.apimanager.model.po.AmActionHistory;

import java.util.List;

public interface IAmActionHistoryService {
    void add(AmActionHistoryReqDto dto);

    void update(AmActionHistoryReqDto dto);

    AmActionHistory findById(Integer id);

    void delete(AmActionHistoryReqDto dto);

    Page<AmActionHistory> findPage(AmActionHistoryQueryReqDto dto);

    List<AmActionHistory> list(AmActionHistoryQueryReqDto dto);
}
