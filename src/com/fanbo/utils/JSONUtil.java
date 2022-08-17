package com.fanbo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * desc:  json工具
 *
 * @author FanBo
 */
public class JSONUtil {

    /**
     * 转义带斜线的json数据
     *
     * @param obj
     * @return
     */
    public static String getJson(String obj) {
        obj = obj.replace("\\", "");
        char[] array = obj.toCharArray();
        char[] charArray = new char[array.length - 2];

        for (int i = 1; i < array.length - 1; i++) {
            charArray[i - 1] = array[i];

        }
        return new String(charArray);
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(map.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, new Date(Long.parseLong(datetimestamp)));
                    }
                } else if (filedTypeName.equalsIgnoreCase("java.lang.Long")) {
                    field.set(obj, toLong(map.get(field.getName())));
                } else if (filedTypeName.equalsIgnoreCase("java.lang.Integer")) {
                    field.set(obj, toInteger(map.get(field.getName())));
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Long toLong(Object obj) {
        return (obj == null || "".equals(obj) || "null".equals(obj)) ? 0L : Long.valueOf(obj.toString());
    }

    public static Integer toInteger(Object obj) {
        return (obj == null || "".equals(obj) || "null".equals(obj)) ? 0 : Integer.valueOf(obj.toString());
    }

    public static String toString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, String> mapToMap(Map<String, Object> map) {
        if (null != map && map.size() > 0) {
            Map<String, String> rMap = new HashMap<String, String>();
            Set<String> keySet = map.keySet();
            Iterator<String> it = keySet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                rMap.put(key, toString(map.get(key)));
            }
            return rMap;
        } else {
            return null;
        }
    }

}
