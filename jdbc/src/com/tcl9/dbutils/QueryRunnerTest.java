package com.tcl9.dbutils;

import com.tcl3.preparedstatement.util.JDBCUtils;
import com.tcl7.customers.beans.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，它是对JDBC的简单封装
 */
public class QueryRunnerTest {
    // 添加
    @Test
    public void testInsert() throws SQLException {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getDruidConnection();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int insertCount = runner.update(conn, sql, "蔡徐坤","qq.com","1998-01-03");
            System.out.println(insertCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    // 查询
    @Test
    public void testQuery(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email from customers where id=?";
            // handler 来定义返回类型的类
            // 使用ResultSetHandler的实现类：BeanHandler,返回一条记录
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(conn, sql, handler, 20);
            System.out.println(customer);


            System.out.println("返回多条记录");
            String sql1 = "select id,name,email from customers where id<?";
            //使用ResultSetHandler的实现类：BeanListHandler,返回一个list
            BeanListHandler<Customer> listHandler = new BeanListHandler<>(Customer.class);
            List<Customer> customers = runner.query(conn, sql1, listHandler, 20);
            for(Customer cust: customers){
                System.out.println(cust);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    // 特殊查询 只有一行一列数据
    @Test
    public void testQuery2(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();

            conn = JDBCUtils.getConnection();
            String sql = "select count(*) from customers";
            ScalarHandler scalarHandler = new ScalarHandler();
            Object count = runner.query(conn, sql, scalarHandler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
}
