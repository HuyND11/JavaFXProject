package com.example.studentproject.Connection;

import com.example.studentproject.Modles.Class;
import com.example.studentproject.Modles.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataConnect {
    private Connection conn;

    public static final String URL = "jdbc:mysql://localhost/student";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public DataConnect(){
        try {
            this.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.printf("Connect Successful");
        } catch (SQLException e) {
            System.out.printf("Error: " + e.toString());
        }
    }

    public ArrayList<Student> getStudent () {
        ArrayList<Student> list = new ArrayList<Student>();

        String sql = "SELECT std_info.id, std_info.classId, std_info.name, std_info.address, std_info.score, std_info.phone, class.className FROM students as std_info LEFT JOIN class ON std_info.classId = class.id ORDER BY std_info.id ASC";
        try {
            ResultSet result = conn.prepareStatement(sql).executeQuery();
            while(result.next()){

                Student std = new Student(
                        result.getInt("id"),
                        result.getString("name"),
                        new Class(result.getInt("classId"),result.getString("className")),
                        result.getFloat("score"),
                        result.getString("address"),
                        result.getString("phone")
                );
                list.add(std);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void deleteStudent (int id ) {
        String sql = "DELETE FROM students WHERE id =" + id;
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertStudent (Student std) {
        String sql = "INSERT INTO students (name, classId, address, score, phone) VALUES ('"+ std.getName() +"', "+ std.getStudentClass().getId() +", '"+ std.getAddress() +"', " + std.getScore() +",' "+ std.getPhone() +"')";
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent (int id, Student std) {
        String sql = "UPDATE students SET name ='" + std.getName() +"', classId = '" + std.getStudentClass().getId() + "', score = '" + std.getScore() +  "', address = '" + std.getAddress() + "', phone = '" + std.getPhone() + "' WHERE id = " + id;
        System.out.println(sql);
    }

    public ArrayList<Class> getListClassName  () {
        ArrayList<Class> listClassName = new ArrayList<>();
        String sql = "SELECT * FROM class";
        try {
            ResultSet result = conn.prepareStatement(sql).executeQuery();
            while (result.next()){
                Class classItem = new Class(
                        result.getInt("id"),
                        result.getString("className")
                );
                listClassName.add(classItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listClassName;
    }
}