package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import org.springframework.util.StringUtils;

import java.util.*;

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
        AmActionHistory actionHistory = actionHistoryDao.findLastModifyRecordByActionId(dto.getActionId());
        if(Objects.isNull(actionHistory)){
            return compareResult;
        }
        // 基本信息对比
        List<Map<String, String>> baseInfoList = new ArrayList<>();
        Integer domainId = actionResDto.getDomainId();
        Integer historyDomainId = actionHistory.getDomainId();
        if(!domainId.equals(historyDomainId)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "服务地址");
            baseInfo.put("modifyStr", domainService.findById(historyDomainId).getDomainName() + " 修改为 " + domainService.findById(domainId).getDomainName());
            baseInfoList.add(baseInfo);
        }

        String requestUrl = actionResDto.getRequestUrl();
        String historyRequestUrl = actionHistory.getRequestUrl();
        if(!requestUrl.equalsIgnoreCase(historyRequestUrl)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口地址");
            baseInfo.put("modifyStr", historyRequestUrl + " 修改为 " + requestUrl);
            baseInfoList.add(baseInfo);
        }

        String actionName = actionResDto.getActionName();
        String historyActionName = actionHistory.getActionName();
        if(!actionName.equalsIgnoreCase(historyActionName)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口名称");
            baseInfo.put("modifyStr", historyActionName + " 修改为 " + actionName);
            baseInfoList.add(baseInfo);
        }

        Integer requestType = actionResDto.getRequestType();
        Integer historyRequestType = actionHistory.getRequestType();
        if(!requestType.equals(historyRequestType)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "请求类型");
            baseInfo.put("modifyStr", CommonMeta.RequestType.findDescByCode(historyRequestType) + " 修改为 " + CommonMeta.RequestType.findDescByCode(requestType));
            baseInfoList.add(baseInfo);
        }

        Integer moduleId = actionResDto.getModuleId();
        Integer historyModuleId = actionHistory.getModuleId();
        if(!moduleId.equals(historyModuleId)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "所属模块");
            baseInfo.put("modifyStr", moduleService.findById(historyModuleId).getModuleName() + " 修改为 " + moduleService.findById(moduleId).getModuleName());
            baseInfoList.add(baseInfo);
        }

        Integer status = actionResDto.getStatus();
        Integer historyStatus = actionHistory.getStatus();
        if(!status.equals(historyStatus)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口状态");
            baseInfo.put("modifyStr", CommonMeta.Status.findDescByCode(historyStatus) + " 修改为 " + CommonMeta.Status.findDescByCode(status));
            baseInfoList.add(baseInfo);
        }

        Integer actionLevel = actionResDto.getActionLevel();
        Integer historyActionLevel = actionHistory.getActionLevel();
        if(actionLevel != null && !actionLevel.equals(historyActionLevel)){
            Map<String, String> baseInfo = new HashMap<>();
            baseInfo.put("fieldName", "接口级别");
            baseInfo.put("modifyStr", CommonMeta.ActionLevel.findDescByCode(historyActionLevel) + " 修改为 " + CommonMeta.ActionLevel.findDescByCode(actionLevel));
            baseInfoList.add(baseInfo);
        }
        compareResult.put("baseInfoChange", baseInfoList);

        List<JSONObject> paramsChangeList = new ArrayList<>();
        List<JSONObject> requestHeadParamsChangeList = buildRequestHeadParamsChangeList(actionHistory, actionResDto);
        paramsChangeList.addAll(requestHeadParamsChangeList);

        List<JSONObject> requestParamsChangeList = buildRequestParamsChangeList(actionHistory, actionResDto);
        paramsChangeList.addAll(requestParamsChangeList);

        List<JSONObject> responseParamsChangeList = buildResponseParamsChangeList(actionHistory, actionResDto);
        paramsChangeList.addAll(responseParamsChangeList);

        compareResult.put("paramsInfoChange", paramsChangeList);
        return compareResult;
    }

    private List<JSONObject> buildResponseParamsChangeList(AmActionHistory actionHistory, AmActionResDto actionResDto) {
        //请求头参数变化对比
        String responseDefinition = actionResDto.getResponseDefinition();
        String actionHistoryResponseDefinition = actionHistory.getResponseDefinition();
        List<String> responseFieldNames = new ArrayList<>();
        JSONObject responseFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(responseDefinition)){
            JSONArray responseDefinitionArray = JSON.parseArray(responseDefinition);
            buildFieldArray(responseFieldNames, responseFieldInfo, responseDefinitionArray);
        }

        List<String> historyResponseFieldNames = new ArrayList<>();
        JSONObject historyResponseFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(actionHistoryResponseDefinition)){
            JSONArray historyResponseDefinitionArray = JSON.parseArray(actionHistoryResponseDefinition);
            buildFieldArray(historyResponseFieldNames, historyResponseFieldInfo, historyResponseDefinitionArray);
        }

        List<String> copyResponseFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(responseFieldNames.toArray(new String[]{}), responseFieldNames.size())));
        List<String> copyHistoryResponseFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyResponseFieldNames.toArray(new String[]{}), historyResponseFieldNames.size())));

        List<JSONObject> paramsChangeList = new ArrayList<>();
        // 新增字段
        copyResponseFieldNames.removeAll(copyHistoryResponseFieldNames);
        List<String> diffRequestNames = Arrays.asList(Arrays.copyOf(copyResponseFieldNames.toArray(new String[]{}), copyResponseFieldNames.size()));
        for(String requestFieldName : copyResponseFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestFieldName);
            obj.put("operateDesc", "新增");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //删除字段
        copyResponseFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(responseFieldNames.toArray(new String[]{}), responseFieldNames.size())));
        copyHistoryResponseFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyResponseFieldNames.toArray(new String[]{}), historyResponseFieldNames.size())));
        copyHistoryResponseFieldNames.removeAll(copyResponseFieldNames);
        for(String responseFieldName : copyHistoryResponseFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", responseFieldName);
            obj.put("operateDesc", "删除");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //修改字段
        copyResponseFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(responseFieldNames.toArray(new String[]{}), responseFieldNames.size())));
        copyResponseFieldNames.removeAll(diffRequestNames);
        for(String responseFieldName : copyResponseFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", responseFieldName);
            obj.put("operateDesc", "修改");
            JSONObject responseField = responseFieldInfo.getJSONObject(responseFieldName);
            JSONObject historyResponseField = historyResponseFieldInfo.getJSONObject(responseFieldName);
            // 字段类型
            String typeNew = responseField.getString("type");
            String typeOld = historyResponseField.getString("type");

            StringBuffer sbuffer = new StringBuffer();
            if(!typeNew.equals(typeOld)){
                sbuffer.append("类型：" + CommonMeta.FieldType.findDescByCode(typeOld) + " 修改为 " + CommonMeta.FieldType.findDescByCode(typeNew));
            }
            if(sbuffer.length() > 0){
                obj.put("modifyStr", sbuffer.toString());
                paramsChangeList.add(obj);
            }
        }
        return paramsChangeList;
    }

    private List<JSONObject> buildRequestParamsChangeList(AmActionHistory actionHistory, AmActionResDto actionResDto) {
        //请求头参数变化对比
        String requestDefinition = actionResDto.getRequestDefinition();
        String actionHistoryRequestDefinition = actionHistory.getRequestDefinition();
        List<String> requestFieldNames = new ArrayList<>();
        JSONObject requestFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(requestDefinition)){
            JSONArray requestDefinitionArray = JSON.parseArray(requestDefinition);
            buildFieldArray(requestFieldNames, requestFieldInfo, requestDefinitionArray);
        }

        List<String> historyRequestFieldNames = new ArrayList<>();
        JSONObject historyRequestFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(actionHistoryRequestDefinition)){
            JSONArray historyRequestDefinitionArray = JSON.parseArray(actionHistoryRequestDefinition);
            buildFieldArray(historyRequestFieldNames, historyRequestFieldInfo, historyRequestDefinitionArray);
        }

        List<String> copyRequestFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestFieldNames.toArray(new String[]{}), requestFieldNames.size())));
        List<String> copyHistoryRequestFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyRequestFieldNames.toArray(new String[]{}), historyRequestFieldNames.size())));

        List<JSONObject> paramsChangeList = new ArrayList<>();
        // 新增字段
        copyRequestFieldNames.removeAll(copyHistoryRequestFieldNames);
        List<String> diffRequestNames = Arrays.asList(Arrays.copyOf(copyRequestFieldNames.toArray(new String[]{}), copyRequestFieldNames.size()));
        for(String requestFieldName : copyRequestFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestFieldName);
            obj.put("operateDesc", "新增");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //删除字段
        copyRequestFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestFieldNames.toArray(new String[]{}), requestFieldNames.size())));
        copyHistoryRequestFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyRequestFieldNames.toArray(new String[]{}), historyRequestFieldNames.size())));
        copyHistoryRequestFieldNames.removeAll(copyRequestFieldNames);
        for(String requestFieldName : copyHistoryRequestFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestFieldName);
            obj.put("operateDesc", "删除");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //修改字段
        copyRequestFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestFieldNames.toArray(new String[]{}), requestFieldNames.size())));
        copyRequestFieldNames.removeAll(diffRequestNames);
        for(String requestFieldName : copyRequestFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestFieldName);
            obj.put("operateDesc", "修改");
            JSONObject requestField = requestFieldInfo.getJSONObject(requestFieldName);
            JSONObject historyRequestField = historyRequestFieldInfo.getJSONObject(requestFieldName);
            // 是否必填项
            String requiredNew = requestField.getString("required");
            String requiredOld = historyRequestField.getString("required");
            // 字段类型
            String typeNew = requestField.getString("type");
            String typeOld = historyRequestField.getString("type");

            StringBuffer sbuffer = new StringBuffer();
            if(!requiredNew.equals(requiredOld)){
                sbuffer.append("必填：" + CommonMeta.Required.findDescByCode(requiredOld) + " 修改为 " + CommonMeta.Required.findDescByCode(requiredNew) + "<br/>");
            }

            if(!typeNew.equals(typeOld)){
                sbuffer.append("类型：" + CommonMeta.FieldType.findDescByCode(typeOld) + " 修改为 " + CommonMeta.FieldType.findDescByCode(typeNew) + "<br/>");
            }
            if(sbuffer.length() > 0){
                obj.put("modifyStr", sbuffer.toString());
                paramsChangeList.add(obj);
            }
        }
        return paramsChangeList;
    }

    private List<JSONObject> buildRequestHeadParamsChangeList(AmActionHistory actionHistory, AmActionResDto actionResDto) {
        //请求头参数变化对比
        String requestHeadDefinition = actionResDto.getRequestHeadDefinition();
        String actionHistoryRequestHeadDefinition = actionHistory.getRequestHeadDefinition();
        List<String> requestHeadFieldNames = new ArrayList<>();
        JSONObject requestHeadFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(requestHeadDefinition)){
            JSONArray requestHeadDefinitionArray = JSON.parseArray(requestHeadDefinition);
            buildFieldArray(requestHeadFieldNames, requestHeadFieldInfo, requestHeadDefinitionArray);
        }

        List<String> historyRequestHeadFieldNames = new ArrayList<>();
        JSONObject historyRequestHeadFieldInfo = new JSONObject();
        if(!StringUtils.isEmpty(actionHistoryRequestHeadDefinition)){
            JSONArray historyRequestHeadDefinitionArray = JSON.parseArray(actionHistoryRequestHeadDefinition);
            buildFieldArray(historyRequestHeadFieldNames, historyRequestHeadFieldInfo, historyRequestHeadDefinitionArray);
        }

        List<String> copyRequestHeadFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestHeadFieldNames.toArray(new String[]{}), requestHeadFieldNames.size())));
        List<String> copyHistoryRequestHeadFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyRequestHeadFieldNames.toArray(new String[]{}), historyRequestHeadFieldNames.size())));

        List<JSONObject> paramsChangeList = new ArrayList<>();
        // 新增字段
        copyRequestHeadFieldNames.removeAll(copyHistoryRequestHeadFieldNames);
        List<String> diffRequestHeadNames = Arrays.asList(Arrays.copyOf(copyRequestHeadFieldNames.toArray(new String[]{}), copyRequestHeadFieldNames.size()));
        for(String requestHeadFieldName : copyRequestHeadFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestHeadFieldName);
            obj.put("operateDesc", "新增");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //删除字段
        copyRequestHeadFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestHeadFieldNames.toArray(new String[]{}), requestHeadFieldNames.size())));
        copyHistoryRequestHeadFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(historyRequestHeadFieldNames.toArray(new String[]{}), historyRequestHeadFieldNames.size())));
        copyHistoryRequestHeadFieldNames.removeAll(copyRequestHeadFieldNames);
        for(String requestHeadFieldName : copyHistoryRequestHeadFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestHeadFieldName);
            obj.put("operateDesc", "删除");
            obj.put("modifyStr", "");
            paramsChangeList.add(obj);
        }

        //修改字段
        copyRequestHeadFieldNames = new ArrayList<>(Arrays.asList(Arrays.copyOf(requestHeadFieldNames.toArray(new String[]{}), requestHeadFieldNames.size())));
        copyRequestHeadFieldNames.removeAll(diffRequestHeadNames);
        for(String requestHeadFieldName : copyRequestHeadFieldNames){
            JSONObject obj = new JSONObject();
            obj.put("fieldName", requestHeadFieldName);
            obj.put("operateDesc", "修改");
            JSONObject requestHeadField = requestHeadFieldInfo.getJSONObject(requestHeadFieldName);
            JSONObject historyRequestHeadField = historyRequestHeadFieldInfo.getJSONObject(requestHeadFieldName);
            // 字段类型
            String typeNew = requestHeadField.getString("type");
            String typeOld = historyRequestHeadField.getString("type");

            StringBuffer sbuffer = new StringBuffer();
            if(!typeNew.equals(typeOld)){
                sbuffer.append("类型：" + CommonMeta.FieldType.findDescByCode(typeOld) + " 修改为 " + CommonMeta.FieldType.findDescByCode(typeNew) + "<br/>");
            }
            if(sbuffer.length() > 0){
                obj.put("modifyStr", sbuffer.toString());
                paramsChangeList.add(obj);
            }
        }
        return paramsChangeList;
    }

    private void buildFieldArray(List<String> fieldNames, JSONObject fieldInfo, JSONArray definitionArray) {
        if(definitionArray == null || definitionArray.size() == 0){
            return;
        }
        for(int i = 0; i < definitionArray.size(); i ++){
            JSONObject definitionObject = definitionArray.getJSONObject(i);
            JSONArray children = definitionObject.getJSONArray("child");
            if(children != null && children.size() > 0){
                buildFieldArray(fieldNames, fieldInfo, children);
            }
            String name = definitionObject.getString("name");
            fieldNames.add(name);
            fieldInfo.put(name, definitionObject);
        }
    }
}
