package org.expleo.TicketBookingSystem.models;

import org.expleo.TicketBookingSystem.service.MovieService;

public class Customer extends User {

    public Customer(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }
    public static void viewMovies() {
        MovieService.viewMovies();

    }
}