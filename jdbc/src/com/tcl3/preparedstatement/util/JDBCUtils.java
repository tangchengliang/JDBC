package com.tcl3.preparedstatement.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *  操作数据库的工具类
 */
public class JDBCUtils {
    /**
     * @Description 获取数据库链接
     * @return 连接
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        // 1.加载配置文件, 读取信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        // 读取数据
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        // 2.加载驱动
        Class.forName(driverClass);

        // 3.返回链接
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * @Description 关闭资源链接
     */
    public static void closeResource(Connection conn, PreparedStatement ps){
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @Description 关闭资源链接, 多了一个查询请求
     */
    public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
