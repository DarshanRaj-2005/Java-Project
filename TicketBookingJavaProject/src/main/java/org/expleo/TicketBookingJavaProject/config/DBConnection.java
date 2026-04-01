package org.expleo.TicketBookingJavaProject.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection class handles database connection to MySQL.
 */
public class DBConnection {

    // Database connection URL
    private static final String URL = "jdbc:mysql://mysql-19f33634-dharshujayapal-dc10.e.aivencloud.com:24437/defaultdb?sslMode=REQUIRED";
    
    // Database username
    private static final String USER = "avnadmin";
    
    // Database password
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    /**
     * Gets a database connection.
     * @return Connection object to MySQL database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
        
        // Return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
