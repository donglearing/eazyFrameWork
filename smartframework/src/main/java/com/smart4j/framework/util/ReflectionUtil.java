package com.smart4j.framework.util;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午8:41
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            log.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("invoke method failure", e);
        }
        return result;
    }

    /**
     * 设置成员变量
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }

}