package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.dao.BookingDAO;
import org.expleo.TicketBookingJavaProject.dao.BookingDAOImpl;
import org.expleo.TicketBookingJavaProject.model.Booking;

public class BookingRepositoryImpl implements BookingRepository {

    private BookingDAO bookingDAO;

    public BookingRepositoryImpl() {
        this.bookingDAO = new BookingDAOImpl();
    }

    @Override
    public boolean saveBooking(Booking booking) {
        return bookingDAO.saveBooking(booking);
    }
}