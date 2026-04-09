/*
 * FILE: BookingRepositoryImpl.java
 * PURPOSE: Handles all booking database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Data Access Object (DAO) Pattern
 * - Static methods for easy access
 * 
 * WHAT THIS FILE DOES:
 * - CRUD operations for bookings
 * - Filter bookings by user or theatre
 * - Cancel bookings
 * 
 * DATABASE TABLE: bookings
 */



//------------Author Name: Krishna Prasath, Rohini---------------



package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.expleo.TicketBookingJavaProject.config.DBConnection;
import org.expleo.TicketBookingJavaProject.dao.BookingDAO;
import org.expleo.TicketBookingJavaProject.model.Booking;

/*
 * Repository implementation for Booking database operations.
 * Implements BookingDAO interface for proper DAO pattern.
 */
public class BookingRepositoryImpl implements BookingDAO {

    /*
     * getAllBookings - Gets all bookings (DAO implementation)
     */
    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String seatLabelsStr = rs.getString("seat_labels");
                List<String> seatLabels = new ArrayList<>();
                if (seatLabelsStr != null && !seatLabelsStr.isEmpty()) {
                    seatLabels.addAll(Arrays.asList(seatLabelsStr.split(",")));
                }

                bookings.add(new Booking(rs.getString("booking_id"), rs.getInt("user_id"), rs.getString("movie_id"),
                        rs.getInt("theatre_id"), rs.getString("showtime"), seatLabels, rs.getDouble("total_amount"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /*
     * getBookingsByUserId - Gets bookings for a user
     */
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String seatLabelsStr = rs.getString("seat_labels");
                List<String> seatLabels = new ArrayList<>();
                if (seatLabelsStr != null && !seatLabelsStr.isEmpty()) {
                    seatLabels.addAll(Arrays.asList(seatLabelsStr.split(",")));
                }

                bookings.add(new Booking(rs.getString("booking_id"), rs.getInt("user_id"), rs.getString("movie_id"),
                        rs.getInt("theatre_id"), rs.getString("showtime"), seatLabels, rs.getDouble("total_amount"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /*
     * getBookingsByTheatreId - Gets bookings for a theatre
     */
    public static List<Booking> getBookingsByTheatreId(int theatreId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE theatre_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, theatreId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String seatLabelsStr = rs.getString("seat_labels");
                List<String> seatLabels = new ArrayList<>();
                if (seatLabelsStr != null && !seatLabelsStr.isEmpty()) {
                    seatLabels.addAll(Arrays.asList(seatLabelsStr.split(",")));
                }

                bookings.add(new Booking(rs.getString("booking_id"), rs.getInt("user_id"), rs.getString("movie_id"),
                        rs.getInt("theatre_id"), rs.getString("showtime"), seatLabels, rs.getDouble("total_amount"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching theatre bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /*
     * getBookingById - Gets booking by ID
     */
    @Override
    public Booking getBookingById(String bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String seatLabelsStr = rs.getString("seat_labels");
                List<String> seatLabels = new ArrayList<>();
                if (seatLabelsStr != null && !seatLabelsStr.isEmpty()) {
                    seatLabels.addAll(Arrays.asList(seatLabelsStr.split(",")));
                }

                return new Booking(rs.getString("booking_id"), rs.getInt("user_id"), rs.getString("movie_id"),
                        rs.getInt("theatre_id"), rs.getString("showtime"), seatLabels, rs.getDouble("total_amount"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching booking: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * addBooking - Adds new booking
     */
    @Override
    public void addBooking(Booking booking) {
        String query = "INSERT INTO bookings (booking_id, user_id, movie_id, theatre_id, showtime, seat_labels, total_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getBookingId());
            stmt.setInt(2, booking.getUserId());
            stmt.setString(3, booking.getMovieId());
            stmt.setInt(4, booking.getTheatreId());
            stmt.setString(5, booking.getShowtime());
            stmt.setString(6, String.join(",", booking.getSeatLabels()));
            stmt.setDouble(7, booking.getTotalAmount());
            stmt.setString(8, booking.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * updateBooking - Updates booking
     */
    @Override
    public void updateBooking(Booking booking) {
        String query = "UPDATE bookings SET status = ?, total_amount = ?, seat_labels = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getStatus());
            stmt.setDouble(2, booking.getTotalAmount());
            stmt.setString(3, String.join(",", booking.getSeatLabels()));
            stmt.setString(4, booking.getBookingId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * deleteBooking - Removes booking
     */
    @Override
    public void deleteBooking(String bookingId) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bookingId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Booking deleted successfully!");
            } else {
                System.out.println("Booking not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * cancelBooking - Sets booking status to CANCELLED
     */
    @Override
    public void cancelBooking(String bookingId) {
        String query = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "CANCELLED");
            stmt.setString(2, bookingId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Booking cancelled successfully!");
            } else {
                System.out.println("Booking not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
