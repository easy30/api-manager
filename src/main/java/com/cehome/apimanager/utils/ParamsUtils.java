package com.cehome.apimanager.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;

import java.util.*;

/**
 * 参数列表工具类
 */
public class ParamsUtils {
    public static void convertJsonToRows(JSONObject jsonObject, JSONArray rows) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                value = "";
            }
            String className = value.getClass().getName();
            JSONObject row = new JSONObject();
            row.put("name", entry.getKey());
            row.put("rule", "");
            row.put("desc", "");
            if ("java.util.LinkedHashMap".equals(className)) {
                JSONArray array = new JSONArray();
                row.put("child", array);
                row.put("type", CommonMeta.FieldType.OBJECT.getCode());
                convertJsonToRows(new JSONObject((LinkedHashMap)value), array);
            } else if ("java.util.ArrayList".equals(className)) {
                JSONArray array = new JSONArray();
                Set<Integer> types = new HashSet<>();
                row.put("defaultVal", "");
                row.put("child", array);
                ArrayList arrayValue = (ArrayList) value;
                for (int i = 0; i < arrayValue.size(); i++) {
                    Object o = arrayValue.get(i);
                    JSONObject object = new JSONObject();
                    object.put("array[" + i + "]", o);
                    convertJsonToRows(object, array);
                    types.add(array.getJSONObject(i).getInteger("type"));
                }
                if (types.size() == 1) {
                    Integer type = Integer.valueOf(types.toArray()[0].toString());
                    row.put("type", type + 5);
                } else {
                    row.put("type", CommonMeta.FieldType.ARRAY.getCode());
                }
            } else if ("java.lang.String".equals(className)) {
                row.put("type", CommonMeta.FieldType.STRING.getCode());
                row.put("defaultVal", value);
            } else if ("java.lang.Integer".equals(className)) {
                row.put("type", CommonMeta.FieldType.NUMBER.getCode());
                row.put("defaultVal", Integer.valueOf(value.toString()));
            } else if ("java.lang.Long".equals(className)) {
                row.put("type", CommonMeta.FieldType.NUMBER.getCode());
                row.put("defaultVal", Long.valueOf(value.toString()));
            } else if ("java.lang.Double".equals(className)) {
                row.put("type", CommonMeta.FieldType.NUMBER.getCode());
                row.put("defaultVal", Double.valueOf(value.toString()));
            } else if ("java.lang.Float".equals(className)) {
                row.put("type", CommonMeta.FieldType.NUMBER.getCode());
                row.put("defaultVal", Float.valueOf(value.toString()));
            } else if ("java.lang.Boolean".equals(className)) {
                row.put("type", CommonMeta.FieldType.BOOLEAN.getCode());
                row.put("defaultVal", value);
            }
            rows.add(row);
        }
    }

    public static void convertToRows(JSONArray jsonArray, List<Map<String, Object>> rows) {
        for(int i = 0; i < jsonArray.size(); i ++){
            JSONObject paramsInfo = new JSONObject();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray children = jsonObject.getJSONArray("child");
            if(children != null){
                convertToRows(children, rows);
            } else {
                paramsInfo.put("name", jsonObject.getString("name"));
                paramsInfo.put("type", CommonMeta.FieldType.findDescByCode(jsonObject.getString("type")));
                paramsInfo.put("required", CommonMeta.Required.findDescByCode(jsonObject.getString("required")));
                paramsInfo.put("remark", jsonObject.getString("desc"));
                rows.add(paramsInfo);
            }
        }
    }
}
