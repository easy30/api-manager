package com.cehome.apimanager.service;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmObjectFieldDescQueryReqDto;
import com.cehome.apimanager.model.dto.AmObjectFieldDescReqDto;
import com.cehome.apimanager.model.po.AmObjectFieldDesc;

import java.util.List;

public interface IAmObjectFieldDescService {
    List<AmObjectFieldDesc> list(AmObjectFieldDescQueryReqDto dto);

    void add(AmObjectFieldDescReqDto dto);

    void update(AmObjectFieldDescReqDto dto);

    Page<AmObjectFieldDesc> findPage(AmObjectFieldDescQueryReqDto dto);

    AmObjectFieldDesc findById(Integer id);

    void delete(AmObjectFieldDescReqDto dto);

    AmObjectFieldDesc findByClassWholeName(String className);

    AmObjectFieldDesc findByTableName(String tableName);

    List<String> listObjectNames(AmObjectFieldDescQueryReqDto dto);

    void createObj(AmObjectFieldDescReqDto dto);
}
