package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmOperateLogQueryReqDto;
import com.cehome.apimanager.model.po.AmOperateLog;
import com.cehome.apimanager.service.IAmOperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/operatelog")
public class AmOperateLogController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmOperateLogController.class);

    @Autowired
    private IAmOperateLogService operateLogService;

    /**
     * 分页获取日志列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmOperateLogQueryReqDto dto) {
        try {
            Page<AmOperateLog> page = operateLogService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取日志列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> list(AmOperateLogQueryReqDto dto) {
        try {
            List<AmOperateLog> list = operateLogService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键查询日志
     *
     * @param dto
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(AmOperateLogQueryReqDto dto) {
        try {
            AmOperateLog operateLog = operateLogService.findById(dto.getId());
            return toSuccess(operateLog);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
