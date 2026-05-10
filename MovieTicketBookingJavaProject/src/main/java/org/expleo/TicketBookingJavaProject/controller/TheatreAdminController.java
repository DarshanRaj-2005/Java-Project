/*
 * FILE: TheatreAdminController.java
 * PURPOSE: Handles Theatre Admin operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Composition: Uses MovieController
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * 
 * WHAT THIS FILE DOES:
 * - Add, update, delete movies in their theatre
 * - Create officers for their theatre
 * - View movies in their theatre
 * 
 * WHO USES THIS:
 * - Theatre Admin only (role: "Theatre Admin")
 * 
 * NOTE: Theatre Admin can only manage movies in their assigned theatre.
 */



//------------Author Name: Tamil Kumar, Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

public class TheatreAdminController extends BaseController {

    private UserRepositoryImpl userDAO = UserRepositoryImpl.getInstance();
    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();
    private MovieController movieController;

    public TheatreAdminController(MovieController movieController) {
        super();
        this.movieController = movieController;
        printInfo("TheatreAdminController initialized");
    }

    public TheatreAdminController(MovieController movieController, Scanner sharedScanner) {
        super(sharedScanner);
        this.movieController = movieController;
        printInfo("TheatreAdminController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("THEATRE ADMIN");
        System.out.println("1. Create Officer");
        System.out.println("2. Add Movie");
        System.out.println("3. Update Movie");
        System.out.println("4. Delete Movie");
        System.out.println("5. View Movies");
        System.out.println("6. Back");
        
        int choice = getValidChoice(1, 6);
        
        switch (choice) {
            case 1:
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                printInfo("Please provide admin user context for this operation.");
                stop();
                break;
            case 6:
                stop();
                break;
            default:
                printError("Invalid option");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@");
    }

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

    public void createOfficer(User adminUser) {
        Theatre theatre = theatreDAO.getAllTheatres().stream()
            .filter(t -> t.getAdminId() == adminUser.getUserId())
            .findFirst().orElse(null);
        
        if (theatre == null) {
            printError("You are not assigned to any theatre!");
            return;
        }
        
        printSubHeader("CREATE NEW OFFICER for " + theatre.getName());
        printInfo("This officer will only be able to book tickets for " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        if (name.isEmpty()) {
            printError("Name cannot be empty!");
            return;
        }
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        if (!isValidEmail(email)) {
            printError("Email must contain '@' symbol!");
            return;
        }
        
        if (userDAO.getUserByEmail(email) != null) {
            printError("Email already exists!");
            return;
        }

        System.out.print("Enter Phone (10 digits): ");
        String phone = sc.nextLine().trim();
        
        if (!isValidPhone(phone)) {
            printError("Phone number must be exactly 10 digits!");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        if (password.isEmpty()) {
            printError("Password cannot be empty!");
            return;
        }

        User officer = new User(0, name, email, phone, password, "Officer");
        officer.setTheatreId(theatre.getId());
        userDAO.addUser(officer);
        
        printSuccess("Officer '" + name + "' created successfully!");
        printInfo("Assigned to: " + theatre.getName() + " (" + theatre.getCity() + ")");
    }

    public void addMovie(User adminUser) {
        movieController.addMovie(adminUser);
    }

    public void updateMovie(User adminUser) {
        movieController.updateMovie(adminUser);
    }

    public void deleteMovie(User adminUser) {
        movieController.deleteMovie(adminUser);
    }

    public void viewMovies(User adminUser) {
        movieController.viewMovies(adminUser);
    }
}