package com.cehome.apimanager.model.dto;

import com.cehome.apimanager.model.po.AmUser;

public class AmUserQueryReqDto extends AmUser {
	private static final long serialVersionUID = 608014258068965313L;
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
