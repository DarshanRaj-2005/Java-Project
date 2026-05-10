/*
 * FILE: SuperAdminController.java
 * PURPOSE: Handles Super Admin operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields and methods
 * - Abstraction: Simple interface for admin operations
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * - Composition: Uses TheatreRepositoryImpl and UserRepositoryImpl
 * 
 * WHAT THIS FILE DOES:
 * - Create and remove theatres
 * - Create and remove theatre admins
 * - Assign admins to theatres
 * - View all theatres
 * 
 * WHO USES THIS:
 * - Super Admin only (role: "Super Admin")
 * 
 * DEFAULT LOGIN:
 * - Email: admin@gmail.com
 * - Password: admin123
 */



//------------Author Name: Tamil Kumar, Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public class SuperAdminController extends BaseController {

    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();
    private UserRepositoryImpl userDAO = UserRepositoryImpl.getInstance();

    public SuperAdminController() {
        super();
        printInfo("SuperAdminController initialized");
    }

    public SuperAdminController(Scanner sharedScanner) {
        super(sharedScanner);
        printInfo("SuperAdminController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("SUPER ADMIN");
        System.out.println("1. Create Theatre");
        System.out.println("2. Create Theatre Admin");
        System.out.println("3. View Theatres");
        System.out.println("4. Remove Theatre");
        System.out.println("5. Remove Theatre Admin");
        System.out.println("6. Back");
        
        int choice = getValidChoice(1, 6);
        
        switch (choice) {
            case 1:
                createTheatre();
                break;
            case 2:
                createTheatreAdmin();
                break;
            case 3:
                viewTheatres();
                break;
            case 4:
                removeTheatre();
                break;
            case 5:
                removeTheatreAdmin();
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

    public void createTheatre() {
        printSubHeader("CREATE NEW THEATRE");
        
        System.out.print("Enter Theatre Name: ");
        String name = sc.nextLine().trim();
        
        System.out.print("Enter City: ");
        String city = sc.nextLine().trim();

        if (name.isEmpty() || city.isEmpty()) {
            printError("Name and City cannot be empty!");
            return;
        }

        Theatre theatre = new Theatre(0, name, city);
        theatreDAO.addTheatre(theatre);
        
        printSuccess("Theatre '" + name + "' created successfully in " + city + "!");
        printInfo("Please create a Theatre Admin and assign them to this Theatre.");
    }

    public void createTheatreAdmin() {
        printSubHeader("CREATE NEW THEATRE ADMIN");
        
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

        if (userDAO.getUserByEmail(email) != null) {
            printError("Email already exists!");
            return;
        }

        User admin = new User(0, name, email, phone, password, "Theatre Admin");
        userDAO.addUser(admin);

        printSuccess("Theatre Admin '" + name + "' created successfully!");
        assignTheatreToAdmin(admin);
    }

    private void assignTheatreToAdmin(User admin) {
        List<String> cities = theatreDAO.getAllCities();
        
        if (cities.isEmpty()) {
            printInfo("No theatres in system yet. Admin created but unassigned.");
            return;
        }

        printSubHeader("ASSIGN THEATRE");
        System.out.println("Available cities:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        System.out.print("Select City (Number): ");
        int cityChoice = InputUtil.getIntInput(sc);
        
        if (cityChoice < 1 || cityChoice > cities.size()) {
            printError("Invalid selection. Admin created but unassigned.");
            return;
        }
        
        String selectedCity = cities.get(cityChoice - 1);
        List<Theatre> cityTheatres = theatreDAO.getTheatresByCity(selectedCity);
        
        System.out.println("\nTheatres in " + selectedCity + ":");
        for (int i = 0; i < cityTheatres.size(); i++) {
            Theatre t = cityTheatres.get(i);
            String status = t.getAdminId() > 0 ? "(Already has admin)" : "(No admin)";
            System.out.println((i + 1) + ". " + t.getName() + " " + status);
        }
        
        System.out.print("Select Theatre (Number): ");
        int theatreChoice = InputUtil.getIntInput(sc);
        
        if (theatreChoice < 1 || theatreChoice > cityTheatres.size()) {
            printError("Invalid selection. Admin created but unassigned.");
            return;
        }
        
        Theatre selected = cityTheatres.get(theatreChoice - 1);
        
        if (selected.getAdminId() > 0) {
            if (!confirmAction("This theatre already has an admin. Replace?")) {
                printInfo("Admin assignment cancelled.");
                return;
            }
        }

        selected.setAdminId(admin.getUserId());
        theatreDAO.updateTheatreAdmin(selected.getId(), admin.getUserId());
        printSuccess("Admin '" + admin.getName() + "' assigned to '" + selected.getName() + "'.");
    }

    public void removeTheatre() {
        printSubHeader("REMOVE THEATRE");
        
        List<Theatre> theatres = theatreDAO.getAllTheatres();
        if (theatres.isEmpty()) {
            printInfo("No theatres found.");
            return;
        }

        viewTheatres();
        
        System.out.print("Enter Theatre ID to remove: ");
        int id = InputUtil.getIntInput(sc);
        
        if (id <= 0) {
            printError("Invalid theatre ID!");
            return;
        }
        
        if (confirmAction("Are you sure you want to remove this theatre?")) {
            theatreDAO.deleteTheatre(id);
            printSuccess("Theatre removed successfully.");
        } else {
            printInfo("Removal cancelled.");
        }
    }

    public void removeTheatreAdmin() {
        printSubHeader("REMOVE THEATRE ADMIN");
        
        List<User> admins = userDAO.getAllUsers().stream()
            .filter(u -> u.getRole().equals("Theatre Admin"))
            .collect(Collectors.toList());
        
        if (admins.isEmpty()) {
            printInfo("No Theatre Admins found.");
            return;
        }
        
        System.out.println("Available Theatre Admins:");
        for (User u : admins) {
            System.out.println(u.getUserId() + ". " + u.getName() + " (" + u.getEmail() + ")");
        }
        
        System.out.print("Enter Admin User ID to remove: ");
        int id = InputUtil.getIntInput(sc);
        
        if (id <= 0) {
            printError("Invalid user ID!");
            return;
        }
        
        if (confirmAction("Are you sure you want to remove this admin?")) {
            userDAO.deleteUser(id);
            printSuccess("Admin removed successfully.");
        } else {
            printInfo("Removal cancelled.");
        }
    }

    public void viewTheatres() {
        printSubHeader("THEATRE LIST");
        
        List<Theatre> theatres = theatreDAO.getAllTheatres();
        if (theatres.isEmpty()) {
            printInfo("No theatres found.");
            return;
        }

        for (Theatre t : theatres) {
            String adminName = "Not Assigned";
            User admin = userDAO.getAllUsers().stream()
                .filter(u -> u.getUserId() == t.getAdminId())
                .findFirst().orElse(null);
            if (admin != null) {
                adminName = admin.getName();
            }
            System.out.println("ID: " + t.getId() + " | Name: " + t.getName() + " | City: " + t.getCity() + " | Admin: " + adminName);
        }
    }
}