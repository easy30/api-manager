package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmTestGroupAction;

public class AmTestGroupActionQueryReqDto extends AmTestGroupAction {
    private static final long serialVersionUID = -6673954219106118293L;
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
