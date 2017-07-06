package com.smart4j.framework;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongpengfei
 * Date 17/7/4
 * Time 下午5:17
 */

public class TestMain {
    public static void main(String[] args){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
//        urls.getClass().getMethod()
//        try {
////            CastUtil.class.getMethod("castString", Object.class).invoke(castUtil, "11111");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        //do some thing

//        Enumeration<URL>[] uls =  Thread.currentThread().getContextClassLoader().getResource("/Users/onlys/Downloads/cryptClient_v103_good.jar");

        List<File> files  = (List<File>) Arrays.asList(new File("/Users/onlys/Downloads").listFiles()).stream().
                filter(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory()).collect(Collectors.toList());

        System.out.println(files.size());
    }

}
