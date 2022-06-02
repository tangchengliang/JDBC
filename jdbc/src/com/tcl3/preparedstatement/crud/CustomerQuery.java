package com.tcl3.preparedstatement.crud;

import com.tcl3.preparedstatement.bean.Customer;
import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对customers表格查询操作
 */
public class CustomerQuery {

    @Test
    public void test() throws Exception {
        String sql = "select id,name,email,birth from customers where id = ?";
        Customer result = query(sql, 1);
        System.out.println(result);
    }

    // 针对 customer通用查询操作
    public Customer query(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            //1. 建立连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，得到prepareStatement对象
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //4. 执行executeQuery()，得到结果集：ResultSet
            rs = ps.executeQuery();
            // 5.得到结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 6.1通过ResultSetMetaData得到columnCount,columnLabel；通过ResultSet得到列值
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Customer cust = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);
                    // 获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);
                    // 给cust对象指定columnName属性，赋值为columnValue，通过反射实现
                    Field filed = Customer.class.getDeclaredField(columnName);
                    filed.setAccessible(true);
                    filed.set(cust, columValue);
                }
                return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);

            // 执行，并返回结果集
            rs = ps.executeQuery();

            // 打印结果集，使用next()
            if (rs.next()) {
                // 获取当前数据的各个字段的值
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);

                // 方式1 直接打印
                //            System.out.println("id="+id+",name="+name+",email="+email+",birth="+birth);
                //方式二 集合
                //            Object[] data = new Object[]{id, name, email, birth};
                // 方式3 封装成一个类 ORM思想
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}
