package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionHistoryDao;
import com.cehome.apimanager.model.dto.AmActionHistoryQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionHistoryReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmActionHistory;
import com.cehome.apimanager.service.IAmActionHistoryService;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.service.IAmModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AmActionHistoryServiceImpl implements IAmActionHistoryService {
    @Autowired
    private AmActionHistoryDao actionHistoryDao;

    @Autowired
    private IAmActionService actionService;

    @Autowired
    private IAmDomainService domainService;

    @Autowired
    private IAmModuleService moduleService;

    @Override
    public void add(AmActionHistoryReqDto dto) {
        actionHistoryDao.add(dto);
    }


    @Override
    public void update(AmActionHistoryReqDto dto) {
        actionHistoryDao.update(dto);
    }

    @Override
    public AmActionHistory findById(Integer id) {
        return actionHistoryDao.get(id);
    }

    @Override
    public void delete(AmActionHistoryReqDto dto) {
        actionHistoryDao.delete(dto.getId());
    }

    @Override
    public Page<AmActionHistory> findPage(AmActionHistoryQueryReqDto dto) {
        return actionHistoryDao.find(dto);
    }

    @Override
    public List<AmActionHistory> list(AmActionHistoryQueryReqDto dto) {
        return actionHistoryDao.list(dto);
    }

    @Override
    public JSONObject compareHistoryDiff(AmActionHistoryQueryReqDto dto) {
        JSONObject compareResult = new JSONObject();
        AmActionResDto actionResDto = actionService.findById(dto.getActionId());
        AmActionHistory actionHistory = actionHistoryDao.get(dto.getId());
        // 基本信息对比
        List<Map<String, String>> baseInfoList = new ArrayList<>();
        Integer domainId = actionResDto.getDomainId();
        Integer historyDomainId = actionHistory.getDomainId();
        if(!domainId.equals(historyDomainId)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "服务地址");
            baseInfo.put("before", domainService.findById(historyDomainId).getDomainName());
            baseInfo.put("after", domainService.findById(domainId).getDomainName());
            baseInfoList.add(baseInfo);
        }

        String requestUrl = actionResDto.getRequestUrl();
        String historyRequestUrl = actionHistory.getRequestUrl();
        if(!requestUrl.equalsIgnoreCase(historyRequestUrl)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口地址");
            baseInfo.put("before", historyRequestUrl);
            baseInfo.put("after", requestUrl);
            baseInfoList.add(baseInfo);
        }

        String actionName = actionResDto.getActionName();
        String historyActionName = actionHistory.getActionName();
        if(!actionName.equalsIgnoreCase(historyActionName)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口名称");
            baseInfo.put("before", historyActionName);
            baseInfo.put("after", actionName);
            baseInfoList.add(baseInfo);
        }

        Integer requestType = actionResDto.getRequestType();
        Integer historyRequestType = actionHistory.getRequestType();
        if(!requestType.equals(historyRequestType)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "请求类型");
            baseInfo.put("before", CommonMeta.RequestType.findDescByCode(historyRequestType));
            baseInfo.put("after", CommonMeta.RequestType.findDescByCode(requestType));
            baseInfoList.add(baseInfo);
        }

        Integer moduleId = actionResDto.getModuleId();
        Integer historyModuleId = actionHistory.getModuleId();
        if(!moduleId.equals(historyModuleId)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "所属模块");
            baseInfo.put("before", moduleService.findById(historyModuleId).getModuleName());
            baseInfo.put("after", moduleService.findById(moduleId).getModuleName());
            baseInfoList.add(baseInfo);
        }

        Integer status = actionResDto.getStatus();
        Integer historyStatus = actionHistory.getStatus();
        if(!status.equals(historyStatus)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口状态");
            baseInfo.put("before", CommonMeta.Status.findDescByCode(historyStatus));
            baseInfo.put("after", CommonMeta.Status.findDescByCode(status));
            baseInfoList.add(baseInfo);
        }
        compareResult.put("baseInfoChange", baseInfoList);

        //参数变化对比
        actionResDto.getRequestData();
        return null;
    }
}
