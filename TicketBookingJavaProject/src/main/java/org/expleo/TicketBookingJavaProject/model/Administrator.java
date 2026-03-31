package org.expleo.TicketBookingJavaProject.model;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.service.MovieService;

/*
 * Administrator class inherits from User
 * Represents system admin with special privileges
 * Can add and view movies
 */
public class Administrator extends User { 

    // Scanner for input
    private Scanner sc = new Scanner(System.in);

    // Constructor calling parent (User) constructor
    public Administrator(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }

    // Method to add a new movie
    public void addMovie() {

        System.out.print("Enter Movie ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        System.out.print("Enter Language: ");
        String language = sc.nextLine();

        System.out.print("Enter Duration: ");
        int duration = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Release Date: ");
        String releaseDate = sc.nextLine();

        // Create Movie object
        Movie movie = new Movie(id, title, genre, language, duration, releaseDate);

        // Store in repository instead of local list
        org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl.addMovie(movie);

        System.out.println("Movie added successfully!");
    }

    // Method to view all movies
    public static void viewMovies() {
        MovieService.viewMovies();
    }
}