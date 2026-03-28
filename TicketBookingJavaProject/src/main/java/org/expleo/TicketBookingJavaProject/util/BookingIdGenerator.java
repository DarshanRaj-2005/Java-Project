package org.expleo.TicketBookingJavaProject.util;

import java.util.UUID;

public class BookingIdGenerator {

    public static String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 6);
    }
}