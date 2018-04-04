package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmAction;

public class AmActionQueryReqDto extends AmAction {
    private static final long serialVersionUID = -2793848993488196621L;
    private Integer pageIndex = 1;
    private Integer pageSize = 20;

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
}
