package com.dongpf.dosomething.dao;

import com.smart4j.framework.helper.DatabaseHelper;
import com.smart4j.framework.util.CollectionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by dongpengfei
 * Date 17/7/6
 * Time 下午3:42
 */

public class BaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 查询对象的集合
     *
     * @param conn
     * @param entityClass
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
                                              Object... params) {
        List<T> entityList;
        try {
            Connection conn = DatabaseHelper.getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity list failure", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询一个对象
     *
     * @param entityClass
     * @param sql
     * @param params
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql,
                                    Object... params) {
        T entity;
        try {
            Connection conn = DatabaseHelper.getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity failure", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 查询并返回单个列值
     */
    public static <T> T query(String sql, Object... params) {
        T obj;
        try {
            Connection conn = DatabaseHelper.getConnection();
            obj = QUERY_RUNNER.query(conn, sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 查询并返回多个列值
     */
    public static <T> List<T> queryList(String sql, Object... params) {
        List<T> list;
        try {
            Connection conn = DatabaseHelper.getConnection();
            list = QUERY_RUNNER.query(conn, sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query list failure", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 查询并返回多个列值（具有唯一性, 即不能重复）
     */
    public static <T> Set<T> querySet(String sql, Object... params) {
        Collection<T> valueList = queryList(sql, params);
        return new LinkedHashSet<T>(valueList);
    }

    /**
     * 查询并返回数组
     */
    public static Object[] queryArray(String sql, Object... params) {
        Object[] resultArray;
        try {
            Connection conn = DatabaseHelper.getConnection();
            resultArray = QUERY_RUNNER.query(conn, sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array failure", e);
            throw new RuntimeException(e);
        }
        return resultArray;
    }

    /**
     * 查询并返回数组列表
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> resultArrayList;
        try {
            Connection conn = DatabaseHelper.getConnection();
            resultArrayList = QUERY_RUNNER.query(conn, sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array list failure", e);
            throw new RuntimeException(e);
        }
        return resultArrayList;
    }

    /**
     * 查询并返回结果集映射（列名 => 列值）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> resultMap;
        try {
            Connection conn = DatabaseHelper.getConnection();
            resultMap = QUERY_RUNNER.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map failure", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    /**
     * 查询并返回结果集映射列表（列名 => 列值）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> resultMapList;
        try {
            Connection conn = DatabaseHelper.getConnection();
            resultMapList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map list failure", e);
            throw new RuntimeException(e);
        }
        return resultMapList;
    }

    /**
     * 执行更新
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        int row = 0;
        try {
            Connection conn = DatabaseHelper.getConnection();
            row = QUERY_RUNNER.update(conn, sql, params);
        } catch (Exception e) {
            LOGGER.error("execute update failure", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return row;
    }

    /**
     * 插入对象
     *
     * @param fieldMap
     * @return
     */
    public static <T> boolean insertEntity(Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }
        String sql = "INSERT INTO tb_customer ";
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + "VALUES " + values;
        System.out.println(sql);
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新对象
     *
     * @param id
     * @param fieldMap
     * @return
     */
    public static <T> boolean updateEntity(Long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }
        String sql = "UPDATE tb_customer SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";
        System.out.println(sql);
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    public static <T> boolean deleteEntity(Long id) {
        String sql = "DELETE FROM tb_customer WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行sql语句
     *
     * @param filePath
     */
    public static void executeSqlFile(String filePath) {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (Exception e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

}
