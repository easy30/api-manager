package com.cehome.apimanager.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseDao;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AmActionDao extends BaseDao<AmAction>{

	public List<AmAction> findUrlList() {
		return sqlSessionTemplate.selectList(getClassName() + ".findUrlList");
	}

	public List<Map<String, Integer>> countGroupByProject(AmActionQueryReqDto dto) {
		return sqlSessionTemplate.selectList(AmAction.class.getName() + ".countGroupByProject");
	}

	public Page<AmActionResDto > search(AmAction entity){
		String dataJson = JSON.toJSONString(entity);
		JSONObject dataObject = JSON.parseObject(dataJson, JSONObject.class);
		Page<AmActionResDto> page = new Page();
		Integer pageIndex = dataObject.getInteger("pageIndex");
		Integer pageSize = dataObject.getInteger("pageSize");
		page.buildPageInfo(pageIndex, pageSize);
		dataObject.put("pageOffset", page.getPageOffset());
		List<AmActionResDto > datas = sqlSessionTemplate.selectList(getClassName() + ".search", dataObject);
		Integer totalRecord =sqlSessionTemplate.selectOne(getClassName() + ".searchCount", dataObject);
		page.fillPage(datas, totalRecord);
		return page;
	}
}
