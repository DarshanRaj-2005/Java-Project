package org.expleo.TicketBookingJavaProject.repository.impl;

import java.util.List;

import org.expleo.TicketBookingJavaProject.config.DBConnection;
import org.expleo.TicketBookingJavaProject.dao.SeatDAO;
import org.expleo.TicketBookingJavaProject.dao.SeatDAOImpl;
import org.expleo.TicketBookingJavaProject.model.Seat;

public class SeatRepositoryImpl implements SeatRepository {

	private SeatDAO seatDAO;

    public SeatRepositoryImpl() {
        this.seatDAO = new SeatDAOImpl();
    }

    @Override
    public List<Seat> getSeatsByShowId(String sessionKey) {
        return seatDAO.getSeatsByShowId(sessionKey);
    }

    @Override
    public Seat findSeatByLabel(String sessionKey, String seatLabel) {
        return seatDAO.findSeatByLabel(sessionKey, seatLabel);
    }

    @Override
    public boolean updateSeatBookingStatus(int seatId, boolean booked) {
        return seatDAO.updateSeatBookingStatus(seatId, booked);
    }
}