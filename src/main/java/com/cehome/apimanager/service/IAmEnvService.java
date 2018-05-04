package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.dto.AmEnvReqDto;
import com.cehome.apimanager.model.po.AmEnv;

import java.util.List;

public interface IAmEnvService {
    List<AmEnv> list(AmEnvQueryReqDto dto);

    void add(AmEnvReqDto dto);

    void update(AmEnvReqDto dto);

    Page<AmEnv> findPage(AmEnvQueryReqDto dto);

    AmEnv findById(Integer id);

    void delete(AmEnvReqDto dto);
}
