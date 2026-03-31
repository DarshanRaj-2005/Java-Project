package org.expleo.TicketBookingJavaProject.model;

import org.expleo.TicketBookingJavaProject.service.MovieService;

/*
 * Customer class inherits from User
 * Represents a normal user who can book tickets
 * Sprint 1 - User Authentication Module
 */
public class Customer extends User {

    // Constructor calling parent class constructor
    public Customer(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }
    
    public static void viewMovies() {
        MovieService.viewMovies();

    }
}
