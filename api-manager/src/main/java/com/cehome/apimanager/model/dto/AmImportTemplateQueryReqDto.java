package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmImportTemplate;

public class AmImportTemplateQueryReqDto extends AmImportTemplate {
    private static final long serialVersionUID = -5426976098282932696L;
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
