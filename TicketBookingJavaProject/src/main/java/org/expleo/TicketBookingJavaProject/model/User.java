package org.expleo.TicketBookingJavaProject.model;

/*
 * Base User class
 * Represents common properties shared by all users
 * Sprint 1 - User Authentication Module
 */
public class User {

    // Unique identifier for user
    protected int userId;
    
    // User's full name
    protected String name;
    
    // User's email address (used for login)
    protected String email;
    
    // User's phone number
    protected String phone;
    
    // User's password (used for authentication)
    protected String password;

    // Constructor to initialize user details
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getter for email (used for login)
    public String getEmail() {
        return email;
    }

    // Getter for password (used for authentication)
    public String getPassword() {
        return password;
    }
}
