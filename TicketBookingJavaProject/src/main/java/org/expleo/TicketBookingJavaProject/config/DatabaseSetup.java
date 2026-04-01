package org.expleo.TicketBookingJavaProject.config;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

	// Creates tables securely without overwriting existing data
	public static void initialize() {
		try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {

			String createUsers = "CREATE TABLE IF NOT EXISTS users (" + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(100) NOT NULL, " + "email VARCHAR(100) UNIQUE NOT NULL, "
					+ "phone VARCHAR(15) NOT NULL, " + "password VARCHAR(100) NOT NULL, " + "role VARCHAR(50) NOT NULL"
					+ ")";

			String createTheatres = "CREATE TABLE IF NOT EXISTS theatres (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(100) NOT NULL, " + "city VARCHAR(100) NOT NULL, " + "adminId INT" + ")";

			String createMovies = "CREATE TABLE IF NOT EXISTS movies (" + "id VARCHAR(50) PRIMARY KEY, "
					+ "title VARCHAR(150) NOT NULL, " + "genre VARCHAR(50), " + "language VARCHAR(50), "
					+ "duration INT, " + "releaseDate VARCHAR(50), " + "theatre_id INT" + ")";

			String createSeats = "CREATE TABLE IF NOT EXISTS seats (" + "seat_id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "session_key VARCHAR(100) NOT NULL, " + "row_char VARCHAR(5) NOT NULL, "
					+ "seat_number INT NOT NULL, " + "status VARCHAR(30) NOT NULL, " + "price DOUBLE NOT NULL" + ")";

			String createBookings = "CREATE TABLE IF NOT EXISTS bookings (" + "booking_id VARCHAR(50) PRIMARY KEY, "
					+ "user_id VARCHAR(50), " + "movie_id VARCHAR(50), " + "theatre_id INT, " + "showtime VARCHAR(50), "
					+ "seat_labels TEXT, " + "total_amount DOUBLE, " + "status VARCHAR(50)" + ")";

			stmt.execute(createUsers);
			stmt.execute(createTheatres);
			stmt.execute(createMovies);
			stmt.execute(createSeats);
			stmt.execute(createBookings);

			// Optional: You could insert predefined admin users here if users table is
			// empty
			// We'll let the user repository static block handle that if needed

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
