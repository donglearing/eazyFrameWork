package com.smart4j.framework.helper;

import com.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午8:49
 */

public final class BeanHelper {
    /**
     * 定义Bean 映射（用于存放Bean类与Bean实例的映射关系）
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls, obj);
        }
    }

    /**
     * 获取Bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置bean实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);

    }
}
