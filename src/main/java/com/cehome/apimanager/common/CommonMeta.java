package com.cehome.apimanager.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonMeta {

    /**
     * 字段类型
     */
    public enum FieldType {
        DATE(0, "date"),
        NUMBER(1, "number"),
        STRING(2, "string"),
        BOOLEAN(3, "boolean"),
        OBJECT(4, "object"),
        ARRAY(5, "array"),
        ARRAY_NUMBER(6, "array[number]"),
        ARRAY_STRING(7, "array[string]"),
        ARRAY_BOOLEAN(8, "array[boolean]"),
        ARRAY_OBJECT(9, "array[object]");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(FieldType.values().length);
            KVS = new ArrayList<MetaKv>(FieldType.values().length);
            for (FieldType javaType : FieldType.values()) {
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

        FieldType(int code, String desc) {
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

    /**
     * 必填项
     */
    public enum Required {
        YES(1, "是"),
        NO(2, "否");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(Required.values().length);
            KVS = new ArrayList<MetaKv>(Required.values().length);
            for (Required required : Required.values()) {
                MAP.put(String.valueOf(required.getCode()), required.getDesc());
                KVS.add(new MetaKv(required.getCode(), required.getDesc()));
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

        Required(int code, String desc) {
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

    /**
     * 请求类型
     */
    public enum RequestType {
        GET(1, "get"),
        POST(2, "post");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(RequestType.values().length);
            KVS = new ArrayList<MetaKv>(RequestType.values().length);
            for (RequestType requestType : RequestType.values()) {
                MAP.put(String.valueOf(requestType.getCode()), requestType.getDesc());
                KVS.add(new MetaKv(requestType.getCode(), requestType.getDesc()));
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

        RequestType(int code, String desc) {
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

    /**
     * 接口状态
     */
    public enum Status {
        DOING(1, "开发中"),
        DONE(2, "已完成");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(Status.values().length);
            KVS = new ArrayList<MetaKv>(Status.values().length);
            for (Status status : Status.values()) {
                MAP.put(String.valueOf(status.getCode()), status.getDesc());
                KVS.add(new MetaKv(status.getCode(), status.getDesc()));
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

        Status(int code, String desc) {
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

    /**
     * 模块代码说明
     */
    public enum Module {
        DEPARTMENT(1, "部门"),
        PROJECT(2, "项目"),
        MODULE(3, "模块"),
        ACTION(4, "接口"),
        ENV(5, "系统环境"),
        DOMAIN(6, "服务管理"),
        ACTION_LOGIN(7, "服务认证");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(Module.values().length);
            KVS = new ArrayList<MetaKv>(Module.values().length);
            for (Module module : Module.values()) {
                MAP.put(String.valueOf(module.getCode()), module.getDesc());
                KVS.add(new MetaKv(module.getCode(), module.getDesc()));
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

        Module(int code, String desc) {
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

    /**
     * 日志操作类型
     */
    public enum OperateType {
        ADD(1, "添加"),
        DELETE(2, "删除"),
        UPDATE(3, "修改");

        public static Map<String, String> MAP;
        public static List<MetaKv> KVS;

        static {
            MAP = new HashMap<String, String>(OperateType.values().length);
            KVS = new ArrayList<MetaKv>(OperateType.values().length);
            for (OperateType operateType : OperateType.values()) {
                MAP.put(String.valueOf(operateType.getCode()), operateType.getDesc());
                KVS.add(new MetaKv(operateType.getCode(), operateType.getDesc()));
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

        OperateType(int code, String desc) {
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
