package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for selection operations.
 * Handles city and theatre selection from database.
 * Note: This class provides alternative selection methods using database.
 */
public class SelectionController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /**
     * Selects a city from available cities in database.
     * @return Selected city name or null if invalid
     */
    public String selectCity() {
        // Get cities from database
        List<String> cities = TheatreRepositoryImpl.getAllCities();
        
        if (cities.isEmpty()) {
            System.out.println("No cities available.");
            return null;
        }

        System.out.println("\n--- SELECT CITY ---");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        System.out.print("Enter choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice >= 1 && choice <= cities.size()) {
            return cities.get(choice - 1);
        }
        return null;
    }

    /**
     * Selects a theatre from available theatres.
     * @return Selected theatre name or null if invalid
     */
    public String selectTheatre() {
        List<String> theatres = Arrays.asList("PVR", "INOX", "AGS");

        System.out.println("\n--- SELECT THEATRE ---");
        for (int i = 0; i < theatres.size(); i++) {
            System.out.println((i + 1) + ". " + theatres.get(i));
        }

        System.out.print("Enter choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice >= 1 && choice <= theatres.size()) {
            return theatres.get(choice - 1);
        }
        return null;
    }

    /**
     * Selects a showtime.
     * @return Selected showtime or null if invalid
     */
    public String selectShowTime() {
        List<String> shows = Arrays.asList("10:00 AM", "01:30 PM", "06:00 PM", "10:00 PM");

        System.out.println("\n--- SELECT SHOWTIME ---");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }

        System.out.print("Enter choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice >= 1 && choice <= shows.size()) {
            return shows.get(choice - 1);
        }
        return null;
    }
}
