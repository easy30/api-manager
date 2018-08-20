package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmTestGroupActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupActionReqDto;
import com.cehome.apimanager.model.po.AmTestGroupAction;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmTestGroupActionService;
import com.cehome.apimanager.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/groupaction")
public class AmTestGroupActionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmTestGroupActionController.class);

    @Autowired
    private IAmTestGroupActionService testGroupActionService;

    /**
     * 根据id删除分组接口
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(HttpSession session, AmTestGroupActionReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            testGroupActionService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询分组接口列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmTestGroupActionQueryReqDto dto) {
        try {
            Page<AmTestGroupAction> page = testGroupActionService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
