package com.cehome.apimanager.service;

import com.cehome.apimanager.model.dto.AmImportTemplateQueryReqDto;
import com.cehome.apimanager.model.dto.AmImportTemplateReqDto;
import com.cehome.apimanager.model.po.AmImportTemplate;

import java.util.List;

public interface IAmImportTemplateService {
    void add(AmImportTemplateReqDto dto);

    List<AmImportTemplate> list(AmImportTemplateQueryReqDto dto);

    AmImportTemplate findById(Integer id);

    void delete(AmImportTemplateReqDto dto);
}
