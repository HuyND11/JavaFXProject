package com.example.studentproject.Modles;

public class Student {

    private int id;
    private String name;
    private Class studentClass;
    private Float score;
    private String address;
    private String phone;

    public Student(int id, String name, Class studentClass, Float score, String address, String phone) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.score = score;
        this.address = address;
        this.phone = phone;
    }

    public Student(String name, Class studentClass, Float score, String address, String phone) {
        this.name = name;
        this.studentClass = studentClass;
        this.score = score;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getClassName() {return studentClass.getClassName();}
}
