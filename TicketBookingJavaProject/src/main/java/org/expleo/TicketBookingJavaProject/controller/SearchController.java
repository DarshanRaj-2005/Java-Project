package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/**
 * Controller for search operations.
 * Handles searching movies by name, language, genre, city, and theatre.
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
            System.out.println("3. Search by Genre");
            System.out.println("4. Search by City");
            System.out.println("5. Search by Theatre");
            System.out.println("6. Back to Menu");
            
            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);
            
            switch (choice) {
                case 1:
                    return searchByMovieName(allowBooking);
                case 2:
                    return searchByLanguage(allowBooking);
                case 3:
                    return searchByGenre(allowBooking);
                case 4:
                    return searchByCity(allowBooking);
                case 5:
                    return searchByTheatre(allowBooking);
                case 6:
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
     * Searches movies by genre.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    private Movie searchByGenre(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY GENRE ---");
        System.out.println("Available Genres:");
        
        // Get all unique genres from database
        Set<String> genres = new HashSet<>();
        List<Movie> allMovies = MovieRepositoryImpl.getAllMovies();
        for (Movie m : allMovies) {
            if (m.getGenre() != null && !m.getGenre().isEmpty()) {
                genres.add(m.getGenre());
            }
        }
        
        if (genres.isEmpty()) {
            System.out.println("No genres available (no movies in system).");
            return null;
        }

        // Display genres as options
        List<String> genreList = new ArrayList<>(genres);
        Collections.sort(genreList);
        
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println((i + 1) + ". " + genreList.get(i));
        }
        
        System.out.print("\nSelect Genre (Number): ");
        int genreChoice = InputUtil.getIntInput(sc);
        
        if (genreChoice < 1 || genreChoice > genreList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedGenre = genreList.get(genreChoice - 1);
        
        // Search movies by selected genre
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByGenre(selectedGenre);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found in " + selectedGenre + " genre.");
            return null;
        }

        // Display unique movies
        System.out.println("\n--- MOVIES IN " + selectedGenre.toUpperCase() + " GENRE ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getDuration() + " mins");
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
     * Searches movies by city.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    private Movie searchByCity(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY CITY ---");
        
        // Get all cities that have theatres with movies
        List<String> cities = TheatreRepositoryImpl.getAllCities();
        
        if (cities.isEmpty()) {
            System.out.println("No cities available (no theatres in system).");
            return null;
        }

        // Display cities as options
        System.out.println("Available Cities:");
        Collections.sort(cities);
        
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        System.out.print("\nSelect City (Number): ");
        int cityChoice = InputUtil.getIntInput(sc);
        
        if (cityChoice < 1 || cityChoice > cities.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedCity = cities.get(cityChoice - 1);
        
        // Get theatres in the selected city
        List<Theatre> theatres = TheatreRepositoryImpl.getTheatresByCity(selectedCity);
        
        if (theatres.isEmpty()) {
            System.out.println("No theatres found in " + selectedCity + ".");
            return null;
        }

        // Get all movies from theatres in this city
        Set<Movie> cityMovies = new HashSet<>();
        for (Theatre t : theatres) {
            List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(t.getId());
            cityMovies.addAll(theatreMovies);
        }
        
        if (cityMovies.isEmpty()) {
            System.out.println("No movies available in " + selectedCity + ".");
            return null;
        }

        // Display unique movies in the city
        System.out.println("\n--- MOVIES IN " + selectedCity.toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(new ArrayList<>(cityMovies));
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
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
     * Searches movies by theatre.
     * @param allowBooking Whether to allow booking after search
     * @return Selected Movie if booking is allowed, null otherwise
     */
    private Movie searchByTheatre(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY THEATRE ---");
        
        // Get all theatres
        List<Theatre> allTheatres = TheatreRepositoryImpl.getAllTheatres();
        
        if (allTheatres.isEmpty()) {
            System.out.println("No theatres available in the system.");
            return null;
        }

        // Group theatres by city for better display
        Map<String, List<Theatre>> theatresByCity = new HashMap<>();
        for (Theatre t : allTheatres) {
            theatresByCity.computeIfAbsent(t.getCity(), k -> new ArrayList<>()).add(t);
        }
        
        System.out.println("Available Theatres:");
        int counter = 1;
        List<Theatre> theatreList = new ArrayList<>();
        
        for (String city : new TreeMap<>(theatresByCity).keySet()) {
            System.out.println("\n--- " + city + " ---");
            for (Theatre t : theatresByCity.get(city)) {
                System.out.println((counter) + ". " + t.getName());
                theatreList.add(t);
                counter++;
            }
        }
        
        System.out.print("\nSelect Theatre (Number): ");
        int theatreChoice = InputUtil.getIntInput(sc);
        
        if (theatreChoice < 1 || theatreChoice > theatreList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        Theatre selectedTheatre = theatreList.get(theatreChoice - 1);
        
        // Get movies in the selected theatre
        List<Movie> movies = MovieRepositoryImpl.getMoviesByTheatre(selectedTheatre.getId());
        
        if (movies.isEmpty()) {
            System.out.println("No movies available in " + selectedTheatre.getName() + ".");
            return null;
        }

        // Display movies in the theatre
        System.out.println("\n--- MOVIES AT " + selectedTheatre.getName().toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(movies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
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
