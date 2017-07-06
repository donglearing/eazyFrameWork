package com.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.util.CollectionUtil;
import com.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午8:59
 */

public class IocHelper {
    static {
        //获取所有的Bean类与Bean实例之间的映射关系（简称Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历beanMap
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从benaMap中获取bean类与bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean类定义的所有成员变量（简称，Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历 Bean Field
                    for (Field beanField : beanFields) {
                        //判断当前Bean Field 是否带有Inject 注解
                        if(beanField.isAnnotationPresent(Inject.class)) {
                            //在Bean Map中获取Bean Field 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                //通过反射初始化Bean Field值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }

            }
        }
    }
}
