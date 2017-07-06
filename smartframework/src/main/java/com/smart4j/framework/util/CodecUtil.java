package com.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午2:45
 */

public class CodecUtil {
    private static final Logger log = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
