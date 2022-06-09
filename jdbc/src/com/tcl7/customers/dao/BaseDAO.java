package com.tcl7.customers.dao;

import com.tcl3.preparedstatement.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个用来被继承的对数据库进行基本操作的Dao
 * 封装了数据表的基本操作
 * 抽象类，不能被实例化
 * @param <T>
 */
public abstract class BaseDAO<T> {

    // 通用更新操作
    public int update(Connection conn, String sql, Object ... args){
        PreparedStatement ps = null;
        try {
            // 1.返回PrepareStatement的实例
            ps = conn.prepareStatement(sql);
            // 2.填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            // 3.执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭ps资源
            JDBCUtils.closeResource(null,ps);
        }
        return 0;
    }
    // 查询不同表的通用方法,返回一条记录
    public <E> E getInstance(Connection conn, Class<E> clazz, String sql, Object... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
                E e = clazz.newInstance();
                //处理结果中的每一列
                for (int i = 0; i < countColumn; i++) {
                    // 获取元数据名称(别名)
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    // 获取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 利用反射 封装数据给t
                    Field filed = clazz.getDeclaredField(columnLabel);
                    filed.setAccessible(true);
                    filed.set(e, columnValue);
                }
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    // 查询不同表的通用方法,返回多条记录, 考虑事务
    public List<T> getForList(Connection conn, Class<T> clazz, String sql){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 预编译sql对象
            ps = conn.prepareStatement(sql);
            // 执行sql语句
            rs = ps.executeQuery();
            // 对结果进行输出
            // 获取元数据
            ResultSetMetaData metaData = rs.getMetaData();
            // 获取列数
            int countColumn = metaData.getColumnCount();
            // 遍历
            // 创建集合对象
            ArrayList<T> list = new ArrayList<T>();
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
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    // 特殊需求，例如查count
    public <E> E getValue(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            if(rs.next()){
                // 返回一行一列数据，故，直接return
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }
}
