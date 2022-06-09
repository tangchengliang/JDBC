package com.tcl4.blob;

import com.tcl3.preparedstatement.bean.Customer;
import com.tcl3.preparedstatement.util.JDBCUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * 向表处理Blob数据
 */
public class BlobTest {

    // 插入一条 Blob数据
    @Test
    public void testInsert() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into customers(name, email, birth, photo)values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1,"小黄鸡");
        ps.setObject(2,"tcl@qq.com");
        ps.setObject(3,"1997-07-01");
        // 设置blob文件时
        FileInputStream is = new FileInputStream(new File("C:\\java_test\\JDBC\\jdbc\\datafile\\小黄鸡.jpg"));
        ps.setBlob(4,is);

        ps.execute();
        JDBCUtils.closeResource(conn,ps);
    }

    // 获取Blob字段
    @Test
    public void testQuery() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream os = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,22);
            rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer cust = new Customer(id,name,email,birth);

                // 读取Blob类型的字段
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                os = new FileOutputStream("download.jpg");
                byte[] buffer = new byte[1024];
                int len=0;
                while ((len = is.read(buffer))!=-1){
                    os.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            JDBCUtils.closeResource(conn,ps,rs);
        }
    }
}
