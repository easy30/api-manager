package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.AmImportTemplateQueryReqDto;
import com.cehome.apimanager.model.dto.AmImportTemplateReqDto;
import com.cehome.apimanager.model.po.AmImportTemplate;
import com.cehome.apimanager.service.IAmImportTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/template")
public class AmImportTemplateController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmImportTemplateController.class);

    @Autowired
    private IAmImportTemplateService importTemplateService;

    /**
     * 保存模板信息
     *
     * @param dto
     * @return
     */
    @RequestMapping("add")
    public Map<String, Object> add(AmImportTemplateReqDto dto) {
        try {
            importTemplateService.add(dto);
            return toSuccess(dto.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 返回模板列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(AmImportTemplateQueryReqDto dto) {
        try {
            List<AmImportTemplate> list = importTemplateService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键id查询模板
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmImportTemplate importTemplate = importTemplateService.findById(id);
            return toSuccess(importTemplate);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 删除模板
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(AmImportTemplateReqDto dto) {
        try {
            importTemplateService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
