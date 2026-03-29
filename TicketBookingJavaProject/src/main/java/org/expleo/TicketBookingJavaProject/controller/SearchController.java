package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.service.SearchService;

public class SearchController {

    private Scanner sc = new Scanner(System.in);

    // Service layer object to handle business logic
    private SearchService service = new SearchService();

    // Method to handle movie search options
    public void searchMovie() {

        // Display search options to user
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Language");

        // Take user choice
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        // If user chooses search by title
        if (choice == 1) {
            System.out.println("Enter title:");
            String title = sc.nextLine(); // get title input

            // Call service method to search movies by title
            List<Movie> result = service.searchByTitle(title);

            // Display the search results
            service.displayMovies(result);

        } 
        // If user chooses search by language
        else if (choice == 2) {
            System.out.println("Enter language:");
            String lang = sc.nextLine(); // get language input

            // Call service method to search movies by language
            List<Movie> result = service.searchByLanguage(lang);

            // Display the search results
            service.displayMovies(result);

        } 
        // If user enters invalid choice
        else {
            System.out.println("Invalid choice");
        }
    }
}