package com.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午5:39
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */

    Class<? extends Annotation> value();

}
