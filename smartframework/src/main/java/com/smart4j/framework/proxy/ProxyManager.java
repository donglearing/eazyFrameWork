package com.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午7:15
 */

public class ProxyManager {
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, o, method, methodProxy, objects, proxyList).doProxyChain();
            }
        });
    }
}
