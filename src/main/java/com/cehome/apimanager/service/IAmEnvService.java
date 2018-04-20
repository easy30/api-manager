package com.cehome.apimanager.service;

import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.po.AmEnv;

import java.util.List;

public interface IAmEnvService {
    List<AmEnv> list(AmEnvQueryReqDto dto);
}
