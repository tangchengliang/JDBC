package com.tcl3.preparedstatement.crud;

import com.tcl3.preparedstatement.bean.Order;
import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class OrderQuery {

    @Test
    public void test() throws Exception {
        String sql = "select order_id orderID,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order result = query(sql, 1);
        System.out.println(result);
    }
    public Order query(String sql, Object ...args) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1. 建立连接
            conn = JDBCUtils.getConnection();
            // 2.实例化preparedStatement
            ps = conn.prepareStatement(sql);
            // 3.填充定位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //4.执行
            rs = ps.executeQuery();

            //5.对结果操作
            //5.1 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //5.2 得到元数据长度
            int columnCount = rsmd.getColumnCount();
            //5.3遍历数据
            if(rs.next()){
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    // 获取属性名
                    String orderLabel = rsmd.getColumnLabel(i + 1);
                    // 获取列值
                    Object orderValue = rs.getObject(i+1);
                    // 反射
                    Field filed = Order.class.getDeclaredField(orderLabel);
                    filed.setAccessible(true);
                    filed.set(order, orderValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    // 先写一个普通查询
    @Test
    public void testQuery() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1. 建立连接
            conn = JDBCUtils.getConnection();
            //2.编写sql，实例化preparedStatement
            String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
            ps = conn.prepareStatement(sql);
            //3.填充定位符号
            ps.setObject(1,1);
            //4.执行
            rs = ps.executeQuery();
            if(rs.next()){
                int orderId = rs.getInt(1);
                String orderName = rs.getString(2);
                Date orderDate = rs.getDate(3);

                Order order = new Order(orderId, orderName, orderDate);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}
