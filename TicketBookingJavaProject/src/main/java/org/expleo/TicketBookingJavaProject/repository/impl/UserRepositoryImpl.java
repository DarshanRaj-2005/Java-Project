package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

// Data access layer for user operations
public class UserRepositoryImpl {
    
    // Static initializer creates default admin account on class load
    static {
        initializeDefaultAdmin();
    }

    // Creates a default Super Admin if not already exists
    private static void initializeDefaultAdmin() {
        try (Connection conn = DBConnection.getConnection()) {
            // Check if admin email already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, "admin@gmail.com");
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                // Insert default admin credentials
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)");
                insertStmt.setString(1, "Super Admin");
                insertStmt.setString(2, "admin@gmail.com");
                insertStmt.setString(3, "1234567890");
                insertStmt.setString(4, "admin123");
                insertStmt.setString(5, "Super Admin");
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) { }
    }

    // Retrieves user by email address (used for login validation)
    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("name"),
                    rs.getString("email"), rs.getString("phone"),
                    rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) { }
        return null;
    }

    // Adds new user to database (used during registration)
    public static void addUser(User user) {
        String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }

    // Updates user profile information in database
    public static void updateProfile(User user) {
        String query = "UPDATE users SET name = ?, phone = ?, password = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }
}
