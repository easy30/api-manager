package com.cehome.apimanager.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseDao<E> {
	private Class<E> clz;

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	public void add(E entity) {
		sqlSessionTemplate.insert(getClassName() + ".insertSelective", entity);
	}

	public void update(E entity) {
		sqlSessionTemplate.update(getClassName() + ".updateSelective", entity);
	}

	public E get(Integer id) {
		return sqlSessionTemplate.selectOne(getClassName() + ".selectByPrimaryKey", id);
	}

	public void delete(Integer id){
		sqlSessionTemplate.delete(getClassName() + ".deleteByPrimaryKey", id);
	}
	
	public Page<E> find(E entity){
		String dataJson = JSON.toJSONString(entity);
		JSONObject dataObject = JSON.parseObject(dataJson, JSONObject.class);
		Page<E> page = new Page<>();
		Integer pageIndex = dataObject.getInteger("pageIndex");
		Integer pageSize = dataObject.getInteger("pageSize");
		page.buildPageInfo(pageIndex, pageSize);
		dataObject.put("pageOffset", page.getPageOffset());
		List<E> datas = sqlSessionTemplate.selectList(getClassName() + ".find", dataObject);
		Integer totalRecord = sqlSessionTemplate.selectOne(getClassName() + ".findCount", dataObject);
		page.fillPage(datas, totalRecord);
		return page;
	}
	
	public List<E> list(E entity) {
		return sqlSessionTemplate.selectList(getClassName() + ".list", entity);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<E> getClz() {
		if (clz == null) {
			clz = ((Class<E>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
					.getActualTypeArguments()[0]));
		}
		return clz;
	}

	protected String getClassName() {
		return getClz().getName();
	}
}
