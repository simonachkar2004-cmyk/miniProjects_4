package com.example.miniproject_4;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/client_db",
                    "root",
                    "jijilalas101"
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}