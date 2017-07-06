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
 * Time 下午9:22
 */

@Aspect(value = Controller.class)
public class ControllerAspect2 extends AspectProxy {

    private static final Logger LOOGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        System.out.println("111----------董鹏飞-------------");
        System.out.println(String.format("111class: %s", cls.getName()));
        System.out.println(String.format("11method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        System.out.println(String.format("1111tend ime: %dms", System.currentTimeMillis()- begin));
        System.out.println("------------董鹏飞end-------------");
    }
}
