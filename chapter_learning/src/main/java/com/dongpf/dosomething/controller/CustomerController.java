package com.dongpf.dosomething.controller;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午8:46
 */

import com.dongpf.dosomething.model.Customer;
import com.dongpf.dosomething.service.CustomerService;
import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.bean.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 处理客户管理相关请求
 *
 * @author alone
 *
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    /**
     * 进入 客户列表 界面
     *
     * @param param
     * @return
     */
//    @Action("get:/customer")
    public void index() {
        customerService.getCustomerList();
    }

    public void index2() {
        customerService.getCustomerList();
    }

    public void index3() {
        customerService.getCustomerList();
    }


//    /**
//     * 显示 客户基本 信息
//     *
//     * @param param
//     * @return
//     */
//    @Action("get:/customer_show")
//    public View show(Param param) {
//        long id = param.getLong("id");
//        Customer customer = customerService.getCustomer(id);
//        return new View("customer_show.jsp").addModel("customer", customer);
//    }
//
//    /**
//     * 进入 创建客户 界面
//     *
//     * @param param
//     * @return
//     */
//    @Action("get:/customer_create")
//    public View create() {
//
//        String permissionName = PermissionConstant.PERMISSION_CUSTOMER_CREATE;
//
//        try {
//            SecurityHelper.hasPermisson(permissionName);
//            return new View("customer_create.jsp");
//        } catch (AuthzException e) {
//            LOGGER.error("currentUser don't has " + permissionName + " permission");
//            return new View("/");
//        }
//    }
//
//    /**
//     * 处理 创建客户 请求
//     *
//     * @param param
//     * @return
//     */
//    @Action("post:/customer_create")
//    public Data createSubmit(Param param) {
//        Map<String, Object> fieldMap = param.getFieldMap();
//        FileParam fileParam = param.getFile("photo");
//        boolean result = customerService.createCustomer(fieldMap, fileParam);
//        return new Data(result);
//    }
//
//    /**
//     * 进入 编辑客户 界面
//     *
//     * @param param
//     * @return
//     */
//    @Action("get:/customer_edit")
//    public View edit(Param param) {
//        long id = param.getLong("id");
//        Customer customer = customerService.getCustomer(id);
//        return new View("customer_edit.jsp").addModel("customer", customer);
//    }
//
//    /**
//     * 处理 编辑客户 请求
//     *
//     * @param param
//     * @return
//     */
//    @Action("put:/customer_edit")
//    public Data editSubmit(Param param) {
//        long id = param.getLong("id");
//        Map<String, Object> fieldMap = param.getFieldMap();
//        boolean result = customerService.updateCustomer(id, fieldMap);
//        return new Data(result);
//    }
//
//    /**
//     * 处理 删除客户 请求
//     *
//     * @param param
//     * @return
//     */
//    @Action("delete:/customer_delete")
//    public Data delete(Param param) {
//        long id = param.getLong("id");
//        boolean result = customerService.deleteCustomer(id);
//        return new Data(result);
//    }

}

