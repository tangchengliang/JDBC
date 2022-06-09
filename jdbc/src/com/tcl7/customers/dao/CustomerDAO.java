package com.tcl7.customers.dao;

import com.tcl7.customers.beans.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口，用来规范针对Customers表的操作
 */
public interface CustomerDAO {
    /**
     * 将cust对象添加到数据库中
     * @param conn
     * @param cust
     */
    void insert(Connection conn, Customer cust);

    /**
     * 针对指定的id，删除表中的一条记录
     * @param conn
     * @param cust
     */
    void deleteById(Connection conn, int id);

    /**
     * 针对内存中的对象，去修改数据表中指定的记录
     * @param conn
     * @param cust
     */
    void update(Connection conn, Customer cust);

    /**
     *  根据id查询一条记录
     * @param conn
     * @param id
     * @return Customer
     */
    Customer getCustomerById(Connection conn, int id);

    /**
     * 查询表中所有数据
     * @param conn
     * @return List<Customer>
     */
    List<Customer> getAll(Connection conn);

    /**
     *  返回数据表中的记录
     * @param conn
     * @return Long
     */
    Long getCount(Connection conn);

    /**
     * 返回最大生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
