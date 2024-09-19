package com.felipe.SQLite;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class SQLConnection {
    private Connection connection = null;
    private String url = "jdbc:sqlite:db_tasks.db";

    public Connection getConnection(){
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Connection made successfully");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            connection = null;
            return connection;
        } 
    }
    public boolean disconnect(Connection connection) {
        try {
            if (connection.isClosed() == false) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        System.out.println("Disconnected!!!");
        return true;
    }
    
}
