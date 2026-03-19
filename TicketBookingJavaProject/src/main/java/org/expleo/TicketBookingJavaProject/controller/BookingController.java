package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.service.BookingService;

public class BookingController {
	private BookingService service = new BookingService();

    public void checkTicketAvailability(int ticketCount) {

        boolean isAvailable = service.validateTicketCount(ticketCount);

        if (isAvailable) {
            System.out.println("Tickets available. Proceed to seat selection.");
        } 
        else {
            System.out.println("Not enough seats available.");
        }
    }
}
