package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for search operations.
 * Handles searching movies by name or language.
 */
public class SearchController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);
    
    // Reference to MovieController
    private MovieController movieController;
    
    // Currently selected movie for booking
    private Movie selectedMovie;

    public SearchController(MovieController movieController) {
        this.movieController = movieController;
    }

    /**
     * Main search flow - allows user to choose search type and search movies.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    public Movie searchMovie(boolean allowBooking) {
        while (true) {
            System.out.println("\n--- SEARCH OPTIONS ---");
            System.out.println("1. Search by Movie Name");
            System.out.println("2. Search by Language");
            System.out.println("3. Back to Menu");
            
            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);
            
            switch (choice) {
                case 1:
                    return searchByMovieName(allowBooking);
                case 2:
                    return searchByLanguage(allowBooking);
                case 3:
                    return null;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Searches movies by title/name.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    private Movie searchByMovieName(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY MOVIE NAME ---");
        System.out.print("Enter Movie Name to search: ");
        String searchQuery = sc.nextLine().trim().toLowerCase();
        
        if (searchQuery.isEmpty()) {
            System.out.println("Error: Please enter a movie name!");
            return null;
        }

        // Search movies by title
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByTitle(searchQuery);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found matching '" + searchQuery + "'");
            return null;
        }

        // Display unique movies (remove duplicates by title+language)
        System.out.println("\n--- MATCHING MOVIES ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre());
        }

        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /**
     * Searches movies by language.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    private Movie searchByLanguage(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY LANGUAGE ---");
        System.out.println("Available Languages:");
        
        // Get all unique languages from database
        Set<String> languages = new HashSet<>();
        List<Movie> allMovies = MovieRepositoryImpl.getAllMovies();
        for (Movie m : allMovies) {
            languages.add(m.getLanguage());
        }
        
        if (languages.isEmpty()) {
            System.out.println("No languages available (no movies in system).");
            return null;
        }

        // Display languages as options
        List<String> langList = new ArrayList<>(languages);
        Collections.sort(langList);
        
        for (int i = 0; i < langList.size(); i++) {
            System.out.println((i + 1) + ". " + langList.get(i));
        }
        
        System.out.print("\nSelect Language (Number): ");
        int langChoice = InputUtil.getIntInput(sc);
        
        if (langChoice < 1 || langChoice > langList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedLanguage = langList.get(langChoice - 1);
        
        // Search movies by selected language
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByLanguage(selectedLanguage);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found in " + selectedLanguage + " language.");
            return null;
        }

        // Display unique movies
        System.out.println("\n--- MOVIES IN " + selectedLanguage.toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }

        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /**
     * Removes duplicate movies based on title and language combination.
     * @param movies List of movies (may contain duplicates)
     * @return List of unique movies
     */
    private List<Movie> getUniqueMovies(List<Movie> movies) {
        Map<String, Movie> uniqueMap = new LinkedHashMap<>();
        for (Movie m : movies) {
            String key = m.getTitle().toLowerCase() + "_" + m.getLanguage().toLowerCase();
            uniqueMap.putIfAbsent(key, m);
        }
        return new ArrayList<>(uniqueMap.values());
    }

    /**
     * Gets the currently selected movie for booking.
     * @return The selected Movie object
     */
    public Movie getSelectedMovie() {
        return selectedMovie;
    }
    
    /**
     * Sets the selected movie for booking.
     * @param movie The movie to set as selected
     */
    public void setSelectedMovie(Movie movie) {
        this.selectedMovie = movie;
    }

    /**
     * Clears the currently selected movie.
     */
    public void clearSelectedMovie() {
        selectedMovie = null;
    }

    /**
     * Gets all cities where movies are available.
     * @return List of city names
     */
    public List<String> getCitiesWithMovies() {
        Set<String> cities = new HashSet<>();
        List<Movie> movies = MovieRepositoryImpl.getAllMovies();
        
        for (Movie m : movies) {
            Theatre theatre = TheatreRepositoryImpl.getTheatreById(m.getTheatreId());
            if (theatre != null) {
                cities.add(theatre.getCity());
            }
        }
        
        List<String> cityList = new ArrayList<>(cities);
        Collections.sort(cityList);
        return cityList;
    }

    /**
     * Gets theatres that have the selected movie.
     * @param city Optional city filter (can be null for all cities)
     * @return List of theatres showing the movie
     */
    public List<Theatre> getTheatresForSelectedMovie(String city) {
        if (selectedMovie == null) {
            return new ArrayList<>();
        }
        
        List<Theatre> result = new ArrayList<>();
        List<Theatre> allTheatres = TheatreRepositoryImpl.getAllTheatres();
        
        for (Theatre t : allTheatres) {
            // Check if this theatre has the movie
            List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(t.getId());
            boolean hasMovie = false;
            
            for (Movie m : theatreMovies) {
                if (m.getTitle().equalsIgnoreCase(selectedMovie.getTitle()) &&
                    m.getLanguage().equalsIgnoreCase(selectedMovie.getLanguage())) {
                    hasMovie = true;
                    break;
                }
            }
            
            if (hasMovie) {
                // Apply city filter if provided
                if (city == null || city.isEmpty() || t.getCity().equalsIgnoreCase(city)) {
                    result.add(t);
                }
            }
        }
        
        return result;
    }
}
