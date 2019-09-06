package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.SysDbQueryReqDto;
import com.cehome.apimanager.model.dto.SysDbReqDto;
import com.cehome.apimanager.model.dto.SysDbResDto;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.ISysDbService;
import com.cehome.apimanager.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/sysdb")
public class SysDbController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SysDbController.class);

    @Autowired
    private ISysDbService sysDbService;

    @RequestMapping("findTables")
    public Map<String, Object> findTables(SysDbQueryReqDto sysDbQueryReqDto) {
        try {
            Page<SysDbResDto> sysDbResDtoPage = sysDbService.findTables(sysDbQueryReqDto);
            return toSuccess(sysDbResDtoPage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    @RequestMapping("makeObjectDesc")
    public Map<String, Object> makeObjectDesc(HttpSession session, SysDbReqDto sysDbReqDto){
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            sysDbReqDto.setOperateUser(loginUser.getId());
            sysDbService.makeObjectDesc(sysDbReqDto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
