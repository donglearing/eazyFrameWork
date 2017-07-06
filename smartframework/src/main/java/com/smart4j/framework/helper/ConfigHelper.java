package com.smart4j.framework.helper;

import com.smart4j.framework.util.PropsUtil;

import java.util.Properties;
import com.smart4j.framework.ConfigConstant;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午3:49
 */

public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    public static int getAppUploadLimit() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT);
    }
}
