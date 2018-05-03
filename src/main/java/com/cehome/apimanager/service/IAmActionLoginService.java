package com.cehome.apimanager.service;

import com.cehome.apimanager.model.dto.AmActionLoginQueryReqDto;
import com.cehome.apimanager.model.po.AmActionLogin;

import java.util.List;

public interface IAmActionLoginService {
    List<AmActionLogin> list(AmActionLoginQueryReqDto dto);
    void setCookieStore();
}
