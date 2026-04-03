package org.expleo.TicketBookingJavaProject.model;

import org.expleo.TicketBookingJavaProject.service.MovieService;

/*
 * Customer class inherits from User
 * Represents a normal user who can book tickets
 * Sprint 1 - User Authentication Module
 */
public class Customer extends User {

    // Constructor calling parent class constructor
    public Customer(int userId, String name, String email, String phone, String password, String role) {
        super(userId, name, email, phone, password, role);
    }
    
    public static void viewMovies() {
        MovieService.viewMovies();

    }
}
