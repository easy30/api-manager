package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cehome.apimanager.cache.CacheProvider;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionDao;
import com.cehome.apimanager.model.dto.*;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.service.IAmActionHistoryService;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import com.cehome.apimanager.utils.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 动作业务接口实现
 *
 * @author sunlei
 */
@Service
public class AmActionServiceImpl implements IAmActionService {

    @Autowired
    private AmActionDao actionDao;

    @Autowired
    private CacheProvider cacheProvider;

    @Autowired
    private IAmOperateLogService operateLogService;

    @Autowired
    private IAmActionHistoryService actionHistoryService;

    @Override
    public void add(AmActionReqDto dto) {
        buildMock(dto);
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        String requestUrl = dto.getRequestUrl();
        if(requestUrl.charAt(0) != '/'){
            dto.setRequestUrl("/" + requestUrl);
        }
        actionDao.add(dto);

        cacheProvider.addActionUrlCache(dto);

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加接口【" + dto.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getCreateUser());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void update(AmActionReqDto dto) {
        AmAction actionResDto = actionDao.get(dto.getId());
        if (actionResDto == null) {
            return;
        }
        String requestUrl = actionResDto.getRequestUrl();
        AmAction action = new AmAction();
        action.setRequestUrl(requestUrl);
        action.setId(dto.getId());
        cacheProvider.removeActionUrlCache(action);

        if(dto.getRequestUrl().charAt(0) != '/'){
            dto.setRequestUrl("/" + dto.getRequestUrl());
        }
        cacheProvider.addActionUrlCache(dto);

        buildMock(dto);
        dto.setUpdateTime(new Date());
        actionDao.update(dto);

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改接口基本信息【" + dto.getId() + "】");
                operateLogReqDto.setOperateUser(dto.getUpdateUser());
                if(!actionResDto.equals(dto)){
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(actionResDto, dto));
                }
                operateLogService.add(operateLogReqDto);
            }
        });

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmActionHistoryReqDto actionHistoryReqDto = new AmActionHistoryReqDto();
                BeanUtils.copyProperties(actionResDto, actionHistoryReqDto);
                actionHistoryReqDto.setId(null);
                actionHistoryReqDto.setActionId(actionResDto.getId());
                actionHistoryReqDto.setUpdateUser(actionResDto.getUpdateUser());
                actionHistoryReqDto.setUpdateTime(new Date());
                actionHistoryService.add(actionHistoryReqDto);
            }
        });
    }

    @Override
    public AmActionResDto findById(Integer id) {
        AmAction amAction = actionDao.get(id);
        if (amAction == null) {
            return null;
        }
        AmActionResDto amActionResDto = new AmActionResDto();
        BeanUtils.copyProperties(amAction, amActionResDto);
        return amActionResDto;
    }

    @Override
    public Integer findIdByRequestUrl(String requestUrl) {
        List<AmAction> actionUrlCache = cacheProvider.getActionUrlCache();
        AmAction result = actionUrlCache.stream().filter(action -> UrlUtils.match(action.getRequestUrl(), requestUrl)).findAny().orElse(null);
        return result == null ? 0 : result.getId();
    }

    @Override
    public void delete(AmActionReqDto dto) {
        AmActionResDto actionResDto = findById(dto.getId());
        if (actionResDto == null) {
            return;
        }
        String requestUrl = actionResDto.getRequestUrl();
        AmAction action = new AmAction();
        action.setRequestUrl(requestUrl);
        action.setId(dto.getId());
        cacheProvider.removeActionUrlCache(action);

        actionDao.delete(dto.getId());

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除接口【" + dto.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getUpdateUser());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public Page<AmAction> findPage(AmActionQueryReqDto dto) {
        return actionDao.find(dto);
    }

    @Override
    public List<AmAction> list(AmActionQueryReqDto dto) {
        return actionDao.list(dto);
    }

    @Override
    public List<AmAction> findUrlList() {
        return actionDao.findUrlList();
    }

    private JSONObject buildMockData(List<JSONObject> columnList) {
        JSONObject mockObject = new JSONObject();
        for (JSONObject column : columnList) {
            int columnType = column.getIntValue("type");
            if (CommonMeta.FieldType.DATE.getCode() == columnType
                    || CommonMeta.FieldType.NUMBER.getCode() == columnType
                    || CommonMeta.FieldType.STRING.getCode() == columnType
                    || CommonMeta.FieldType.BOOLEAN.getCode() == columnType
                    || CommonMeta.FieldType.ARRAY_NUMBER.getCode() == columnType
                    || CommonMeta.FieldType.ARRAY_STRING.getCode() == columnType
                    || CommonMeta.FieldType.ARRAY_BOOLEAN.getCode() == columnType) {
                String name = column.getString("name");
                String rule = column.getString("rule");
                Object value = column.get("defaultVal");
                String ruleDesc = "";
                if(!StringUtils.isEmpty(rule)){
                    if(CommonMeta.FieldType.DATE.getCode() == columnType){
                        if("@date".equals(rule)){
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                            value = sf.format(new Date());
                        } else if("@datetime".equals(rule)){
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            value = sf.format(new Date());
                        }
                        mockObject.put(name, value);
                    } else {
                        if(rule.contains(":")){
                            String[] ruleItem = rule.split(":");
                            String expression = ruleItem[0];
                            String itemValue = ruleItem[1];
                            if(columnType == CommonMeta.FieldType.NUMBER.getCode() || columnType == CommonMeta.FieldType.ARRAY_NUMBER.getCode()){
                                if(itemValue.contains(".")){
                                    value = Double.valueOf(itemValue);
                                } else {
                                    value = Integer.valueOf(itemValue);
                                }
                            } else {
                                value = itemValue;
                            }
                            ruleDesc = '|' + expression;
                            mockObject.put(name + ruleDesc, value);
                        } else {
                            mockObject.put(name, value);
                        }
                    }
                } else {
                    if(columnType == CommonMeta.FieldType.NUMBER.getCode() || columnType == CommonMeta.FieldType.ARRAY_NUMBER.getCode()){
                        String valueStr = value.toString();
                        if(valueStr.contains(".")){
                            value = Double.valueOf(valueStr);
                        } else {
                            value = Integer.valueOf(valueStr);
                        }
                    }
                    mockObject.put(name, value);
                }
            } else if (CommonMeta.FieldType.OBJECT.getCode() == columnType) {
                String name = column.getString("name");
                List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
                JSONObject columnObject = buildMockData(children);
                mockObject.put(name, columnObject);
            } else if (CommonMeta.FieldType.ARRAY_OBJECT.getCode() == columnType) {
                JSONArray objectArray = new JSONArray();
                String name = column.getString("name");
                List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
                for (JSONObject child : children) {
                    List<JSONObject> innerChildren = child.getJSONArray("child").toJavaList(JSONObject.class);
                    JSONObject innerColumnObject = buildMockData(innerChildren);
                    objectArray.add(innerColumnObject);
                }
                mockObject.put(name, objectArray);
            }
        }
        return mockObject;
    }

    private void buildMock(AmActionReqDto dto) {
        // 请求头mock模板生成
        String requestHeadDefinition = dto.getRequestHeadDefinition();
        if (!StringUtils.isEmpty(requestHeadDefinition)) {
            List<JSONObject> columnList = JSON.parseArray(requestHeadDefinition, JSONObject.class);
            JSONObject requestHeadMockData = buildMockData(columnList);
            dto.setRequestHeadMock(JSON.toJSONString(requestHeadMockData, SerializerFeature.WriteNullStringAsEmpty));
        }

        // 请求mock模板生成
        String requestDefinition = dto.getRequestDefinition();
        if (!StringUtils.isEmpty(requestDefinition)) {
            List<JSONObject> columnList = JSON.parseArray(requestDefinition, JSONObject.class);
            JSONObject requestMockData = buildMockData(columnList);
            dto.setRequestMock(JSON.toJSONString(requestMockData, SerializerFeature.WriteNullStringAsEmpty));
        }

        // 返回结果mock模板生成
        String responseDefinition = dto.getResponseDefinition();
        if (!StringUtils.isEmpty(responseDefinition)) {
            List<JSONObject> columnList = JSON.parseArray(responseDefinition, JSONObject.class);
            JSONObject responseMockData = buildMockData(columnList);
            dto.setResponseMock(JSON.toJSONString(responseMockData, SerializerFeature.WriteNullStringAsEmpty));
        }
    }
}
