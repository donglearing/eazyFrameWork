package com.smart4j.framework.proxy;


/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午6:04
 */

public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
