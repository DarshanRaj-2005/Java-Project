package org.expleo.TicketBookingJavaProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.config.DBConnection;
import org.expleo.TicketBookingJavaProject.dao.SeatDAO;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class SeatDAOImpl implements SeatDAO {

    @Override
    public List<Seat> getSeatsByShowId(String sessionKey) {

        List<Seat> seats = new ArrayList<>();

        String query = "SELECT * FROM seats WHERE session_key = ? ORDER BY row_char, seat_number";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, sessionKey);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String seatLabel =
                        rs.getString("row_char") + rs.getInt("seat_number");

                Seat seat = new Seat(
                        rs.getInt("seat_id"),
                        sessionKey,
                        seatLabel,
                        rs.getString("status"),
                        rs.getDouble("price")
                );

                seats.add(seat);
            }

        } catch (SQLException e) {
            System.out.println("Error loading seats: " + e.getMessage());
        }

        return seats;
    }

    @Override
    public Seat findSeatByLabel(String sessionKey, String seatLabel) {

        String rowChar = seatLabel.substring(0, 1).toUpperCase();
        int seatNumber = Integer.parseInt(seatLabel.substring(1));

        String query = "SELECT * FROM seats "
                + "WHERE session_key = ? AND row_char = ? AND seat_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, sessionKey);
            ps.setString(2, rowChar);
            ps.setInt(3, seatNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Seat(
                        rs.getInt("seat_id"),
                        sessionKey,
                        seatLabel.toUpperCase(),
                        rs.getString("status"),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.out.println("Seat search failed: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean updateSeatBookingStatus(int seatId, boolean booked) {

        String status = booked ? "BOOKED" : "AVAILABLE";

        String query = "UPDATE seats SET status = ? WHERE seat_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, seatId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Unable to update seat status: " + e.getMessage());
        }

        return false;
    }
}