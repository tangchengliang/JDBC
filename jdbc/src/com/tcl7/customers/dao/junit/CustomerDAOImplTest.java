package com.tcl7.customers.dao.junit;

import com.tcl3.preparedstatement.util.JDBCUtils;
import com.tcl7.customers.beans.Customer;
import com.tcl7.customers.dao.impl.CustomerDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDAOImplTest {
    private CustomerDAOImpl dao = new CustomerDAOImpl();
    @Test
    public void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(1, "胜利", "jskjk@qq.com", new Date(42434L));
            dao.insert(conn, customer);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            dao.deleteById(conn, 23);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void update() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(1, "胜利", "jskjk@qq.com", new Date(42434L));
            dao.update(conn, customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void getCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = dao.getCustomerById(conn,1);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Customer> all = dao.getAll(conn);
            for(Customer cust:all){
                System.out.println(cust);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void getMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println(maxBirth);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }
}