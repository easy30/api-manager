package com.cehome.apimanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmObjectFieldDescQueryReqDto;
import com.cehome.apimanager.model.dto.AmObjectFieldDescReqDto;
import com.cehome.apimanager.model.po.AmObjectFieldDesc;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmObjectFieldDescService;
import com.cehome.apimanager.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/object/field")
public class AmObjectFieldDescController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AmObjectFieldDescController.class);

    @Autowired
    private IAmObjectFieldDescService objectFieldDescService;

    /**
     * 获取对象列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(AmObjectFieldDescQueryReqDto dto) {
        try {
            List<AmObjectFieldDesc> list = objectFieldDescService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取对象备注名称列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("/listObjectNames")
    public Map<String, Object> listObjectNames(AmObjectFieldDescQueryReqDto dto) {
        try {
            List<String> list = objectFieldDescService.listObjectNames(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 保存对象信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Map<String, Object> add(HttpSession session, @RequestBody AmObjectFieldDescReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            objectFieldDescService.add(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 创建对象
     *
     * @param session
     * @param dto
     * @return
     */
    @RequestMapping(value = "createObj", method = RequestMethod.POST)
    public Map<String, Object> createObj(HttpSession session, @RequestBody AmObjectFieldDescReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            dto.setUpdateUser(loginUser.getId());
            objectFieldDescService.createObj(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 更新
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Map<String, Object> update(HttpSession session, @RequestBody AmObjectFieldDescReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setUpdateUser(loginUser.getId());
            objectFieldDescService.update(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmObjectFieldDescQueryReqDto dto) {
        try {
            Page<AmObjectFieldDesc> page = objectFieldDescService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmObjectFieldDesc objectFieldDesc = objectFieldDescService.findById(id);
            return toSuccess(objectFieldDesc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据对象备注名称获取
     *
     * @param classWholeNames
     * @return
     */
    @RequestMapping("findObjectDescByClassWholeNames")
    public Map<String, Object> findObjectDescByClassWholeNames(String classWholeNames) {
        try {
            Map<String, Object> mapData = new HashMap<>();
            String[] splitNames = classWholeNames.split(",");
            for (String name : splitNames) {
                if (StringUtils.isEmpty(name.trim())) {
                    continue;
                }
                AmObjectFieldDesc objectFieldDesc = objectFieldDescService.findByClassWholeName(name);
                if (objectFieldDesc != null) {
                    String fieldDescValue = objectFieldDesc.getFieldDescValue();
                    Map map = JSON.parseObject(fieldDescValue, Map.class);
                    mapData.putAll(map);
                }
            }
            return toSuccess(mapData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取对象字段信息
     *
     * @param classWholeNames
     * @return
     */
    @RequestMapping("findObjectInfoByClassWholeNames")
    public Map<String, Object> findObjectInfoByClassWholeNames(String classWholeNames) {
        try {
            AmObjectFieldDesc objectFieldDesc = objectFieldDescService.findByClassWholeName(classWholeNames);
            List<JSONObject> jsonObjects = new ArrayList<>();
            if (objectFieldDesc != null) {
                String fieldInfoValue = objectFieldDesc.getFieldInfoValue();
                jsonObjects = JSON.parseArray(fieldInfoValue, JSONObject.class);
            }
            return toSuccess(jsonObjects);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(HttpSession session, AmObjectFieldDescReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            objectFieldDescService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
