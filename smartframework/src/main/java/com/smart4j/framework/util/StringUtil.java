package com.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午3:55
 */

public class StringUtil {
    public static final String SEPARATOR = String.valueOf((char) 29);

    public static boolean isEmpty(String str) {
        if(str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String[] splitString(String string, String regex) {
        String[] result = string.split(regex);
        return result;
    }
}
