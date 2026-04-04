package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for Booking database operations. Handles all CRUD
 * operations for bookings.
 */
public class BookingRepositoryImpl {

	// Backward compatibility - for legacy models
	private static List<org.expleo.TicketBookingJavaProject.model.City> cities = new ArrayList<>();
	private static List<org.expleo.TicketBookingJavaProject.model.Showtime> showtimes = new ArrayList<>();

	// Backward compatibility methods
	public static List<org.expleo.TicketBookingJavaProject.model.City> getCities() {
		return cities;
	}

	public static void addCity(org.expleo.TicketBookingJavaProject.model.City city) {
		cities.add(city);
	}

	public static List<org.expleo.TicketBookingJavaProject.model.Showtime> getShowtimes() {
		return showtimes;
	}

	public static void addShowtime(org.expleo.TicketBookingJavaProject.model.Showtime showtime) {
		showtimes.add(showtime);
	}

	// Redirect methods for compatibility
	public static List<org.expleo.TicketBookingJavaProject.model.Movie> getMovies() {
		return MovieRepositoryImpl.getAllMovies();
	}

	public static List<org.expleo.TicketBookingJavaProject.model.Theatre> getTheatres() {
		return TheatreRepositoryImpl.getAllTheatres();
	}

	/**
	 * Retrieves all bookings from the database.
	 * 
	 * @return List of all Booking objects
	 */
	public static List<Booking> getBookings() {
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

	/**
	 * Retrieves bookings for a specific user.
	 * 
	 * @param userId User ID to filter by
	 * @return List of Booking objects for the user
	 */
	public static List<Booking> getBookingsByUserId(int userId) {
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

	/**
	 * Retrieves bookings for a specific theatre.
	 * 
	 * @param theatreId Theatre ID to filter by
	 * @return List of Booking objects for the theatre
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

	/**
	 * Retrieves a booking by its ID.
	 * 
	 * @param bookingId Booking ID to search for
	 * @return Booking object if found, null otherwise
	 */
	public static Booking getBookingById(String bookingId) {
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

	/**
	 * Adds a new booking to the database.
	 * 
	 * @param booking Booking object to add
	 */
	public static void addBooking(Booking booking) {
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

	/**
	 * Updates an existing booking in the database.
	 * 
	 * @param booking Booking object with updated information
	 */
	public static void updateBooking(Booking booking) {
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

	/**
	 * Deletes a booking from the database.
	 * 
	 * @param bookingId Booking ID to delete
	 */
	public static void deleteBooking(String bookingId) {
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

	/**
	 * Cancels a booking by updating its status.
	 * 
	 * @param bookingId Booking ID to cancel
	 */
	public static void cancelBooking(String bookingId) {
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
