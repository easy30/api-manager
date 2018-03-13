package com.cehome.apimanager.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonMeta {

	/**
	 * 字段类型
	 *
	 */
	public enum JavaType {
		NUMBER(1, "number"), STRING(2, "string"), BOOLEAN(3, "boolean"), OBJECT(4, "object"), ARRAY(5, "array");

		public static Map<String, String> MAP;
		public static List<MetaKv> KVS;

		static {
			MAP = new HashMap<String, String>(JavaType.values().length);
			KVS = new ArrayList<MetaKv>(JavaType.values().length);
			for (JavaType javaType : JavaType.values()) {
				MAP.put(String.valueOf(javaType.getCode()), javaType.getDesc());
				KVS.add(new MetaKv(javaType.getCode(), javaType.getDesc()));
			}
		}

		public static String findDescByCode(Integer code) {
			if (null == code || -1 == code)
				return "";
			return findDescByCode(String.valueOf(code));
		}

		public static String findDescByCode(String code) {
			String desc = MAP.get(code);
			if (null == desc) {
				desc = "未知";
			}
			return desc;
		}

		private final int code;
		private final String desc;

		JavaType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}
}
