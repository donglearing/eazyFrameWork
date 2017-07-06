package com.smart4j.framework.helper;

import com.smart4j.framework.util.CollectionUtil;
import com.smart4j.framework.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dongpengfei
 * Date 17/7/5
 * Time 下午7:48
 */

public class DatabaseHelper {
    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    private static final BasicDataSource DATA_SOURCE;

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            log.error(e.getMessage(), e);
//        }
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
//        Connection conn = null;
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

//    public static void closeConnection() {
//        Connection conn = CONNECTION_HOLDER.get();
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                log.error(e.getMessage(), e);
//                throw new RuntimeException(e);
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entity;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return result;
    }

    public static int executeUpdate(String sql, Object... params) {
        int result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.update(conn, sql, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return result;
    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not insert entity: field is empty");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " values " +values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not update entity: fieldMap is empty");
            return false;
        }
        String sql = "update" + getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id = ?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id = ?";
        return executeUpdate(sql, id) == 1;
    }

    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            log.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                log.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                log.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
