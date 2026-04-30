package com.example.miniproject_4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class loginConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/login_db";
    private static final String USER = "root";
    private static final String PASSWORD = "jijilalas101";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}