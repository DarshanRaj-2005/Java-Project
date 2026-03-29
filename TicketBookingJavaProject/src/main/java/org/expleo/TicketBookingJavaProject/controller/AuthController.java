package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;

public class AuthController {

    private Scanner sc = new Scanner(System.in);

    // Temporary in-memory users (MVP)
    private Map<String, String> users = new HashMap<>();
    private Map<String, String> roles = new HashMap<>();

    public AuthController() {
        // Default admin
        users.put("admin@gmail.com", "admin123");
        roles.put("admin@gmail.com", "admin");
    }

    // REGISTER
    public void register() {
        System.out.println("\n--- REGISTER ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        users.put(email, password);
        roles.put(email, "customer");

        System.out.println("Registration Successful!");
    }

    // LOGIN
    public String login() {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (users.containsKey(email) && users.get(email).equals(password)) {
            System.out.println("Login Successful!");
            return roles.get(email);
        }

        System.out.println("Invalid Credentials!");
        return "guest";
    }
}