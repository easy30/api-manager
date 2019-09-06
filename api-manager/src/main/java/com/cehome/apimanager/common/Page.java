package com.cehome.apimanager.common;

import java.io.Serializable;
import java.util.List;


/**
 * 分页对象
 * 
 * @author sunlei
 *
 * @param <E>
 */
public class Page<E> implements Serializable {
	private static final long serialVersionUID = 4113548847060853651L;
	
	/**
	 * 第几页
	 */
	private int pageIndex = 1;
	/**
	 * 起始位置
	 */
	private int pageOffset;
	/**
	 * 每页显示多少条
	 */
	private int pageSize = 20;
	/**
	 * 总共多少条记录
	 */
	private long totalRecord;
	/**
	 * 总共多少页
	 */
	private int totalPage;
	/**
	 * 放置具体数据的列表
	 */
	private List<E> datas;

	public Page() {
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<E> getDatas() {
		return datas;
	}

	public void setDatas(List<E> datas) {
		this.datas = datas;
	}

	public Integer getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public Page<E> fillPage(List<E> datas, long totalRecord) {
		this.datas = datas;
		this.totalRecord = totalRecord;
		this.totalPage = Double.valueOf(Math.ceil(Double.valueOf(getTotalRecord()) / getPageSize())).intValue();
		return this;
	}
	
	public Page<E> buildPageInfo(int pageIndex, int pageSize){
		this.pageIndex = pageIndex <= 0 ? 1 : pageIndex;
		this.pageSize = pageSize;
		this.pageOffset = (this.pageIndex - 1) * pageSize;
		return this;
	}
	
	public Page<E> buildPageInfo(){
		buildPageInfo(this.pageIndex, this.pageSize);
		return this;
	}
}