package com.cehome.apimanager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class  ReadConfig{
	private static final Logger logger = LoggerFactory.getLogger(ReadConfig.class);

	private static Properties config = null;
	private Properties pro = null;
	static {
		if(config==null)
			config = init("config/config.properties");
	}

	public ReadConfig(){}
	public ReadConfig(String filePath){
		pro = init(filePath);
	}

	public static Properties init(String filePath){
		Properties p = null;
		InputStream inputStream = null;
		try{
			p = new Properties();

			inputStream = ReadConfig.class.getClassLoader().getResourceAsStream(
					filePath);
			p.load(inputStream);
		}
		catch(IOException e){
			logger.error("====================加载资源文件config.properties失败!====================",e);
		}finally{
			try{
				if(inputStream!=null){
					inputStream.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}

		}
		return p;
	}

	/**
	 * 读取配置key的值
	 * @param key
	 * @return value
	 */
	public static String get(String key){
		String value =	config.getProperty(key);
		if(value!=null)
			return value.trim();
		return value;
	}
}
