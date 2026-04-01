package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for Seat database operations. Handles seat layout
 * and status management.
 */
public class SeatRepositoryImpl {

	/**
	 * Gets all seats for a session. If no seats exist, creates a 10x10 seat layout.
	 * 
	 * @param sessionKey Session identifier (format: theatre_movie_showtime)
	 * @return List of seats
	 */
	public List<Seat> getSeatsForSession(String sessionKey) {
		List<Seat> seats = new ArrayList<>();
		String query = "SELECT * FROM seats WHERE session_key = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

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
	 * Creates a 10x10 seat layout for a session.
	 * 
	 * @param sessionKey Session identifier
	 */
	private void createSeatLayout(String sessionKey) {
		String insertQuery = "INSERT INTO seats (session_key, row_char, seat_number, status, price) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

			double defaultPrice = 200.0;

			// Create 10 rows (A-J) with 10 seats each
			for (char row = 'A'; row <= 'J'; row++) {
				for (int num = 1; num <= 10; num++) {
					insertStmt.setString(1, sessionKey);
					insertStmt.setString(2, String.valueOf(row));
					insertStmt.setInt(3, num);
					insertStmt.setString(4, "AVAILABLE");
					insertStmt.setDouble(5, defaultPrice);
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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

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
