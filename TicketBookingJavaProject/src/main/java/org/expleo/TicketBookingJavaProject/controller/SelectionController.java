package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;

public class SelectionController {

    // Scanner object to take user input from console
    private Scanner sc = new Scanner(System.in);

    // Method to display list of cities and return selected city
    public String selectCity() {

        // List of available cities
        List<String> cities = Arrays.asList("Chennai", "Salem", "Coimbatore");

        // Display city options
        System.out.println("\nSelect City:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        // Get user choice
        int choice = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // Return selected city (index adjusted by -1)
        return cities.get(choice - 1);
    }

    // Method to display list of theatres and return selected theatre
    public String selectTheatre() {

        // List of available theatres
        List<String> theatres = Arrays.asList("PVR", "INOX", "AGS");

        // Display theatre options
        System.out.println("\nSelect Theatre:");
        for (int i = 0; i < theatres.size(); i++) {
            System.out.println((i + 1) + ". " + theatres.get(i));
        }

        // Get user choice
        int choice = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // Return selected theatre
        return theatres.get(choice - 1);
    }

    // Method to display available show timings and return selected time
    public String selectShowTime() {

        // List of show timings
        List<String> shows = Arrays.asList("10 AM", "2 PM", "6 PM", "10 PM");

        // Display show time options
        System.out.println("\nSelect Show Time:");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }

        // Get user choice
        int choice = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // Return selected show time
        return shows.get(choice - 1);
    }
}