package com.tcl3.preparedstatement.crud;

import com.tcl3.preparedstatement.bean.Customer;
import com.tcl3.preparedstatement.bean.Order;
import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用preparedStatement实现针对   -不同表-   的通用查询操作
 */
public class PreparedStatementQueryTest {
    @Test
    public void testGetInstance(){
        String sql1 = "select id,name,email,birth from customers where id = ?";
        Customer customer = getInstance(Customer.class, sql1, 1);
        System.out.println(customer);

        String sql2 = "select order_id orderID,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql2, 1);
        System.out.println(order);
    }

    @Test
    public void testGetList(){
        String sql1 = "select id,name,email,birth from customers where id <= ?";
        List<Customer> cusList = getForList(Customer.class, sql1, 5);
        System.out.println(cusList);

        String sql2 = "select order_id orderID,order_name orderName,order_date orderDate from `order` where order_id <= ?";
        List<Order> orderList = getForList(Order.class, sql2, 5);
        System.out.println(orderList);
    }

    // 查询不同表的通用方法,返回一条记录
    public <T> T getInstance(Class<T> clazz, String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 建立连接
            conn = JDBCUtils.getConnection();
            // 预编译sql对象
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            // 执行sql语句
            rs = ps.executeQuery();
            // 对结果进行输出
            // 获取元数据
            ResultSetMetaData metaData = rs.getMetaData();
            // 获取列数
            int countColumn = metaData.getColumnCount();
            // 遍历
            if (rs.next()) {
                T t = clazz.newInstance();
                //处理结果中的每一列
                for (int i = 0; i < countColumn; i++) {
                    // 获取元数据名称(别名)
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    // 获取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 利用反射 封装数据给t
                    Field filed = clazz.getDeclaredField(columnLabel);
                    filed.setAccessible(true);
                    filed.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    // 查询不同表的通用方法,返回一条记录
    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 建立连接
            conn = JDBCUtils.getConnection();
            // 预编译sql对象
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            // 执行sql语句
            rs = ps.executeQuery();
            // 对结果进行输出
            // 获取元数据
            ResultSetMetaData metaData = rs.getMetaData();
            // 获取列数
            int countColumn = metaData.getColumnCount();
            // 遍历
            // 创建集合对象
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.newInstance();
                //处理结果中的每一列
                for (int i = 0; i < countColumn; i++) {
                    // 获取元数据名称(别名)
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    // 获取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 利用反射 封装数据给t
                    Field filed = clazz.getDeclaredField(columnLabel);
                    filed.setAccessible(true);
                    filed.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}
