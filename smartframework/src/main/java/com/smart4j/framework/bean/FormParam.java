package com.smart4j.framework.bean;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午3:11
 */

public class FormParam {
    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
