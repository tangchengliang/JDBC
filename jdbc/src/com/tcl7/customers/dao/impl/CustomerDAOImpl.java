package com.tcl7.customers.dao.impl;

import com.tcl7.customers.beans.Customer;
import com.tcl7.customers.dao.BaseDAO;
import com.tcl7.customers.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 测试时，idea右键go to test 可以帮助创建
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        update(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        update(conn, sql, cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = (Customer) getInstance(conn, Customer.class, sql, id);
        return customer;
    }


    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> forList = getForList(conn, Customer.class, sql);
        return forList;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return (Long) getValue(conn,sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return (Date) getValue(conn,sql);
    }
}
