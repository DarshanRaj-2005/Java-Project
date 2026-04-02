package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for Theatre database operations.
 * Implements DAO pattern for theatre-related database access.
 */
public class TheatreRepositoryImpl {

    /**
     * Retrieves all theatres from the database.
     * @return List of all Theatre objects
     */
    public static List<Theatre> getAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        String query = "SELECT * FROM theatres";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Theatre theatre = new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
                theatre.setAdminId(rs.getInt("adminId"));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching theatres: " + e.getMessage());
            e.printStackTrace();
        }
        return theatres;
    }

    /**
     * Retrieves a theatre by its ID.
     * @param id Theatre ID
     * @return Theatre object if found, null otherwise
     */
    public static Theatre getTheatreById(int id) {
        String query = "SELECT * FROM theatres WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Theatre theatre = new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
                theatre.setAdminId(rs.getInt("adminId"));
                return theatre;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching theatre: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new theatre to the database.
     * @param theatre Theatre object to add
     */
    public static void addTheatre(Theatre theatre) {
        String query = "INSERT INTO theatres (name, city, adminId) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, theatre.getName());
            stmt.setString(2, theatre.getCity());
            stmt.setInt(3, theatre.getAdminId());
            
            stmt.executeUpdate();
            
            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    theatre.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Theatre added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding theatre: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates a theatre's information.
     * @param theatreId Theatre ID to update
     * @param theatre Theatre object with new values
     */
    public static void updateTheatre(int theatreId, Theatre theatre) {
        String query = "UPDATE theatres SET name = ?, city = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, theatre.getName());
            stmt.setString(2, theatre.getCity());
            stmt.setInt(3, theatreId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theatre updated successfully!");
            } else {
                System.out.println("Theatre not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating theatre: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Removes a theatre from the database.
     * @param id Theatre ID to remove
     */
    public static void removeTheatre(int id) {
        String query = "DELETE FROM theatres WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Theatre removed successfully!");
            } else {
                System.out.println("Theatre not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error removing theatre: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the admin ID for a theatre.
     * @param theatreId Theatre ID
     * @param adminId Admin user ID to assign
     */
    public static void updateTheatreAdmin(int theatreId, int adminId) {
        String query = "UPDATE theatres SET adminId = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, adminId);
            stmt.setInt(2, theatreId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating theatre admin: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves theatres filtered by city.
     * @param city City name to filter by
     * @return List of theatres in the specified city
     */
    public static List<Theatre> getTheatresByCity(String city) {
        List<Theatre> theatres = new ArrayList<>();
        String query = "SELECT * FROM theatres WHERE city = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Theatre theatre = new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
                theatre.setAdminId(rs.getInt("adminId"));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching theatres by city: " + e.getMessage());
            e.printStackTrace();
        }
        return theatres;
    }
    
    /**
     * Gets all unique cities from theatres table.
     * @return List of distinct city names
     */
    public static List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT city FROM theatres ORDER BY city";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                cities.add(rs.getString("city"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cities: " + e.getMessage());
            e.printStackTrace();
        }
        return cities;
    }
}
