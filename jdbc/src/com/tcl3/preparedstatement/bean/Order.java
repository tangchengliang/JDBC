package com.tcl3.preparedstatement.bean;

import java.sql.Date;

public class Order {
    private int orderID;
    private String orderName;
    private Date orderDate;

    public Order() {
    }

    public Order(int orderID, String orderName, Date orderDate) {
        this.orderID = orderID;
        this.orderName = orderName;
        this.orderDate = orderDate;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderName='" + orderName + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
