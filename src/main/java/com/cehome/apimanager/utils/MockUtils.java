package com.cehome.apimanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.alibaba.fastjson.JSON;

/**
 * mock工具类
 * 
 * @author sunlei
 *
 */
public class MockUtils {
	private static ScriptEngine engine = null;
	
	/**
	 * 根据mock模板返回mock数据
	 * 
	 * @param mockTemplate
	 * @return
	 */
	public static String buildMockData(String mockTemplate){
		if(engine == null){
			createScriptEngine();
		}
		Object mockData = new Object();
		if (engine instanceof Invocable) {
			Invocable invoke = (Invocable) engine;
			try {
				mockData = invoke.invokeFunction("mockData", JSON.parseObject(mockTemplate).toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mockData.toString();
	}
	
	private static synchronized void createScriptEngine(){
		if(engine != null){
			return;
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("js");
		InputStreamReader inputStreamReader = null;
		try {
			InputStream inputStream = new FileInputStream(new File(getJsPath()));
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			engine.eval(inputStreamReader);
			engine.eval("function mockData(mockTemplate) { "
						+ "	var jsonObject = JSON.parse(mockTemplate);"
						+ "	var mockData = Mock.mock(jsonObject);"
						+ " return JSON.stringify(mockData, null, 2);"
					  + "}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStreamReader != null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getJsPath(){
		URL resource = Thread.currentThread().getContextClassLoader().getResource("");
		String path = resource.getPath();
		return path + File.separator + "static/res/mock/mock-min.js";
	}
}
