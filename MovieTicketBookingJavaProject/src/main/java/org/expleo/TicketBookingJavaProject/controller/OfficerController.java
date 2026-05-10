/*
 * FILE: OfficerController.java
 * PURPOSE: Handles Officer operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Composition: Uses MovieController and BookingController
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * 
 * WHAT THIS FILE DOES:
 * - View movies in their assigned theatre
 * - Book tickets (only for their theatre)
 * - Cancel tickets
 * 
 * WHO USES THIS:
 * - Officer only (role: "Officer")
 * 
 * IMPORTANT:
 * Officers can ONLY book tickets for their assigned theatre.
 * They cannot view or manage movies in other theatres.
 */



//------------Author Name: Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;

public class OfficerController extends BaseController {

    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();
    private MovieController movieController;
    private BookingController bookingController;

    public OfficerController(MovieController movieController, BookingController bookingController) {
        super();
        this.movieController = movieController;
        this.bookingController = bookingController;
        printInfo("OfficerController initialized");
    }

    public OfficerController(MovieController movieController, BookingController bookingController, Scanner sharedScanner) {
        super(sharedScanner);
        this.movieController = movieController;
        this.bookingController = bookingController;
        printInfo("OfficerController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("OFFICER");
        System.out.println("1. View Movies");
        System.out.println("2. Book Ticket");
        System.out.println("3. Cancel Ticket");
        System.out.println("4. Back");
        
        int choice = getValidChoice(1, 4);
        
        switch (choice) {
            case 1:
                printInfo("Please provide officer user context for viewing movies.");
                break;
            case 2:
                printInfo("Please provide officer user context for booking.");
                break;
            case 3:
                cancelTicket();
                break;
            case 4:
                stop();
                break;
            default:
                printError("Invalid option");
        }
    }

    public void viewMovies(User officerUser) {
        Theatre theatre = theatreDAO.getTheatreById(officerUser.getTheatreId());
        if (theatre != null) {
            printSubHeader("MOVIES AT " + theatre.getName() + " (" + theatre.getCity() + ")");
            movieController.viewMoviesForTheatre(theatre.getId());
        } else {
            printError("No theatre assigned to you!");
        }
    }

    public void viewAllMovies() {
        movieController.viewMovies();
    }

    public void bookTicket(User officerUser) {
        Theatre theatre = theatreDAO.getTheatreById(officerUser.getTheatreId());
        
        if (theatre == null) {
            printError("You are not assigned to any theatre!");
            printInfo("Please contact your Theatre Admin.");
            return;
        }
        
        printSubHeader("BOOKING TICKET");
        System.out.println("Booking for: " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        bookingController.startBookingForTheatre(theatre, officerUser.getUserId());
    }

    public void cancelTicket() {
        bookingController.cancelBooking(0);
    }
}