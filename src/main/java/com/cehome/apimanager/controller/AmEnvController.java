package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.service.IAmEnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/evn")
public class AmEnvController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmEnvController.class);
    @Autowired
    private IAmEnvService envService;

    /**
     * 获取环境列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(AmEnvQueryReqDto dto) {
        try {
            List<AmEnv> list = envService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
