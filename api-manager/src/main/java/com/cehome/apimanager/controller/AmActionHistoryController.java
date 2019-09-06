package com.cehome.apimanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionHistoryQueryReqDto;
import com.cehome.apimanager.model.po.AmActionHistory;
import com.cehome.apimanager.service.IAmActionHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/actionhistory")
public class AmActionHistoryController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmActionHistoryController.class);

    @Autowired
    private IAmActionHistoryService actionHistoryService;

    /**
     * 根据主键历史接口文档
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmActionHistory actionHistory = actionHistoryService.findById(id);
            return toSuccess(actionHistory);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询接口文档历史列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmActionHistoryQueryReqDto dto) {
        try {
            Page<AmActionHistory> page = actionHistoryService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取历史接口文档列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> list(AmActionHistoryQueryReqDto dto) {
        try {
            List<AmActionHistory> list = actionHistoryService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 比较接口的某个历史版本与当前的差异
     *
     * @param dto
     * @return
     */
    @RequestMapping("compareHistoryDiff")
    public Map<String, Object> compareHistoryDiff(AmActionHistoryQueryReqDto dto) {
        try {
            JSONObject compareResult = actionHistoryService.compareHistoryDiff(dto);
            return toSuccess(compareResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
