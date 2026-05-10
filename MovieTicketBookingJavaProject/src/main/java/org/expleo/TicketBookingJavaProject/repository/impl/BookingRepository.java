package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Booking;

public interface BookingRepository {

    boolean saveBooking(Booking booking);
}