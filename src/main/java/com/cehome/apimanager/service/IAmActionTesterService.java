package com.cehome.apimanager.service;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.model.dto.ActionTesterReqDto;

/**
 * 接口测试
 *
 * @author sunlei
 */
public interface IAmActionTesterService {

    /**
     * 发送接口请求
     *
     * @param dto
     */
    JSONObject send(ActionTesterReqDto dto);

    /**
     * 根据接口编号和环境变量测试接口
     *
     * @param dto
     * @return
     */
    JSONObject sendByActionId(ActionTesterReqDto dto);
}
