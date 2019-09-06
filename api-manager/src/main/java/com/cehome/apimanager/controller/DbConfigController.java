package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.DbConfigQueryReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.model.po.DbConfig;
import com.cehome.apimanager.service.IDbConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/dbconfig")
public class DbConfigController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DbConfigController.class);
    @Autowired
    private IDbConfigService dbConfigService;

    /**
     * 获取数据库配置列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(DbConfigQueryReqDto dto) {
        try {
            List<DbConfig> list = dbConfigService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
