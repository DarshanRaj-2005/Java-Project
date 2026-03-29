package org.expleo.TicketBookingJavaProject;

import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.controller.*;

public class App {

    private static Scanner sc = new Scanner(System.in);

    private static AuthController authController = new AuthController();
    private static MovieController movieController = new MovieController();
    private static BookingController bookingController = new BookingController();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MOVIE BOOKING SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Guest");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    authController.register();
                    break;

                case 2:
                    String role = authController.login();

                    if (role.equalsIgnoreCase("admin")) {
                        adminMenu();
                    } else if (role.equalsIgnoreCase("customer")) {
                        customerMenu();
                    } else {
                        System.out.println("Invalid Role!");
                    }
                    break;

                case 3:
                    guestMenu();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ================= CUSTOMER =================
    private static void customerMenu() {

        SearchController searchController =
                new SearchController(movieController.getMovies());

        SelectionController selectionController =
                new SelectionController();

        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Search Movie & Book");
            System.out.println("2. View Movie List");
            System.out.println("3. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    String movie = searchController.searchMovie();
                    if (movie == null) break;

                    String city = selectionController.selectCity();
                    String theatre = selectionController.selectTheatre();
                    String show = selectionController.selectShowTime();

                    System.out.println("\n--- SELECTION SUMMARY ---");
                    System.out.println("Movie   : " + movie);
                    System.out.println("City    : " + city);
                    System.out.println("Theatre : " + theatre);
                    System.out.println("Show    : " + show);

                    System.out.print("Enter number of tickets: ");
                    int count = sc.nextInt();
                    sc.nextLine();

                    bookingController.startBooking(count);
                    break;

                case 2:
                    movieController.viewMovies();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ================= ADMIN =================
    private static void adminMenu() {

        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. View Movie List");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    movieController.addMovie();
                    break;

                case 2:
                    movieController.updateMovie();
                    break;

                case 3:
                    movieController.deleteMovie();
                    break;

                case 4:
                    movieController.viewMovies();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ================= GUEST =================
    private static void guestMenu() {

        SearchController searchController =
                new SearchController(movieController.getMovies());

        while (true) {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. View Movie List");
            System.out.println("3. Back");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    searchController.searchMovie();
                    break;

                case 2:
                    movieController.viewMovies();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}