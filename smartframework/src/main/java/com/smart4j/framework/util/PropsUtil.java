package com.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午3:50
 */

public class PropsUtil {
    private static final Logger log = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (Exception e) {
            log.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    public static String getString(Properties props, String key, String defaultValue){
        String value = defaultValue;
        if(props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if(props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }

}
