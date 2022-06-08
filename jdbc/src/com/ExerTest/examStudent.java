package com.ExerTest;

public class examStudent {
    private int FlowID;
    private int Type;
    private String IDCard;
    private String ExamCard;
    private String StudentName;
    private String Location;
    private int Grade;

    public examStudent() {
    }

    public examStudent(int flowID, int type, String IDCard, String examCard, String studentName, String location, int grade) {
        FlowID = flowID;
        Type = type;
        this.IDCard = IDCard;
        ExamCard = examCard;
        StudentName = studentName;
        Location = location;
        Grade = grade;
    }

    public int getFlowID() {
        return FlowID;
    }

    public int getType() {
        return Type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public String getExamCard() {
        return ExamCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getLocation() {
        return Location;
    }

    public int getGrade() {
        return Grade;
    }

    public void setFlowID(int flowID) {
        FlowID = flowID;
    }

    public void setType(int type) {
        Type = type;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public void setExamCard(String examCard) {
        ExamCard = examCard;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        System.out.println("********查询结果********");
        return "examStudent{" +
                "FlowID=" + FlowID +
                "\n Type=" + Type +
                "\n IDCard='" + IDCard + '\'' +
                "\n ExamCard='" + ExamCard + '\'' +
                "\n StudentName='" + StudentName + '\'' +
                "\n Location='" + Location + '\'' +
                "\n Grade=" + Grade +
                '}';
    }
}
