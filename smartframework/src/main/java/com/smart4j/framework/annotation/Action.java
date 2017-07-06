package com.smart4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午8:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 请求类型
     */
    String value();
}
