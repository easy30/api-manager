package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmActionHistory;

public class AmActionHistoryQueryReqDto extends AmActionHistory {
    private static final long serialVersionUID = 186220974712960564L;
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
