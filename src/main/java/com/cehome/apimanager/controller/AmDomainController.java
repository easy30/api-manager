package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/domain")
public class AmDomainController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmDomainController.class);
    @Autowired
    private IAmDomainService domainService;

    /**
     * 返回域名列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(AmDomainQueryReqDto dto) {
        try {
            List<AmDomain> list = domainService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键id查询domain
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmDomain domain = domainService.findById(id);
            return toSuccess(domain);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
