package com.cehome.apimanager.pdf;



import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDocumentVo implements DocumentVo{
	public Map<String, Object> fillDataMap(){

		DocumentVo vo = this.getDocumentVo();
		return JSON.parseObject(JSON.toJSONString(vo), HashMap.class);
	}

	private DocumentVo getDocumentVo() {
		return this;
	}

}
