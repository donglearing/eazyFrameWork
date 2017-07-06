package com.smart4j.framework.proxy;

import com.smart4j.framework.annotation.Transaction;
import com.smart4j.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午7:44
 */

public class TransactionProxy  implements Proxy {
    private static final Logger log = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                log.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.debug("rollback transaction");
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
