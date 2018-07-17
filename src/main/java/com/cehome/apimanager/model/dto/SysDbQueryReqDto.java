package com.cehome.apimanager.model.dto;

import java.io.Serializable;

public class SysDbQueryReqDto implements Serializable {
    private static final long serialVersionUID = 5273499050699037493L;
    private Integer pageIndex = 1;
    private Integer pageSize = 20;
    private String tableName;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
