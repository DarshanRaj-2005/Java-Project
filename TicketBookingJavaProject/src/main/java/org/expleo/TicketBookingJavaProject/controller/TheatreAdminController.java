package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

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
     * Creates a new officer for the theatre.
     */
    public void createOfficer() {
        System.out.println("\n--- CREATE NEW OFFICER ---");
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        // Check if email already exists
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: Email already exists!");
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

        // Create officer
        User officer = new User(0, name, email, phone, password, "Officer");
        UserRepositoryImpl.addUser(officer);
        System.out.println("\nOfficer '" + name + "' created successfully!");
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
