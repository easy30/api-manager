package com.cehome.apimanager.model.dto;

import java.io.Serializable;

public class SysDbReqDto implements Serializable{
    private static final long serialVersionUID = 3750679629938179284L;
    private String tableName;
    private Integer operateUser;
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Integer operateUser) {
        this.operateUser = operateUser;
    }
}
