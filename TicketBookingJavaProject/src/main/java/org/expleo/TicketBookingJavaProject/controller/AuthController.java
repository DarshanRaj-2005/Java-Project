package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

/**
 * Controller for user registration and login operations.
 * Handles customer self-registration with validation.
 */
public class AuthController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /**
     * Registers a new customer.
     * Customers can only be created through self-registration.
     * Includes validation for email and phone number.
     */
    public void register() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        // Validate name
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty!");
            return;
        }

        // Get and validate email
        String email = getValidEmail();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();
        
        // Validate phone number
        if (!isValidPhone(phone)) {
            System.out.println("Error: Phone number must be exactly 10 digits!");
            return;
        }
        
        // Check if phone already exists
        // For simplicity, we skip this check as phone is not unique field

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate password
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty!");
            return;
        }

        // Create new customer
        User newUser = new User(0, name, email, phone, password, "Customer");
        UserRepositoryImpl.addUser(newUser);

        System.out.println("\nRegistration Successful!");
        System.out.println("You can now login with your email and password.");
    }

    /**
     * Gets and validates email from user input.
     * Email must contain "@" symbol.
     * @return Valid email string
     */
    private String getValidEmail() {
        while (true) {
            System.out.print("Enter Email: ");
            String email = sc.nextLine().trim();
            
            // Check if email is empty
            if (email.isEmpty()) {
                System.out.println("Error: Email cannot be empty!");
                continue;
            }
            
            // Check if email contains "@"
            if (!email.contains("@")) {
                System.out.println("Error: Email must contain '@' symbol!");
                System.out.println("Please enter a valid email (e.g., user@example.com)");
                continue;
            }
            
            // Check if email already exists
            if (UserRepositoryImpl.getUserByEmail(email) != null) {
                System.out.println("Error: This email is already registered!");
                continue;
            }
            
            return email;
        }
    }

    /**
     * Validates phone number.
     * Phone number must be exactly 10 digits.
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidPhone(String phone) {
        // Check if phone is exactly 10 digits
        if (phone == null || phone.length() != 10) {
            return false;
        }
        
        // Check if all characters are digits
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        
        return true;
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
