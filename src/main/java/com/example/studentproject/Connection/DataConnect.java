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

    public static final String URL = "jdbc:mysql://localhost/student_javafx";
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

        String sql = "SELECT std_info.id, std_info.idClass, std_info.name, std_info.address, std_info.score, std_info.Phone, class.className FROM student_info as std_info JOIN class ON std_info.idClass = class.id ORDER BY std_info.id ASC";
        try {
            ResultSet result = conn.prepareStatement(sql).executeQuery();
            while(result.next()){

                Student std = new Student(
                    result.getInt("id"),
                    result.getString("name"),
                    new Class(result.getInt("idClass"),result.getString("className")),
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
        String sql = "DELETE FROM student_info WHERE id =" + id;
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertStudent (Student std) {
        String sql = "INSERT INTO student_info (name, idClass, address, score, Phone) VALUES ('"+ std.getName() +"', "+ std.getStudentClass().getId() +", '"+ std.getAddress() +"', " + std.getScore() +",' "+ std.getPhone() +"')";
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
