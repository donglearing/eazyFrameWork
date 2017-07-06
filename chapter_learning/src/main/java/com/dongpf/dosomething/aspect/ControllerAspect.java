package com.dongpf.dosomething.aspect;

import com.smart4j.framework.annotation.Aspect;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午9:03
 */

@Aspect(value = Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger LOOGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        System.out.println("----------begin-------------");
        System.out.println(String.format("class: %s", cls.getName()));
        System.out.println(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        System.out.println(String.format("time: %dms", System.currentTimeMillis()- begin));
        System.out.println("------------end-------------");
    }
}
