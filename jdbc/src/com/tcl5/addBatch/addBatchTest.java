package com.tcl5.addBatch;

import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 批量操作
 */
public class addBatchTest {
    /**
     * 修改1： 使用 addBatch() / executeBatch() / clearBatch()
     * 修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
     *      ?rewriteBatchedStatements=true 写在配置文件的url后面
     *  修改3：使用更新的mysql 驱动：mysql-connector-java-5.1.37-bin.jar
     *  注意：需要下载8.0以上的版本
     */
    @Test
    public void testAddBatch() throws Exception {
        long start = System.currentTimeMillis();
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into goods(name)values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        conn.setAutoCommit(false);
        for(int i = 1;i <= 2000;i++){
            ps.setString(1, "name_" + i);
            //1.“攒”sql
            ps.addBatch();
            if(i % 500 == 0){
            //2.执行
                ps.executeBatch();
            //3.清空
                ps.clearBatch();
            }
        }
        conn.commit();
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start));//20000条：625
        //1000000条:14733
        JDBCUtils.closeResource(conn, ps);
    }
}
