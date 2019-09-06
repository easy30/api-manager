package com.cehome.apimanager.utils;

import org.springframework.util.AntPathMatcher;

/**
 * 用来匹配接口url
 * 
 * @author sunlei
 *
 */
public class UrlUtils {
	public static boolean match(String pattern, String path) {
		AntPathMatcher apm = new AntPathMatcher();
		return apm.match(pattern, path);
	}
}
