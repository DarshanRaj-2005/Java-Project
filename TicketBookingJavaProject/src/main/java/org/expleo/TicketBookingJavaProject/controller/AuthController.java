package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

// Handles user registration and login operations
public class AuthController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /**
     * Registers a new customer.
     * Customers can only be created through self-registration.
     */
    public void register() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        // Check if email already exists
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: This email is already registered!");
            return;
        }

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate inputs
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required!");
            return;
        }

        // Create new customer
        User newUser = new User(0, name, email, phone, password, "Customer");
        UserRepositoryImpl.addUser(newUser);

        System.out.println("\nRegistration Successful!");
        System.out.println("You can now login with your email and password.");
    }

    /**
     * Authenticates a user with email and password.
     * @return User object if credentials are valid, null otherwise
     */
    public User login() {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Email and Password are required!");
            return null;
        }

        // Check credentials
        User user = UserRepositoryImpl.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("\nLogin Successful! Welcome, " + user.getName() + "!");
            return user;
        }

        System.out.println("Error: Invalid email or password!");
        return null;
    }
}
