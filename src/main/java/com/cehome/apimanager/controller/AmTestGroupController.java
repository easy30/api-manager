package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmTestGroupQueryReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupReqDto;
import com.cehome.apimanager.model.dto.AmTestGroupResDto;
import com.cehome.apimanager.model.po.AmTestGroup;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmTestGroupService;
import com.cehome.apimanager.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/testgroup")
public class AmTestGroupController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AmTestGroupController.class);

    @Autowired
    private IAmTestGroupService testGroupService;

    /**
     * 添加测试分组
     *
     * @param dto
     * @return
     */
    @RequestMapping("add")
    public Map<String, Object> add(HttpSession session, AmTestGroupReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            testGroupService.add(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 更新测试分组
     *
     * @param dto
     * @return
     */
    @RequestMapping("update")
    public Map<String, Object> update(HttpSession session, AmTestGroupReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            testGroupService.update(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键返回测试分组
     *
     * @param dto
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(AmTestGroupQueryReqDto dto) {
        try {
            AmTestGroupResDto amTestGroupResDto = testGroupService.findById(dto);
            return toSuccess(amTestGroupResDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据id删除测试分组
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(HttpSession session, AmTestGroupReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            testGroupService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询测试分组列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmTestGroupQueryReqDto dto) {
        try {
            Page<AmTestGroup> page = testGroupService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取测试分组列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> list(AmTestGroupQueryReqDto dto) {
        try {
            List<AmTestGroup> list = testGroupService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
