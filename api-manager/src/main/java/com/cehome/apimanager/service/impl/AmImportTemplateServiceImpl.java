package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.dao.AmImportTemplateDao;
import com.cehome.apimanager.model.dto.AmImportTemplateQueryReqDto;
import com.cehome.apimanager.model.dto.AmImportTemplateReqDto;
import com.cehome.apimanager.model.po.AmImportTemplate;
import com.cehome.apimanager.service.IAmImportTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmImportTemplateServiceImpl implements IAmImportTemplateService {
    @Autowired
    private AmImportTemplateDao importTemplateDao;

    @Override
    public void add(AmImportTemplateReqDto dto) {
        importTemplateDao.add(dto);
    }

    @Override
    public List<AmImportTemplate> list(AmImportTemplateQueryReqDto dto) {
        return importTemplateDao.list(dto);
    }

    @Override
    public AmImportTemplate findById(Integer id) {
        return importTemplateDao.get(id);
    }

    @Override
    public void delete(AmImportTemplateReqDto dto) {
        importTemplateDao.delete(dto.getId());
    }
}
