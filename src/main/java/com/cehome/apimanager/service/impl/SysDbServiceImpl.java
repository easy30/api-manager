package com.cehome.apimanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmObjectFieldDescReqDto;
import com.cehome.apimanager.model.dto.SysDbQueryReqDto;
import com.cehome.apimanager.model.dto.SysDbReqDto;
import com.cehome.apimanager.model.dto.SysDbResDto;
import com.cehome.apimanager.model.po.AmObjectFieldDesc;
import com.cehome.apimanager.service.IAmObjectFieldDescService;
import com.cehome.apimanager.service.ISysDbService;
import com.cehome.apimanager.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysDbServiceImpl implements ISysDbService {

    @Autowired
    private IAmObjectFieldDescService objectFieldDescService;

    @Override
    public Page<SysDbResDto> findTables(SysDbQueryReqDto sysDbQueryReqDto) {
        List<SysDbResDto> tables = DbUtils.getTables(sysDbQueryReqDto.getTableName());
        Page<SysDbResDto> page = new Page<>();
        Integer pageIndex = sysDbQueryReqDto.getPageIndex();
        Integer pageSize = sysDbQueryReqDto.getPageSize();
        page.buildPageInfo(pageIndex, pageSize);
        Integer pageOffset = page.getPageOffset();
        Integer toIndex = pageOffset + page.getPageSize();
        if(toIndex >= tables.size()){
            toIndex = tables.size();
        }
        page.fillPage(tables.subList(page.getPageOffset(), toIndex), tables.size());
        return page;
    }

    @Override
    public void makeObjectDesc(SysDbReqDto sysDbReqDto) {
        List<SysDbResDto> sysDbResDtoList = DbUtils.getColumnsInfo(sysDbReqDto);
        JSONObject jsonObject = new JSONObject();
        for(SysDbResDto sysDbResDto : sysDbResDtoList){
            String columnName = sysDbResDto.getColumnName();
            if("id".equals(columnName)){
                continue;
            }
            String[] nameSplit = columnName.split("_");
            String fieldName = "";
            for(String split : nameSplit){
                if(StringUtils.isEmpty(fieldName)){
                    fieldName += split;
                } else {
                    fieldName += split.substring(0, 1).toUpperCase() + split.substring(1);
                }
            }
            jsonObject.put(fieldName, sysDbResDto.getColumnComment());
        }
        AmObjectFieldDescReqDto objectFieldDescReqDto = new AmObjectFieldDescReqDto();
        String tableName = sysDbReqDto.getTableName();
        String[] tableNameSplit = tableName.split("_");
        String className = "";
        for(String split : tableNameSplit){
            if(StringUtils.isEmpty(className)){
                className += split;
            } else {
                className += split.substring(0, 1).toUpperCase() + split.substring(1);
            }
        }
        objectFieldDescReqDto.setClassWholeName(className);
        objectFieldDescReqDto.setFieldDescValue(jsonObject.toJSONString());
        AmObjectFieldDesc objectFieldDesc = objectFieldDescService.findByClassWholeName(className);
        if(objectFieldDesc != null){
            objectFieldDescReqDto.setUpdateTime(new Date());
            objectFieldDescReqDto.setUpdateUser(sysDbReqDto.getOperateUser());
            objectFieldDescReqDto.setId(objectFieldDesc.getId());
            objectFieldDescService.update(objectFieldDescReqDto);
        } else {
            objectFieldDescReqDto.setCreateTime(new Date());
            objectFieldDescReqDto.setCreateUser(sysDbReqDto.getOperateUser());
            objectFieldDescService.add(objectFieldDescReqDto);
        }
    }
}
