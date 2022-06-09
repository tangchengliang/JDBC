package com.tcl6.transaction;

import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class TransactionTest {

    // ===================未考虑转账失败的操作=====================
    @Test
    public void testV1(){
        String sql = "update user_table set balance = balance - 100 where user = ?";
        updateV1(sql, "AA");
        // 存在问题   模拟网络异常: AA-100成功，网络错误导致 BB+100失败
        // System.out.println(10/0);
        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        updateV1(sql2, "BB");
    }

    // 增删改统一操作 Version 1.0
    public void updateV1(String sql, Object ... args){
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

    // ===================考虑转账失败的操作V2=====================

    /**
     *  1.提前建立连接
     *  2.关闭自动提交
     *  3. sql操作
     *  4.都操作完成再提交，否则回滚
     *  5.最后关闭连接,关闭资源（conn在test里关闭，ps在方法里关闭）
     *  6.恢复DML操作的自动提交功能
     */
    @Test
    public void testV2() throws Exception {
        Connection conn = null;
        try {
            // 1.提前建立连接
            conn = JDBCUtils.getConnection();
            // 2.关闭自动提交
            conn.setAutoCommit(false);
            // 3. sql操作
            String sql = "update user_table set balance = balance - 100 where user = ?";
            updateV2(conn, sql, "AA");
//             System.out.println(10/0);
            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            updateV2(conn, sql2, "BB");
            // 操作完成，提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 若有异常，则回滚事务
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException el) {
                el.printStackTrace();
            }
        }finally {
            // 恢复自动提交的功能
            try{
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            // 关闭连接资源
            JDBCUtils.closeResource(conn, null);
        }
    }
    public void updateV2(Connection conn, String sql, Object ... args){
        PreparedStatement ps = null;
        try {
            // 1.返回PrepareStatement的实例
            ps = conn.prepareStatement(sql);
            // 2.填充占位符
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            // 4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭ps资源
            JDBCUtils.closeResource(null,ps);
        }
    }
}
