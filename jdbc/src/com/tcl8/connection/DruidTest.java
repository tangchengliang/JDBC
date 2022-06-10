package com.tcl8.connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {

    @Test
    public void getConnection() throws Exception {
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

        pros.load(is);
        DataSource ds = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = ds.getConnection();
        System.out.println(conn);

    }
}
