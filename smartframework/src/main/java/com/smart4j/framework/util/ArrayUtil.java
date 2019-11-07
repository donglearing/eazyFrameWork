package com.smart4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 上午10:25
 */

public class ArrayUtil {
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isNotEmpty(array);
    }






    
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
