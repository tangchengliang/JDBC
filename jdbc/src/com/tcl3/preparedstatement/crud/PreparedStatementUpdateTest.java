package com.tcl3.preparedstatement.crud;

import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/*
    增删改
 */
public class PreparedStatementUpdateTest {
    @Test
    public void test(){
        String sql = "delete from customers where id = ?";
        update(sql,3);
    }

    // 增删改统一操作
    public void update(String sql, Object ... args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1. 获取数据库链接
            conn = JDBCUtils.getConnection();
            // 2.返回PrepareStatement的实例
            ps = conn.prepareStatement(sql);
            // 3.填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            // 4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //5. 资源关闭
            JDBCUtils.closeResource(conn, ps);
        }
    }
    // 修改表的一条记录
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1. 获取数据库链接
            conn = JDBCUtils.getConnection();
            // 2.预编译sql语句，返回PrepareStatement的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            // 3.填充占位符
            ps.setObject(1, "千库唯有");
            ps.setObject(2,19);
            // 4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //5. 资源关闭
            JDBCUtils.closeResource(conn, ps);
        }

    }

    // 向customers表中添加一条记录
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
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

            // 3.获取链接
            conn = DriverManager.getConnection(url, user, password);
//        System.out.println(conn);
            // 4. 预编码sql语句，返回PreparedStatement实例
            String sql = "insert into customers(name,email,birth)values(?,?,?)"; // ??? 占位符
            ps = conn.prepareStatement(sql);

            // 5.填充占位符
            ps.setString(1, "伽罗");
            ps.setString(2, "jialuo@mail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1000-01-01");
            // 注意这里的包，不要导错了，是sql date的包
            ps.setDate(3,  new java.sql.Date(date.getTime()));

            // 6.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 7.资源关闭
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
    }
}
