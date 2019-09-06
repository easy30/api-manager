package com.cehome.apimanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;
import com.cehome.apimanager.service.IAmActionTesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/apimanager/tester")
public class AmActionTesterController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmActionTesterController.class);

    @Autowired
    private IAmActionTesterService actionTesterService;

    /**
     * 发送接口请求
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Map<String, Object> send(@RequestBody ActionTesterReqDto dto) {
        try {
            JSONObject response = actionTesterService.send(dto);
            return toSuccess(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据接口编号和环境变量测试接口
     *
     * @param dto
     * @return
     */
    @RequestMapping("/sendByActionId")
    public Map<String, Object> sendByActionId(ActionTesterReqDto dto) {
        try {
            JSONObject response = actionTesterService.sendByActionId(dto);
            return toSuccess(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
