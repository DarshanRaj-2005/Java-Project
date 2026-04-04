package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for Theatre Admin operations.
 * Theatre admins can manage movies and create officers.
 */
public class TheatreAdminController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);
    
    // Reference to MovieController
    private MovieController movieController;

    /**
     * Constructor to initialize dependencies.
     */
    public TheatreAdminController(MovieController movieController) {
        this.movieController = movieController;
    }

    /**
     * Validates email format.
     * Email must contain "@" symbol.
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@");
    }

    /**
     * Validates phone number.
     * Phone number must be exactly 10 digits.
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
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

    /**
     * Creates a new officer for the theatre.
     * Officer is linked to the Theatre Admin's theatre.
     */
    public void createOfficer(User adminUser) {
        // Get the theatre assigned to this admin
        Theatre theatre = TheatreRepositoryImpl.getAllTheatres().stream()
            .filter(t -> t.getAdminId() == adminUser.getUserId())
            .findFirst().orElse(null);
        
        if (theatre == null) {
            System.out.println("Error: You are not assigned to any theatre!");
            return;
        }
        
        System.out.println("\n--- CREATE NEW OFFICER for " + theatre.getName() + " ---");
        System.out.println("This officer will only be able to book tickets for " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty!");
            return;
        }
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        // Validate email
        if (!isValidEmail(email)) {
            System.out.println("Error: Email must contain '@' symbol!");
            return;
        }
        
        // Check if email already exists
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: Email already exists!");
            return;
        }

        System.out.print("Enter Phone (10 digits): ");
        String phone = sc.nextLine().trim();
        
        // Validate phone
        if (!isValidPhone(phone)) {
            System.out.println("Error: Phone number must be exactly 10 digits!");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Validate password
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty!");
            return;
        }

        // Create officer with theatre assignment
        User officer = new User(0, name, email, phone, password, "Officer");
        officer.setTheatreId(theatre.getId());
        UserRepositoryImpl.addUser(officer);
        
        System.out.println("\nOfficer '" + name + "' created successfully!");
        System.out.println("Assigned to: " + theatre.getName() + " (" + theatre.getCity() + ")");
        System.out.println("This officer can only book tickets for this theatre.");
    }

    /**
     * Adds a movie to the theatre.
     */
    public void addMovie(User adminUser) {
        movieController.addMovie(adminUser);
    }

    /**
     * Updates a movie in the theatre.
     */
    public void updateMovie(User adminUser) {
        movieController.updateMovie(adminUser);
    }

    /**
     * Deletes a movie from the theatre.
     */
    public void deleteMovie(User adminUser) {
        movieController.deleteMovie(adminUser);
    }

    /**
     * Views movies in the theatre.
     */
    public void viewMovies(User adminUser) {
        movieController.viewMovies(adminUser);
    }
}
