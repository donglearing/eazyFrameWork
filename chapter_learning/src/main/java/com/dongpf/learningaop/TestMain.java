package com.dongpf.learningaop;

import com.dongpf.dosomething.controller.CustomerController;
import com.smart4j.framework.HelperLoader;
import com.smart4j.framework.helper.BeanHelper;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午8:38
 */

public class TestMain {

    public static void main(String[] args){
        HelperLoader.init();
        System.out.println(BeanHelper.getBeanMap().size());


        CustomerController customerController = (CustomerController)BeanHelper.getBeanMap().get(CustomerController.class);

        customerController.index();
//        customerController.index2();
//        customerController.index3();
    }



}
