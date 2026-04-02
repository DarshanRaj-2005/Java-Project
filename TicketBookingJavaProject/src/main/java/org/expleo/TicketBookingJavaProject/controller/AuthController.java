package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

// Handles user registration and login operations
public class AuthController {

    // Scanner for reading user input from console
    private Scanner sc = new Scanner(System.in);

    // Registers a new customer account
    public void register() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        // Check if email already exists in database
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: This email is already registered!");
            return;
        }

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate all required fields are filled
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required!");
            return;
        }

        // Create new user with "Customer" role and save to database
        User newUser = new User(0, name, email, phone, password, "Customer");
        UserRepositoryImpl.addUser(newUser);
        System.out.println("\nRegistration Successful!");
        System.out.println("You can now login with your email and password.");
    }

    // Authenticates user based on email and password
    public User login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate input fields are not empty
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Email and Password are required!");
            return null;
        }

        // Fetch user by email and verify password match
        User user = UserRepositoryImpl.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("\nLogin Successful! Welcome, " + user.getName() + "!");
            return user;
        }
        System.out.println("Error: Invalid email or password!");
        return null;
    }
}
