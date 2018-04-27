package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmDomainQueryReqDto;
import com.cehome.apimanager.model.dto.AmDomainReqDto;
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
     * 保存域名信息
     *
     * @param dto
     * @return
     */
    @RequestMapping("add")
    public Map<String, Object> add(AmDomainReqDto dto) {
        try {
            domainService.add(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 更新域名信息
     *
     * @param dto
     * @return
     */
    @RequestMapping("update")
    public Map<String, Object> update(AmDomainReqDto dto) {
        try {
            domainService.update(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

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
     * 分页查询域名列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmDomainQueryReqDto dto) {
        try {
            Page<AmDomain> page = domainService.findPage(dto);
            return toSuccess(page);
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

    /**
     * 删除域名
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(AmDomainReqDto dto) {
        try {
            domainService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
