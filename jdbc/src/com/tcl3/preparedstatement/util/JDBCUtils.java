package com.tcl3.preparedstatement.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.DataSources;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
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

    /**
     *  DBCP建立连接，为防止调用一次建立一个池子，使用静态代码块
     *
     */
    // 先声明一个连接池
    private static DataSource source;
    //静态代码块，随着类的运行而加载，追回执行一次
    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            pros.load(is);
            // 创建一个dbcp数据连接池
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDbcpConnection() throws SQLException {
        return source.getConnection();
    }

    /**
     *  Druid 连接
     *
     */
    public static DataSource source1;
    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            pros.load(is);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDruidConnection() throws SQLException {
        return source1.getConnection();
    }

}
