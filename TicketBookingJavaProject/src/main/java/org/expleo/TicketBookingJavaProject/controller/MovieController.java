package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for movie-related operations.
 * Handles adding, updating, deleting, and viewing movies.
 */
public class MovieController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /**
     * Gets the theatre associated with a theatre admin user.
     * @param adminUser The theatre admin user
     * @return Theatre object if found, null otherwise
     */
    private Theatre getAdminTheatre(User adminUser) {
        List<Theatre> theatres = TheatreRepositoryImpl.getAllTheatres();
        for (Theatre t : theatres) {
            if (t.getAdminId() == adminUser.getUserId()) {
                return t;
            }
        }
        return null;
    }

    /**
     * Adds a new movie to the theatre admin's theatre.
     * @param adminUser The theatre admin user performing the action
     */
    public void addMovie(User adminUser) {
        // Get the theatre assigned to this admin
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            System.out.println("Error: You are not assigned to any theatre!");
            return;
        }

        System.out.println("\n--- ADD NEW MOVIE to " + theatre.getName() + " ---");
        
        System.out.print("Enter Movie ID: ");
        String id = sc.nextLine().trim();

        // Check if movie ID already exists
        if (MovieRepositoryImpl.getMovieById(id) != null) {
            System.out.println("Error: Movie with this ID already exists!");
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
            System.out.println("Error: Duration must be a positive number!");
            return;
        }

        System.out.print("Enter Release Date (YYYY-MM-DD): ");
        String releaseDate = sc.nextLine().trim();

        // Create movie object and save to database
        Movie movie = new Movie(id, title, genre, language, duration, releaseDate, theatre.getId());
        MovieRepositoryImpl.addMovie(movie);
    }

    /**
     * Updates an existing movie.
     * @param adminUser The theatre admin user performing the action
     */
    public void updateMovie(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            System.out.println("Error: You are not assigned to any theatre!");
            return;
        }

        // Show movies in this theatre
        viewMovies(adminUser);

        List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(theatre.getId());
        if (theatreMovies.isEmpty()) {
            System.out.println("No movies available to update.");
            return;
        }

        System.out.print("Enter Movie Number to Update (from list above): ");
        int listIndex = InputUtil.getIntInput(sc);

        if (listIndex < 1 || listIndex > theatreMovies.size()) {
            System.out.println("Error: Invalid selection!");
            return;
        }

        // Get the selected movie
        Movie oldMovie = theatreMovies.get(listIndex - 1);
        System.out.println("\nUpdating: " + oldMovie.getTitle() + " (" + oldMovie.getLanguage() + ")");

        // Get new values (press Enter to keep old value)
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

        // Create updated movie and save to database
        Movie newMovie = new Movie(oldMovie.getId(), title, genre, language, duration, rd, oldMovie.getTheatreId());
        MovieRepositoryImpl.updateMovie(oldMovie.getId(), newMovie);
    }

    /**
     * Deletes a movie from the theatre.
     * @param adminUser The theatre admin user performing the action
     */
    public void deleteMovie(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            System.out.println("Error: You are not assigned to any theatre!");
            return;
        }

        // Show movies in this theatre
        viewMovies(adminUser);

        List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(theatre.getId());
        if (theatreMovies.isEmpty()) {
            System.out.println("No movies available to delete.");
            return;
        }

        System.out.print("Enter Movie Number to Delete (from list above): ");
        int listIndex = InputUtil.getIntInput(sc);

        if (listIndex < 1 || listIndex > theatreMovies.size()) {
            System.out.println("Error: Invalid selection!");
            return;
        }

        // Get the selected movie and delete
        Movie movieToDelete = theatreMovies.get(listIndex - 1);
        
        System.out.print("Are you sure you want to delete '" + movieToDelete.getTitle() + "'? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes")) {
            MovieRepositoryImpl.deleteMovie(movieToDelete.getId());
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    /**
     * Views movies for a theatre admin (their theatre only).
     * @param adminUser The theatre admin user
     */
    public void viewMovies(User adminUser) {
        Theatre theatre = getAdminTheatre(adminUser);
        if (theatre == null) {
            System.out.println("Error: No theatre assigned to you.");
            return;
        }
        
        System.out.println("\n--- MOVIE LIST FOR " + theatre.getName().toUpperCase() + " ---");
        List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(theatre.getId());

        if (theatreMovies.isEmpty()) {
            System.out.println("No Movies Available in this theatre.");
            return;
        }

        for (int i = 0; i < theatreMovies.size(); i++) {
            Movie m = theatreMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }
    }

    /**
     * Views all movies (for customers and guests).
     * Shows unique movies without duplicates.
     */
    public void viewMovies() {
        System.out.println("\n--- ALL AVAILABLE MOVIES ---");
        List<Movie> movies = MovieRepositoryImpl.getAllMovies();

        if (movies.isEmpty()) {
            System.out.println("No Movies Available.");
            return;
        }

        // Remove duplicates based on title and language
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

    /**
     * Gets all movies from database (used by SearchController).
     * @return List of all movies
     */
    public List<Movie> getMovies() {
        return MovieRepositoryImpl.getAllMovies();
    }
    
    /**
     * Gets movies for a specific theatre.
     * @param theatreId Theatre ID
     * @return List of movies in that theatre
     */
    public List<Movie> getMoviesForTheatre(int theatreId) {
        return MovieRepositoryImpl.getMoviesByTheatre(theatreId);
    }
    
    /**
     * Views movies for a specific theatre (used by Officers).
     * @param theatreId Theatre ID
     */
    public void viewMoviesForTheatre(int theatreId) {
        List<Movie> movies = MovieRepositoryImpl.getMoviesByTheatre(theatreId);

        if (movies.isEmpty()) {
            System.out.println("No Movies Available.");
            return;
        }

        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }
    }
}
