package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmActionLogin;

public class AmActionLoginQueryReqDto extends AmActionLogin {
    private static final long serialVersionUID = -8293762645629788442L;
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
