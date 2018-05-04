package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionLoginQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionLoginReqDto;
import com.cehome.apimanager.model.po.AmActionLogin;

import java.util.List;

public interface IAmActionLoginService {
    List<AmActionLogin> list(AmActionLoginQueryReqDto dto);
    void setCookieStore();

    void delete(AmActionLoginReqDto dto);

    AmActionLogin findById(Integer id);

    Page<AmActionLogin> findPage(AmActionLoginQueryReqDto dto);

    void update(AmActionLoginReqDto dto);

    void add(AmActionLoginReqDto dto);
}
