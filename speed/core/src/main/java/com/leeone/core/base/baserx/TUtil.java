package com.leeone.core.base.baserx;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类转换初始化
 */
public class TUtil {
    private static <T> T getRealT(Class<?> clazz, int i) {
        try {
            Type genericSuperclass = clazz
                    .getGenericSuperclass();
            return ((Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            return getRealT(clazz.getSuperclass(), i);
        }
        return null;
    }

    public static <T> T getT(Object o, int i) {
        Class<?> aClass = o.getClass();
        return getRealT(aClass, i);
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

