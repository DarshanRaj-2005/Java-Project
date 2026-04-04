package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for User database operations.
 * Handles all CRUD operations for users.
 */
public class UserRepositoryImpl {

    // Static initializer - ensures default Super Admin exists
    static {
        initializeDefaultAdmin();
    }

    /**
     * Creates default Super Admin if not exists.
     */
    private static void initializeDefaultAdmin() {
        try (Connection conn = DBConnection.getConnection()) {
            // Check if admin exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, "admin@gmail.com");
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                // Create default admin
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (name, email, phone, password, role, theatre_id) VALUES (?, ?, ?, ?, ?, 0)");
                insertStmt.setString(1, "Super Admin");
                insertStmt.setString(2, "admin@gmail.com");
                insertStmt.setString(3, "1234567890");
                insertStmt.setString(4, "admin123");
                insertStmt.setString(5, "Super Admin");
                insertStmt.executeUpdate();
                System.out.println("Default Super Admin created!");
            }
        } catch (SQLException e) {
            System.out.println("Warning: Could not initialize default admin: " + e.getMessage());
        }
    }

    /**
     * Helper method to get theatre_id from ResultSet safely.
     * Handles case where column doesn't exist.
     */
    private static int getTheatreIdSafe(ResultSet rs) {
        try {
            return rs.getInt("theatre_id");
        } catch (SQLException e) {
            return 0; // Default to 0 if column doesn't exist
        }
    }

    /**
     * Retrieves all users from database.
     * @return List of all User objects
     */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Retrieves a user by their ID.
     * @param userId User ID to search for
     * @return User object if found, null otherwise
     */
    public static User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a user by their email.
     * @param email Email to search for
     * @return User object if found, null otherwise
     */
    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new user to the database.
     * @param user User object to add
     */
    public static void addUser(User user) {
        // Try with theatre_id column first
        try {
            String query = "INSERT INTO users (name, email, phone, password, role, theatre_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getPassword());
                stmt.setString(5, user.getRole());
                stmt.setInt(6, user.getTheatreId());
                
                stmt.executeUpdate();
                
                // Get generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            // Fallback: try without theatre_id column
            try {
                String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
                
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    
                    stmt.setString(1, user.getName());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getPhone());
                    stmt.setString(4, user.getPassword());
                    stmt.setString(5, user.getRole());
                    
                    stmt.executeUpdate();
                    
                    // Get generated ID
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setUserId(generatedKeys.getInt(1));
                        }
                    }
                }
            } catch (SQLException e2) {
                System.out.println("Error adding user: " + e2.getMessage());
            }
        }
    }

    /**
     * Updates an existing user's information.
     * @param userId User ID to update
     * @param user User object with new values
     */
    public static void updateUser(int userId, User user) {
        // Try with theatre_id column first
        try {
            String query = "UPDATE users SET name = ?, email = ?, phone = ?, password = ?, role = ?, theatre_id = ? WHERE user_id = ?";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getPassword());
                stmt.setString(5, user.getRole());
                stmt.setInt(6, user.getTheatreId());
                stmt.setInt(7, userId);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User updated successfully!");
                } else {
                    System.out.println("User not found!");
                }
            }
        } catch (SQLException e) {
            // Fallback: try without theatre_id
            try {
                String query = "UPDATE users SET name = ?, email = ?, phone = ?, password = ?, role = ? WHERE user_id = ?";
                
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    
                    stmt.setString(1, user.getName());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getPhone());
                    stmt.setString(4, user.getPassword());
                    stmt.setString(5, user.getRole());
                    stmt.setInt(6, userId);
                    
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User updated successfully!");
                    } else {
                        System.out.println("User not found!");
                    }
                }
            } catch (SQLException e2) {
                System.out.println("Error updating user: " + e2.getMessage());
            }
        }
    }

    /**
     * Updates user profile (name, phone, password).
     * @param user User object with updated information
     */
    public static void updateProfile(User user) {
        String query = "UPDATE users SET name = ?, phone = ?, password = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully in database!");
            } else {
                System.out.println("Failed to update profile!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the theatre ID for a user.
     * @param userId User ID to update
     * @param theatreId Theatre ID to assign
     */
    public static void updateUserTheatre(int userId, int theatreId) {
        String query = "UPDATE users SET theatre_id = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, theatreId);
            stmt.setInt(2, userId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user theatre: " + e.getMessage());
        }
    }

    /**
     * Removes a user from the database.
     * @param userId User ID to remove
     */
    public static void removeUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User removed successfully!");
            } else {
                System.out.println("User not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
