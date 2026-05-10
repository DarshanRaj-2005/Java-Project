package org.expleo.TicketBookingJavaProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.expleo.TicketBookingJavaProject.config.DBConnection;
import org.expleo.TicketBookingJavaProject.dao.BookingDAO;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class BookingDAOImpl implements BookingDAO {

	@Override
    public boolean saveBooking(Booking booking) {

        String bookingQuery =
                "INSERT INTO bookings "
              + "(booking_id, user_id, movie_id, theatre_id, showtime, seat_labels, total_amount, status) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String updateSeatQuery =
                "UPDATE seats SET status = 'BOOKED' WHERE seat_id = ?";

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement bookingPs = con.prepareStatement(bookingQuery);

            bookingPs.setString(1, booking.getBookingId());
            bookingPs.setString(2, booking.getUserId());
            bookingPs.setString(3, booking.getMovieId());
            bookingPs.setInt(4, booking.getTheatreId());
            bookingPs.setString(5, booking.getShowtime());
            bookingPs.setString(6, String.join(",", booking.getSeatLabels()));
            bookingPs.setDouble(7, booking.getTotalAmount());
            bookingPs.setString(8, booking.getStatus());

            bookingPs.executeUpdate();

            PreparedStatement seatPs = con.prepareStatement(updateSeatQuery);

            for (Seat seat : booking.getBookedSeats()) {
                seatPs.setInt(1, seat.getSeatId());
                seatPs.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Booking failed: " + e.getMessage());
        }

        return false;
    }
}