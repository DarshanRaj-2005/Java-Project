/*
 * FILE: Administrator.java
 * PURPOSE: Represents a system administrator (extends User class).
 * AUTHOR: KRISHNAPRASATH B
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends User class
 * - Composition: Uses MovieService and MovieRepositoryImpl
 * 
 * Note: This class is not actively used. Admins are User objects with appropriate roles.
 */
package org.expleo.TicketBookingJavaProject.model;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.service.MovieService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * WHAT THIS CLASS DOES:
 * Represents an administrator who can manage movies.
 * Inherits from User class.
 */
public class Administrator extends User { 

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /*
     * Constructor - Creates an Administrator
     */
    public Administrator(int userId, String name, String email, String phone, String password, String role) {
        super(userId, name, email, phone, password, role);
    }

    /*
     * addMovie - Creates a new movie entry
     * 
     * Asks admin for movie details and saves to database.
     */
    public void addMovie() {
        System.out.print("Enter Movie ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        System.out.print("Enter Language: ");
        String language = sc.nextLine();

        System.out.print("Enter Duration: ");
        int duration = InputUtil.getIntInput(sc);

        System.out.print("Enter Release Date: ");
        String releaseDate = sc.nextLine();

        // Create movie and save to database
        Movie movie = new Movie(id, title, genre, language, duration, releaseDate, 0);
        org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl.addMovie(movie);

        System.out.println("Movie added successfully!");
    }

    /*
     * viewMovies - Shows all movies
     */
    public static void viewMovies() {
        MovieService.viewMovies();
    }
}
