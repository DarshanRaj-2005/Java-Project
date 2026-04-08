/*
 * FILE: Customer.java
 * PURPOSE: Represents a customer (extends User class).
 * AUTHOR: KRISHNAPRASATH B
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends User class (gets all user fields)
 * - Polymorphism: Can be used wherever User is expected
 * 
 * Note: This class is not actively used. Regular users are User objects with role="Customer".
 */
package org.expleo.TicketBookingJavaProject.model;

import org.expleo.TicketBookingJavaProject.service.MovieService;

/*
 * WHAT THIS CLASS DOES:
 * Represents a customer who can book tickets.
 * Inherits all fields from User class.
 */
public class Customer extends User {

    /*
     * Constructor - Creates a Customer
     * Calls parent User constructor with same parameters.
     */
    public Customer(int userId, String name, String email, String phone, String password, String role) {
        super(userId, name, email, phone, password, role);
    }
    
    /*
     * viewMovies - Shows available movies
     * 
     * Note: Most functionality is handled by MovieController.
     */
    public static void viewMovies() {
        MovieService.viewMovies();
    }
}
