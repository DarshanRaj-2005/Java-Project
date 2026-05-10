/*
 * FILE: AuthController.java
 * PURPOSE: Handles user registration and login.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private
 * - Abstraction: Hides validation complexity
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * - Composition: Uses UserRepositoryImpl
 * 
 * WHAT THIS FILE DOES:
 * - Registers new customers
 * - Validates email and phone formats
 * - Authenticates users during login
 * 
 * Only customers can self-register. Admins are created by Super Admin.
 */



//------------Author Name: Tamil Kumar---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

public class AuthController extends BaseController {

    private UserRepositoryImpl userDAO = UserRepositoryImpl.getInstance();

    public AuthController() {
        super();
        printInfo("AuthController initialized");
    }

    public AuthController(Scanner sharedScanner) {
        super(sharedScanner);
        printInfo("AuthController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("AUTHENTICATION");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Back");
        
        int choice = getValidChoice(1, 3);
        
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                stop();
                break;
            default:
                printError("Invalid option selected");
        }
    }

    public void register() {
        printSubHeader("CUSTOMER REGISTRATION");

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        if (name.isEmpty()) {
            printError("Name cannot be empty!");
            return;
        }

        String email = getValidEmail();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();
        
        if (!isValidPhone(phone)) {
            printError("Phone number must be exactly 10 digits!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        if (password.isEmpty()) {
            printError("Password cannot be empty!");
            return;
        }

        User newUser = new User(0, name, email, phone, password, "Customer");
        userDAO.addUser(newUser);

        printSuccess("Registration Successful!");
        System.out.println("You can now login with your email and password.");
    }

    private String getValidEmail() {
        while (true) {
            System.out.print("Enter Email: ");
            String email = sc.nextLine().trim();
            
            if (email.isEmpty()) {
                printError("Email cannot be empty!");
                continue;
            }
            
            if (!email.contains("@")) {
                printError("Email must contain '@' symbol!");
                System.out.println("Please enter a valid email (e.g., user@example.com)");
                continue;
            }
            
            if (userDAO.getUserByEmail(email) != null) {
                printError("This email is already registered!");
                continue;
            }
            
            return email;
        }
    }

    private boolean isValidPhone(String phone) {
        if (phone == null || phone.length() != 10) {
            return false;
        }
        
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }

    public User login() {
        printSubHeader("LOGIN");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            printError("Email and Password are required!");
            return null;
        }

        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            printSuccess("Login Successful! Welcome, " + user.getName() + "!");
            return user;
        }

        printError("Invalid email or password!");
        return null;
    }
}