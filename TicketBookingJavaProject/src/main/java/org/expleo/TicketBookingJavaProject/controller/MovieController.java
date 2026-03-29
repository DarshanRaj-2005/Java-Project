package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;

public class MovieController {

    private Scanner sc = new Scanner(System.in);

    // In-memory storage (MVP)
    private List<String> movies = new ArrayList<>();

    // Constructor
    public MovieController() {
        movies.add("Leo");
        movies.add("Jailer");
        movies.add("Vikram");
    }

    // Add Movie
    public void addMovie() {
        System.out.print("Enter Movie Name: ");
        String movie = sc.nextLine();

        movies.add(movie);
        System.out.println("Movie Added Successfully!");
    }

    // Update Movie
    public void updateMovie() {
        viewMovies();

        if (movies.isEmpty()) return;

        System.out.print("Enter Movie Index to Update: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > movies.size()) {
            System.out.println("Invalid Index!");
            return;
        }

        System.out.print("Enter New Movie Name: ");
        String newName = sc.nextLine();

        movies.set(index - 1, newName);
        System.out.println("Movie Updated Successfully!");
    }

    // Delete Movie
    public void deleteMovie() {
        viewMovies();

        if (movies.isEmpty()) return;

        System.out.print("Enter Movie Index to Delete: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > movies.size()) {
            System.out.println("Invalid Index!");
            return;
        }

        movies.remove(index - 1);
        System.out.println("Movie Deleted Successfully!");
    }

    // View Movies
    public void viewMovies() {
        System.out.println("\n--- MOVIE LIST ---");

        if (movies.isEmpty()) {
            System.out.println("No Movies Available.");
            return;
        }

        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
    }

    // IMPORTANT → used by SearchController
    public List<String> getMovies() {
        return movies;
    }
}