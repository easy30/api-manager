package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmTestGroupActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupActionReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.po.AmTestGroupAction;

public interface IAmTestGroupActionService {
    void delete(AmTestGroupActionReqDto dto);

    Page<AmTestGroupAction> findPage(AmTestGroupActionQueryReqDto dto);

    void add(AmTestGroupActionReqDto dto);

    void update(AmTestGroupActionReqDto dto);

    void deleteByGroupId(AmTestGroupReqDto dto);
}
