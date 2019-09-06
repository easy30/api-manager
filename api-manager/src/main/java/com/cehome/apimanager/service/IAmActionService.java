package com.cehome.apimanager.service;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;

import java.util.List;
import java.util.Map;

/**
 * 动作业务接口
 *
 * @author sunlei
 */
public interface IAmActionService {

    /**
     * 添加接口文档
     *
     * @param dto
     */
    void add(AmActionReqDto dto);

    /**
     * 更新接口文档
     *
     * @param dto
     */
    void update(AmActionReqDto dto);

    /**
     * 根据id返回接口文档
     *
     * @param id
     * @return
     */
    AmActionResDto findById(Integer id);

    /**
     * 根据url匹配接口
     *
     * @param requestUrl
     * @return
     */
    Integer findIdByRequestUrl(String requestUrl);

    /**
     * 根据id删除接口文档
     *
     * @param dto
     */
    void delete(AmActionReqDto dto);

    /**
     * 分页查询接口文档列表
     *
     * @param dto
     * @return
     */
    Page<AmAction> findPage(AmActionQueryReqDto dto);

    /**
     * 查询接口文档列表
     *
     * @param dto
     * @return
     */
    List<AmAction> list(AmActionQueryReqDto dto);

    /**
     * 返回全部的接口url列表
     *
     * @return
     */
    List<AmAction> findUrlList();

    /**
     * 查询每个项目下的接口数量
     *
     * @param dto
     * @return
     */
    List<Map<String, Integer>> countGroupByProject(AmActionQueryReqDto dto);

    /**
     * 生成接口pdf文件
     *
     * @param actionId
     * @return
     */
    JSONObject createActionPdf(Integer actionId) throws Exception;
}
