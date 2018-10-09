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
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.pdf.PdfDocumentGenerator;
import com.cehome.apimanager.pdf.bean.ActionInfoVo;
import com.cehome.apimanager.service.IAmActionHistoryService;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IAmDomainService domainService;

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
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加接口【" + dto.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getCreateUser());
                operateLogReqDto.setEntityId(dto.getId());
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
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改接口基本信息【" + dto.getActionName() + "】");
                operateLogReqDto.setOperateUser(dto.getUpdateUser());
                if(!actionResDto.equals(dto)){
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(actionResDto, dto));
                }
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
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

        AmAction entity = actionDao.get(dto.getId());
        actionDao.delete(dto.getId());

        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void doTask() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除接口【" + entity.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getUpdateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public Page<AmAction> findPage(AmActionQueryReqDto dto) {
        Page<AmAction> actionPage = actionDao.find(dto);
        List<AmAction> datas = actionPage.getDatas();
        if(CollectionUtils.isEmpty(datas)){
            return actionPage;
        }
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        List<AmAction> result = new ArrayList<>();
        for(AmAction action : datas){
            AmActionResDto actionResDto = new AmActionResDto();
            BeanUtils.copyProperties(action, actionResDto);
            if(action.getCreateUser() != null){
                actionResDto.setCreateUserName(userDicMap.get(action.getCreateUser() + ""));
            }
            if(action.getUpdateUser() != null){
                actionResDto.setUpdateUserName(userDicMap.get(action.getUpdateUser() + ""));
            }
            result.add(actionResDto);
        }
        actionPage.setDatas(result);
        return actionPage;
    }

    @Override
    public List<AmAction> list(AmActionQueryReqDto dto) {
        List<AmAction> datas = actionDao.list(dto);
        Map<String, String> userDicMap = cacheProvider.getUserDicMap();
        List<AmAction> result = new ArrayList<>();
        for(AmAction action : datas){
            AmActionResDto actionResDto = new AmActionResDto();
            BeanUtils.copyProperties(action, actionResDto);
            if(action.getCreateUser() != null){
                actionResDto.setCreateUserName(userDicMap.get(action.getCreateUser() + ""));
            }
            if(action.getUpdateUser() != null){
                actionResDto.setUpdateUserName(userDicMap.get(action.getUpdateUser() + ""));
            }
            result.add(actionResDto);
        }
        return result;
    }

    @Override
    public List<AmAction> findUrlList() {
        return actionDao.findUrlList();
    }

    @Override
    public List<Map<String, Integer>> countGroupByProject(AmActionQueryReqDto dto) {
        return actionDao.countGroupByProject(dto);
    }

    @Override
    public JSONObject createActionPdf(Integer actionId) throws Exception{
        JSONObject result = new JSONObject();
        AmAction action = actionDao.get(actionId);
        ActionInfoVo actionInfoVo = new ActionInfoVo();
        //接口基本信息
        actionInfoVo.setActionName(action.getActionName());
        actionInfoVo.setActionDesc(action.getActionDesc());
        Integer domainId = action.getDomainId();
        AmDomain domain = domainService.findById(domainId);
        String domainName = domain.getDomainName();
        String url = "http://" + domainName + action.getRequestUrl();
        actionInfoVo.setActionUrl(url);
        actionInfoVo.setMethod(CommonMeta.RequestType.findDescByCode(action.getRequestType()));
        //接口请求头参数
        String requestHeadDefinition = action.getRequestHeadDefinition();
        if(!StringUtils.isEmpty(requestHeadDefinition)){
            JSONArray jsonArray = JSON.parseArray(requestHeadDefinition);
            if(jsonArray != null && !jsonArray.isEmpty()){
                List<Map<String, Object>> rows = new ArrayList<>();
                ParamsUtils.convertToRows(JSON.parseArray(requestHeadDefinition), rows);
                actionInfoVo.setRequestHeadParams(rows);
            }
        }
        // 接口请求参数
        String requestDefinition = action.getRequestDefinition();
        if(!StringUtils.isEmpty(requestDefinition)){
            JSONArray jsonArray = JSON.parseArray(requestDefinition);
            if(jsonArray != null && !jsonArray.isEmpty()){
                List<Map<String, Object>> rows = new ArrayList<>();
                ParamsUtils.convertToRows(JSON.parseArray(requestDefinition), rows);
                actionInfoVo.setRequestParams(rows);
            }
        }
        //接口响应参数
        String responseDefinition = action.getResponseDefinition();
        if(!StringUtils.isEmpty(responseDefinition)){
            JSONArray jsonArray = JSON.parseArray(responseDefinition);
            if(jsonArray != null && !jsonArray.isEmpty()){
                List<Map<String, Object>> rows = new ArrayList<>();
                ParamsUtils.convertToRows(JSON.parseArray(responseDefinition), rows);
                actionInfoVo.setResponseParams(rows);
            }
        }
        //接口响应失败
        String responseFailDefinition = action.getResponseFailDefinition();
        if(!StringUtils.isEmpty(responseFailDefinition)){
            JSONArray jsonArray = JSON.parseArray(responseFailDefinition);
            if(jsonArray != null && !jsonArray.isEmpty()){
                List<Map<String, Object>> rows = new ArrayList<>();
                ParamsUtils.convertToRows(JSON.parseArray(responseFailDefinition), rows);
                actionInfoVo.setResponseFailParams(rows);
            }
        }
        String responseMock = action.getResponseMock();
        if(!StringUtils.isEmpty(responseMock)){
            String responseJson = MockUtils.buildMockData(responseMock);
            ObjectMapper mapper = new ObjectMapper();
            String formatJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(JSON.parseObject(responseJson));
            formatJson = formatJson.replaceAll("&", "&amp;");
            actionInfoVo.setResponseJson(formatJson);
        }

        // classpath 中模板路径
        String template = "templates/actionInfoTemplate.ftl";
        // classpath 路径
        String outputFileClass = ResourceLoader.getPath("");
        String outputFile = new File(outputFileClass)
                .getParentFile().getParent()
                + "/tmp/"
                + System.currentTimeMillis() + ".pdf" ;
        // 生成pdf路径
        outputFile = outputFile == null ? new File(outputFileClass)
                .getParentFile().getParent()
                + "/tmp/"
                + System.currentTimeMillis() + ".pdf" : outputFile;

        PdfDocumentGenerator pdfGenerator = new PdfDocumentGenerator();
        // 生成pdf
        pdfGenerator.generate(template, actionInfoVo, outputFile);

        result.put("filePath", outputFile);
        result.put("fileName", action.getActionName());
        return result;
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
                        if(StringUtils.isEmpty(valueStr)){
                            value = 0;
                        } else {
                            if(valueStr.contains(".")){
                                value = Double.valueOf(valueStr);
                            } else {
                                value = Long.valueOf(valueStr);
                            }
                        }
                    }
                    mockObject.put(name, value);
                }
            } else if (CommonMeta.FieldType.OBJECT.getCode() == columnType) {
                String name = column.getString("name");
                JSONArray child = column.getJSONArray("child");
                if(child != null){
                    List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
                    JSONObject columnObject = buildMockData(children);
                    mockObject.put(name, columnObject);
                } else {
                    mockObject.put(name, new JSONObject());
                }
            } else if (CommonMeta.FieldType.ARRAY_OBJECT.getCode() == columnType) {
                JSONArray objectArray = new JSONArray();
                String name = column.getString("name");
                JSONArray array = column.getJSONArray("child");
                if(array != null){
                    List<JSONObject> children = column.getJSONArray("child").toJavaList(JSONObject.class);
                    for (JSONObject child : children) {
                        List<JSONObject> innerChildren = child.getJSONArray("child").toJavaList(JSONObject.class);
                        JSONObject innerColumnObject = buildMockData(innerChildren);
                        objectArray.add(innerColumnObject);
                    }
                    mockObject.put(name, objectArray);
                } else {
                    mockObject.put(name, objectArray);
                }
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
