package com.dongpf.dosomething.service;

import com.dongpf.dosomething.dao.BaseDao;
import com.dongpf.dosomething.model.Customer;
import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.annotation.Transaction;
import com.smart4j.framework.bean.FileParam;

import java.util.List;
import java.util.Map;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午8:47
 */


@Service
public class CustomerService {


    @Transaction
    public void getCustomerList() {
        System.out.println("getCustomerList------------------------------------over");
    }

    public void createCustomerList() {
        System.out.println("createCustomerList");
    }

    public void deleteCustomerList() {
        System.out.println("deleteCustomerList----------");
    }

    @Transaction
    public Customer getCustomer(Long id) {
        String sql = "SELECT * FROM tb_customer WHERE id = ?";
        return BaseDao.queryEntity(Customer.class, sql, id);
    }

    @Transaction
    public boolean createCustomer(Map<String, Object> fieldMap , FileParam fileParam) {
        boolean result = BaseDao.insertEntity(fieldMap);
//        if(result){
//            UploadHelper.uploadFile("/tmp/upload/", fileParam);
//        }
        return result;
    }

    @Transaction
    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return BaseDao.updateEntity(id, fieldMap);
    }

    @Transaction
    public boolean deleteCustomer(Long id) {
        return BaseDao.deleteEntity(id);
    }

}
