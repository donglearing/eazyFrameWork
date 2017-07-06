package com.smart4j.framework;

import com.smart4j.framework.helper.*;
import com.smart4j.framework.util.ClassUtil;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 上午11:09
 */

public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
