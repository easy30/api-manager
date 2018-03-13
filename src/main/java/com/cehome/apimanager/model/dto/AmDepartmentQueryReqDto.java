package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmDepartment;

public class AmDepartmentQueryReqDto extends AmDepartment {
	private static final long serialVersionUID = -3059665609864523857L;
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
