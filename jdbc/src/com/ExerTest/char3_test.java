package com.ExerTest;

import com.tcl3.preparedstatement.crud.PreparedStatementQueryTest;
import com.tcl3.preparedstatement.crud.PreparedStatementUpdateTest;
import org.junit.Test;

import java.util.Scanner;


public class char3_test {
    /**
     * 1.往customer插入一条数据
     */
    @Test
    public void insert_value() {
        String sql = "insert into customers(name, email, birth) values(?,?,?)";
        PreparedStatementUpdateTest psut = new PreparedStatementUpdateTest();
        psut.update(sql, "伽罗", "jialuo@qq.com", "1997-07-01");
    }

    /**
     * 2.建立一个表格 examstudent
     */
    @Test
    public void addExamStudent() {
        String sql = "CREATE TABLE `examstudent` (\n" +
                "  `FlowID` INT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `Type` INT(20) DEFAULT NULL,\n" +
                "  `IDCard` VARCHAR(18) DEFAULT NULL,\n" +
                "  `ExamCard` VARCHAR(15) DEFAULT NULL,\n" +
                "  `StudentName` VARCHAR(20) DEFAULT NULL,\n" +
                "  `Location` VARCHAR(20) DEFAULT NULL,\n" +
                "  `Grade` INT(10) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`FlowID`)\n" +
                ") ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;";
        PreparedStatementUpdateTest psut = new PreparedStatementUpdateTest();
        psut.update(sql);

        // 向表中插入数据
        String sql_data = "insert into examstudent (`Type`,`IDCard`,`ExamCard`,`StudentName`,`Location`,`Grade`) values(?,?,?,?,?,?)";
        psut.update(sql_data, 4, "123", "456", "tcl", "zj", 100);
    }

    @Test
    public void queryStudent() {
        Scanner sc = new Scanner(System.in);
        String choose = "";
        while (true) {
            System.out.println("======================");
            System.out.println("请选择输入类型");
            System.out.println("a:准考证号");
            System.out.println("b:身份证号");
            choose = sc.nextLine();
            if (choose.equalsIgnoreCase("a")) {
                System.out.println("请输入准考证号：");
                String examCard = sc.nextLine();
                String sql = "select * from examstudent where ExamCard = ?";
                PreparedStatementQueryTest psqt = new PreparedStatementQueryTest();
                examStudent data = psqt.getInstance(examStudent.class, sql, examCard);
                if(data==null){
                    System.out.println("查无此人，请重新进入程序！");
                }else {
                    System.out.println(data);
                    break;
                }
            } else if(choose.equals("b")){
                System.out.println("请输入身份证号：");
                String idCard = sc.nextLine();
                String sql = "select * from examstudent where IDCard = ?";
                PreparedStatementQueryTest psqt = new PreparedStatementQueryTest();
                examStudent data = psqt.getInstance(examStudent.class, sql, idCard);
                if(data==null){
                    System.out.println("查无此人，请重新进入程序！");
                }else {
                    System.out.println(data);
                    break;
                }
            }else {
                System.out.println("输入有误，请重新输入！！！");
            }
        }
    }

    @Test
    public void removeStudent() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入删除的考号：");
            String examCard = sc.nextLine();
            String sql_query = "select * from examstudent where ExamCard = ?";
            PreparedStatementQueryTest psqt = new PreparedStatementQueryTest();
            examStudent data = psqt.getInstance(examStudent.class, sql_query, examCard);
            if (data == null) {
                System.out.println("查无此人，请重新进入程序！");
            } else {
                String sql_remove = "delete from examstudent where ExamCard = ?";
                PreparedStatementUpdateTest psut = new PreparedStatementUpdateTest();
                psut.update(sql_remove, examCard);
                System.out.println("删除成功");
                break;
                // 注意通过执行方法，让其返回修改了几行，即可判断是否修改成功，这里不在演示
            }
        }
    }
}
