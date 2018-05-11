package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmEnvQueryReqDto;
import com.cehome.apimanager.model.dto.AmEnvReqDto;
import com.cehome.apimanager.model.po.AmEnv;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmEnvService;
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
@RequestMapping("/apimanager/env")
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

    /**
     * 保存环境信息
     *
     * @param dto
     * @return
     */
    @RequestMapping("add")
    public Map<String, Object> add(HttpSession session, AmEnvReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setOperateUser(loginUser.getId());
            envService.add(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 更新环境信息
     *
     * @param dto
     * @return
     */
    @RequestMapping("update")
    public Map<String, Object> update(HttpSession session, AmEnvReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setOperateUser(loginUser.getId());
            envService.update(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询环境列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmEnvQueryReqDto dto) {
        try {
            Page<AmEnv> page = envService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键id查询环境
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmEnv env = envService.findById(id);
            return toSuccess(env);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 删除环境
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(HttpSession session, AmEnvReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setOperateUser(loginUser.getId());
            envService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
