/*
 * FILE: MovieController.java
 * PURPOSE: Handles all movie-related operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields, public methods
 * - Abstraction: Simple interface for movie operations
 * - Composition: Uses MovieRepositoryImpl
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * 
 * WHAT THIS FILE DOES:
 * - Add new movies to a theatre
 * - Update existing movies
 * - Delete movies
 * - View movie lists
 * 
 * WHO USES THIS:
 * - Theatre Admin (to manage their theatre's movies)
 * - Customers (to view available movies)
 */



//------------Author Name: Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public class MovieController extends BaseController {

    private MovieRepositoryImpl movieDAO = new MovieRepositoryImpl();
    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();

    public MovieController() {
        super();
        printInfo("MovieController initialized");
    }

    public MovieController(Scanner sharedScanner) {
        super(sharedScanner);
        printInfo("MovieController initialized");
    }

    @Override
    public void showMenu() {
        printHeader("MOVIE MANAGEMENT");
        System.out.println("1. View All Movies");
        System.out.println("2. Back");
        
        int choice = getValidChoice(1, 2);
        
        switch (choice) {
            case 1:
                viewMovies();
                break;
            case 2:
                stop();
                break;
            default:
                printError("Invalid option selected");
        }
    }

    private Theatre getAdminTheatre(User adminUser) {
        List<Theatre> theatres = theatreDAO.getAllTheatres();
        for (Theatre t : theatres) {
            if (t.getAdminId() == adminUser.getUserId()) {
                return t;
            }
        }
        return null;
    }

    public void addMovie(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            printError("You are not assigned to any theatre!");
            return;
        }

        printSubHeader("ADD NEW MOVIE to " + theatre.getName());
        
        System.out.print("Enter Movie ID: ");
        String id = sc.nextLine().trim();

        if (movieDAO.getMovieById(id) != null) {
            printError("Movie with this ID already exists!");
            return;
        }

        System.out.print("Enter Title: ");
        String title = sc.nextLine().trim();

        System.out.print("Enter Genre: ");
        String genre = sc.nextLine().trim();

        System.out.print("Enter Language: ");
        String language = sc.nextLine().trim();

        System.out.print("Enter Duration (minutes): ");
        int duration = InputUtil.getIntInput(sc);
        if (duration <= 0) {
            printError("Duration must be a positive number!");
            return;
        }

        System.out.print("Enter Release Date (YYYY-MM-DD): ");
        String releaseDate = sc.nextLine().trim();

        Movie movie = new Movie(id, title, genre, language, duration, releaseDate, theatre.getId());
        movieDAO.addMovie(movie);
        printSuccess("Movie added successfully!");
    }

    public void updateMovie(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            printError("You are not assigned to any theatre!");
            return;
        }

        viewMovies(adminUser);

        List<Movie> theatreMovies = movieDAO.getMoviesByTheatre(theatre.getId());
        if (theatreMovies.isEmpty()) {
            printInfo("No movies available to update.");
            return;
        }

        System.out.print("Enter Movie Number to Update (from list above): ");
        int listIndex = InputUtil.getIntInput(sc);

        if (listIndex < 1 || listIndex > theatreMovies.size()) {
            printError("Invalid selection!");
            return;
        }

        Movie oldMovie = theatreMovies.get(listIndex - 1);
        printSubHeader("UPDATING: " + oldMovie.getTitle() + " (" + oldMovie.getLanguage() + ")");

        System.out.print("Enter New Title (press Enter to keep '" + oldMovie.getTitle() + "'): ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) title = oldMovie.getTitle();

        System.out.print("Enter New Genre (press Enter to keep '" + oldMovie.getGenre() + "'): ");
        String genre = sc.nextLine().trim();
        if (genre.isEmpty()) genre = oldMovie.getGenre();

        System.out.print("Enter New Language (press Enter to keep '" + oldMovie.getLanguage() + "'): ");
        String language = sc.nextLine().trim();
        if (language.isEmpty()) language = oldMovie.getLanguage();

        System.out.print("Enter New Duration (press Enter to keep " + oldMovie.getDuration() + "): ");
        String durInput = sc.nextLine().trim();
        int duration;
        if (durInput.isEmpty()) {
            duration = oldMovie.getDuration();
        } else {
            try {
                duration = Integer.parseInt(durInput);
            } catch (NumberFormatException e) {
                duration = oldMovie.getDuration();
            }
        }

        System.out.print("Enter New Release Date (press Enter to keep '" + oldMovie.getReleaseDate() + "'): ");
        String rd = sc.nextLine().trim();
        if (rd.isEmpty()) rd = oldMovie.getReleaseDate();

        Movie newMovie = new Movie(oldMovie.getId(), title, genre, language, duration, rd, oldMovie.getTheatreId());
        movieDAO.updateMovie(oldMovie.getId(), newMovie);
        printSuccess("Movie updated successfully!");
    }

    public void deleteMovie(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            printError("You are not assigned to any theatre!");
            return;
        }

        viewMovies(adminUser);

        List<Movie> theatreMovies = movieDAO.getMoviesByTheatre(theatre.getId());
        if (theatreMovies.isEmpty()) {
            printInfo("No movies available to delete.");
            return;
        }

        System.out.print("Enter Movie Number to Delete (from list above): ");
        int listIndex = InputUtil.getIntInput(sc);

        if (listIndex < 1 || listIndex > theatreMovies.size()) {
            printError("Invalid selection!");
            return;
        }

        Movie movieToDelete = theatreMovies.get(listIndex - 1);
        
        if (confirmAction("Are you sure you want to delete '" + movieToDelete.getTitle() + "'?")) {
            movieDAO.deleteMovie(movieToDelete.getId());
            printSuccess("Movie deleted successfully!");
        } else {
            printInfo("Deletion cancelled.");
        }
    }

    public void viewMovies(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            printError("No theatre assigned to you.");
            return;
        }
        
        printSubHeader("MOVIE LIST FOR " + theatre.getName().toUpperCase());
        List<Movie> theatreMovies = movieDAO.getMoviesByTheatre(theatre.getId());

        if (theatreMovies.isEmpty()) {
            printInfo("No Movies Available in this theatre.");
            return;
        }

        for (int i = 0; i < theatreMovies.size(); i++) {
            Movie m = theatreMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }
    }

    public void viewMovies() {
        printSubHeader("ALL AVAILABLE MOVIES");
        List<Movie> movies = movieDAO.getAllMovies();

        if (movies.isEmpty()) {
            printInfo("No Movies Available.");
            return;
        }

        Map<String, Movie> uniqueMovies = new LinkedHashMap<>();
        for (Movie m : movies) {
            String key = m.getTitle().toLowerCase() + "_" + m.getLanguage().toLowerCase();
            uniqueMovies.putIfAbsent(key, m);
        }

        List<Movie> displayList = new ArrayList<>(uniqueMovies.values());
        for (int i = 0; i < displayList.size(); i++) {
            Movie m = displayList.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }
    }

    public List<Movie> getMovies() {
        return movieDAO.getAllMovies();
    }
    
    public List<Movie> getMoviesForTheatre(int theatreId) {
        return movieDAO.getMoviesByTheatre(theatreId);
    }
    
    public void viewMoviesForTheatre(int theatreId) {
        List<Movie> movies = movieDAO.getMoviesByTheatre(theatreId);

        if (movies.isEmpty()) {
            printInfo("No Movies Available.");
            return;
        }

        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }
    }
}