package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for Seat database operations.
 * Handles seat layout and status management.
 */
public class SeatRepositoryImpl {

    // Tiered pricing constants
    private static final double PRICE_PREMIUM = 190.0;  // Rows A, B, C (top 3)
    private static final double PRICE_STANDARD = 160.0; // Rows D, E, F, G
    private static final double PRICE_BASIC = 60.0;    // Rows H, I, J (bottom)

    /**
     * Gets the price for a seat based on its row.
     * @param row The row character (A-J)
     * @return The price for the seat
     */
    private double getPriceForRow(char row) {
        // Top 3 rows: A, B, C -> Rs. 190
        if (row >= 'A' && row <= 'C') {
            return PRICE_PREMIUM;
        }
        // Next rows: D, E, F, G -> Rs. 160
        else if (row >= 'D' && row <= 'G') {
            return PRICE_STANDARD;
        }
        // Bottom rows: H, I, J -> Rs. 60
        else if (row >= 'H' && row <= 'J') {
            return PRICE_BASIC;
        }
        // Default to standard price
        return PRICE_STANDARD;
    }

    /**
     * Gets all seats for a session.
     * If no seats exist, creates a 10x10 seat layout with tiered pricing.
     * 
     * @param sessionKey Session identifier (format: theatre_movie_showtime)
     * @return List of seats
     */
    public List<Seat> getSeatsForSession(String sessionKey) {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT * FROM seats WHERE session_key = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sessionKey);
            ResultSet rs = stmt.executeQuery();

            // Fetch existing seats
            while (rs.next()) {
                seats.add(new Seat(rs.getInt("seat_id"), rs.getString("row_char"), rs.getInt("seat_number"),
                        rs.getString("status"), rs.getDouble("price")));
            }

            // If no seats exist, create layout
            if (seats.isEmpty()) {
                createSeatLayout(sessionKey);
                // Fetch created seats
                rs = stmt.executeQuery();
                while (rs.next()) {
                    seats.add(new Seat(rs.getInt("seat_id"), rs.getString("row_char"), rs.getInt("seat_number"),
                            rs.getString("status"), rs.getDouble("price")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching seats: " + e.getMessage());
            e.printStackTrace();
        }
        return seats;
    }

    /**
     * Creates a 10x10 seat layout for a session with tiered pricing.
     * Top 3 rows (A-C): Rs. 190
     * Next 4 rows (D-G): Rs. 160
     * Bottom 3 rows (H-J): Rs. 60
     * 
     * @param sessionKey Session identifier
     */
    private void createSeatLayout(String sessionKey) {
        String insertQuery = "INSERT INTO seats (session_key, row_char, seat_number, status, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Create 10 rows (A-J) with 10 seats each
            for (char row = 'A'; row <= 'J'; row++) {
                // Get the price for this row based on tiered pricing
                double price = getPriceForRow(row);
                
                for (int num = 1; num <= 10; num++) {
                    insertStmt.setString(1, sessionKey);
                    insertStmt.setString(2, String.valueOf(row));
                    insertStmt.setInt(3, num);
                    insertStmt.setString(4, "AVAILABLE");
                    insertStmt.setDouble(5, price);
                    insertStmt.executeUpdate();
                }
            }
            System.out.println("Seat layout created for this session.");
        } catch (SQLException e) {
            System.out.println("Error creating seat layout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates seat information in the database.
     * 
     * @param seat Seat object with updated information
     */
    public void updateSeat(Seat seat) {
        String query = "UPDATE seats SET status = ?, price = ? WHERE seat_id = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, seat.getStatus());
            stmt.setDouble(2, seat.getPrice());
            stmt.setInt(3, seat.getSeatId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating seat: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
