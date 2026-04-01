package org.expleo.TicketBookingJavaProject;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.controller.AuthController;
import org.expleo.TicketBookingJavaProject.controller.BookingController;
import org.expleo.TicketBookingJavaProject.controller.MovieController;
import org.expleo.TicketBookingJavaProject.controller.OfficerController;
import org.expleo.TicketBookingJavaProject.controller.SearchController;
import org.expleo.TicketBookingJavaProject.controller.SuperAdminController;
import org.expleo.TicketBookingJavaProject.controller.TheatreAdminController;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.service.UserService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Main Application class for the Online Ticket Booking System.
 * Entry point of the application.
 */
public class App {

    // Scanner for user input
    private static Scanner sc;

    // Controllers for different operations
    private static AuthController authController;
    private static MovieController movieController;
    private static SearchController searchController;
    private static BookingController bookingController;
    private static SuperAdminController superAdminController;
    private static TheatreAdminController theatreAdminController;
    private static OfficerController officerController;
    
    // Services
    private static UserService userService;

    // Static initializer - sets up all controllers and database
    static {
        sc = new Scanner(System.in);
        
        // Initialize controllers
        authController = new AuthController();
        movieController = new MovieController();
        searchController = new SearchController(movieController);
        bookingController = new BookingController(searchController, movieController);
        superAdminController = new SuperAdminController();
        theatreAdminController = new TheatreAdminController(movieController);
        officerController = new OfficerController(movieController, bookingController);
        userService = new UserService();
        
        // Initialize database tables
        org.expleo.TicketBookingJavaProject.config.DatabaseSetup.initialize();
    }

    /**
     * Main method - starts the application.
     */
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("   WELCOME TO ONLINE TICKET BOOKING");
        System.out.println("========================================");

        // Main menu loop
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Register (Customer only)");
            System.out.println("2. Login");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1:
                    // Register new customer
                    authController.register();
                    break;

                case 2:
                    // Login and route to appropriate menu
                    User user = authController.login();
                    if (user != null) {
                        routeToUserMenu(user);
                    }
                    break;

                case 3:
                    // Guest menu (limited features)
                    guestMenu();
                    break;

                case 4:
                    // Exit application
                    System.out.println("\nThank you for using the system. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Routes user to their role-specific menu.
     * @param user Logged in user object
     */
    private static void routeToUserMenu(User user) {
        String role = user.getRole();
        
        switch (role.toLowerCase()) {
            case "super admin":
                superAdminMenu(user);
                break;
            case "theatre admin":
                theatreAdminMenu(user);
                break;
            case "officer":
                officerMenu(user);
                break;
            case "customer":
                customerMenu(user);
                break;
            default:
                System.out.println("Unknown role: " + role);
        }
    }

    // ==================== SUPER ADMIN MENU ====================
    /**
     * Menu for Super Admin users.
     * Can manage theatres and theatre admins.
     */
    private static void superAdminMenu(User user) {
        while (true) {
            System.out.println("\n--- SUPER ADMIN MENU ---");
            System.out.println("1. Create Theatre");
            System.out.println("2. Create Theatre Admin");
            System.out.println("3. View Theatres");
            System.out.println("4. Remove Theatre");
            System.out.println("5. Remove Theatre Admin");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1: superAdminController.createTheatre(); break;
                case 2: superAdminController.createTheatreAdmin(); break;
                case 3: superAdminController.viewTheatres(); break;
                case 4: superAdminController.removeTheatre(); break;
                case 5: superAdminController.removeTheatreAdmin(); break;
                case 6: viewProfile(user); break;
                case 7: updateProfile(user); break;
                case 8: 
                    System.out.println("Logged out successfully!");
                    return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== THEATRE ADMIN MENU ====================
    /**
     * Menu for Theatre Admin users.
     * Can manage movies in their theatre.
     */
    private static void theatreAdminMenu(User user) {
        while (true) {
            System.out.println("\n--- THEATRE ADMIN MENU ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. Create Officer");
            System.out.println("5. View Movie List");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1: theatreAdminController.addMovie(user); break;
                case 2: theatreAdminController.updateMovie(user); break;
                case 3: theatreAdminController.deleteMovie(user); break;
                case 4: theatreAdminController.createOfficer(); break;
                case 5: theatreAdminController.viewMovies(user); break;
                case 6: viewProfile(user); break;
                case 7: updateProfile(user); break;
                case 8: 
                    System.out.println("Logged out successfully!");
                    return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== OFFICER MENU ====================
    /**
     * Menu for Officer users.
     * Can view movies and manage bookings.
     */
    private static void officerMenu(User user) {
        while (true) {
            System.out.println("\n--- OFFICER MENU ---");
            System.out.println("1. View Movie List");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1: officerController.viewMovies(); break;
                case 2: officerController.bookTicket(); break;
                case 3: officerController.cancelTicket(); break;
                case 4: 
                    System.out.println("Logged out successfully!");
                    return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== CUSTOMER MENU ====================
    /**
     * Menu for Customer users.
     * Full access to search, book, and manage bookings.
     */
    private static void customerMenu(User user) {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Movie List");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. View Profile");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1: searchController.searchMovie(true); break;
                case 2: bookingController.startBooking(-1); break;
                case 3: movieController.viewMovies(); break;
                case 4: bookingController.cancelBooking(); break;
                case 5: viewProfile(user); break;
                case 6: updateProfile(user); break;
                case 7: 
                    System.out.println("Logged out successfully!");
                    return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== GUEST MENU ====================
    /**
     * Menu for guest users (not logged in).
     * Limited features - can only search and view movies.
     */
    private static void guestMenu() {
        while (true) {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. View Movie List");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
                case 1: searchController.searchMovie(false); break;
                case 2: movieController.viewMovies(); break;
                case 3: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== PROFILE MANAGEMENT ====================
    /**
     * Displays user profile information.
     */
    private static void viewProfile(User user) {
        System.out.println("\n--- YOUR PROFILE ---");
        System.out.println("User ID : " + user.getUserId());
        System.out.println("Name    : " + user.getName());
        System.out.println("Email   : " + user.getEmail());
        System.out.println("Phone   : " + user.getPhone());
        System.out.println("Role    : " + user.getRole());

        // Show theatre info for Theatre Admins
        if (user.getRole().equalsIgnoreCase("Theatre Admin")) {
            org.expleo.TicketBookingJavaProject.model.Theatre theatre = 
                org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl.getAllTheatres().stream()
                .filter(t -> t.getAdminId() == user.getUserId())
                .findFirst().orElse(null);
            
            if (theatre != null) {
                System.out.println("Theatre : " + theatre.getName() + " (" + theatre.getCity() + ")");
            } else {
                System.out.println("Theatre : Not Assigned");
            }
        }
    }

    /**
     * Updates user profile information.
     */
    private static void updateProfile(User user) {
        System.out.println("\n--- UPDATE PROFILE ---");
        
        System.out.print("Enter New Name (press Enter to keep '" + user.getName() + "'): ");
        String name = sc.nextLine().trim();
        
        System.out.print("Enter New Phone (press Enter to keep '" + user.getPhone() + "'): ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter New Password (press Enter to keep current): ");
        String password = sc.nextLine().trim();

        userService.updateProfile(user, name, phone, password);
    }
}
