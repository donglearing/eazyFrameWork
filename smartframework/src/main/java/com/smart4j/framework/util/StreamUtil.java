package com.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 上午11:27
 */

public class StreamUtil {
    private static final Logger log = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     *
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer = new byte[4 * 1024];
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
