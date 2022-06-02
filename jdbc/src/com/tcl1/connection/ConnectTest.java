package com.tcl1.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectTest {

    // 方式一
    @Test
    public void testConnection1() throws SQLException {
        //1.提供java.sql.Driver接口实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();

        //2.提供url，指明具体操作的数据
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";

        //3.提供Properties的对象，指明用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        //4.调用driver的connect()，获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    // 方式二, 方式一代码中显式出现了第三方数据库的API（Driver）
    //说明：相较于方式一，这里使用反射实例化Driver，不在代码中体现第三方数据库的API。体现了面向接口编程思想。
    @Test
    public void testConnection2() throws Exception {
        // 1.获取Driver实现类对象，使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        //2.提供url，指明具体操作的数据
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";

        //3.提供Properties的对象，指明用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        //4.调用driver的connect()，获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    // 方式三, 使用DriverManager实现数据库的连接。体会获取连接必要的4个基本要素。
    @Test
    public void testConnection3() throws Exception {
        //1.数据库连接的4个基本要素：
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "123456";
        String driverName = "com.mysql.jdbc.Driver";

        // 2.实例化Driver
        Class clazz = Class.forName(driverName);
        Driver driver = (Driver)clazz.newInstance();

        // 3.注册驱动
        DriverManager.registerDriver(driver);

        // 4.获取链接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    // 方式四, 在三上的优化
    // 不必显式的注册驱动了。因为在DriverManager的源码中已经存在静态代码块(随着类的加载就执行)，实现了驱动的注册。
    @Test
    public void testConnection4() throws Exception {
        //1.数据库连接的4个基本要素：
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "123456";
        String driverName = "com.mysql.jdbc.Driver";

        // 2.实例化Driver
        Class.forName(driverName);
        // 相较于方式三，可以省略下面操作，
//        Driver driver = (Driver)clazz.newInstance();
//        // 3.注册驱动
//        DriverManager.registerDriver(driver);

        // 4.获取链接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    // 最终版，将数据库链接的基本信息什么在配置文件中
    // ①实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码
    // ②如果修改了配置信息，省去重新编译的过程。
    @Test
    public void testConnection5() throws Exception {
        // 1.加载配置文件, 读取信息
        // 类的加载器
        InputStream is = ConnectTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        // 读取数据
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        // 2.加载驱动
        Class.forName(driverClass);

        // 3.获取链接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

}
