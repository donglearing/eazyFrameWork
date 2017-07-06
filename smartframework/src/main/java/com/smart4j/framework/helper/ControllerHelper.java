package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.bean.Handler;
import com.smart4j.framework.bean.Request;
import com.smart4j.framework.util.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 上午10:37
 */

public class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static{
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> cls : controllerClassSet) {
                Method[] methods = cls.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(cls, method);
                                    //��ʼ��Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 通过方法名称和请求路径名称,找到对应的处理方法.
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }
}
