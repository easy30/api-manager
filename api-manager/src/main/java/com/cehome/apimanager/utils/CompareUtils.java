package com.cehome.apimanager.utils;

import com.cehome.apimanager.common.FiledDesc;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CompareUtils {
    /**
     * 返回两个对象中的字段值的变化
     *
     * @param target 被比较的对象
     * @param source 用来比较的对象
     * @return
     */
    public static String compareFieldDiff(Object target, Object source) {
        StringBuffer compareResult = new StringBuffer();
        if (target == null && source == null) {
            return compareResult.toString();
        }
        if ((target == null && source != null) || (target != null && source == null)) {
            return compareResult.toString();
        }
        Class clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                FiledDesc filedDesc = field.getAnnotation(FiledDesc.class);
                if(filedDesc == null){
                    continue;
                }
                String desc = filedDesc.desc();
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object targetValue = getMethod.invoke(target);
                Object sourceValue = getMethod.invoke(source);
                if (targetValue == null && sourceValue == null) {
                    continue;
                }
                if ((targetValue == null && sourceValue != null) || (targetValue != null && sourceValue == null)) {
                    compareResult.append(desc).append("：").append(targetValue).append(" ------> ").append(sourceValue).append("\n");
                }

                if (!targetValue.toString().equals(sourceValue.toString())) {
                    compareResult.append(desc).append("：").append(targetValue).append(" ------> ").append(sourceValue).append("\n");
                }
            } catch (Exception e) {
            }
        }
        String result = compareResult.toString();
        if("".equals(result)){
            result = "内容未变化";
        }
        return result;
    }
}
