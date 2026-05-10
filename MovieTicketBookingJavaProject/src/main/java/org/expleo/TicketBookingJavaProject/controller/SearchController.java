/*
 * FILE: SearchController.java
 * PURPOSE: Handles searching for movies.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Simple search interface
 * - Composition: Uses MovieRepositoryImpl
 * - Inheritance: Extends BaseController
 * - Polymorphism: Overrides showMenu() method
 * 
 * WHAT THIS FILE DOES:
 * - Search movies by name, language, genre
 * - Search by city or theatre
 * - Allows selecting a movie for booking
 * 
 * SEARCH OPTIONS:
 * 1. By movie name
 * 2. By language
 * 3. By genre
 * 4. By city
 * 5. By theatre
 */



//------------Author Name: Darshan Raj---------------



package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public class SearchController extends BaseController {

    private MovieRepositoryImpl movieDAO = new MovieRepositoryImpl();
    private TheatreRepositoryImpl theatreDAO = new TheatreRepositoryImpl();
    private MovieController movieController;
    private Movie selectedMovie;

    public SearchController(MovieController movieController) {
        super();
        this.movieController = movieController;
        printInfo("SearchController initialized");
    }

    public SearchController(MovieController movieController, Scanner sharedScanner) {
        super(sharedScanner);
        this.movieController = movieController;
        printInfo("SearchController initialized");
    }

    @Override
    public void showMenu() {
        Movie result = searchMovie(true);
        if (result != null) {
            printSuccess("Movie selected: " + result.getTitle());
        }
    }

    public Movie searchMovie(boolean allowBooking) {
        while (isRunning) {
            printSubHeader("SEARCH OPTIONS");
            System.out.println("1. Search by Movie Name");
            System.out.println("2. Search by Language");
            System.out.println("3. Search by Genre");
            System.out.println("4. Search by City");
            System.out.println("5. Search by Theatre");
            System.out.println("6. Back to Menu");
            
            int choice = getValidChoice(1, 6);
            
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
                    stop();
                    return null;
                default:
                    printError("Invalid choice!");
            }
        }
        return null;
    }

    private Movie searchByMovieName(boolean allowBooking) {
        printSubHeader("SEARCH BY MOVIE NAME");
        System.out.print("Enter Movie Name to search: ");
        String searchQuery = sc.nextLine().trim().toLowerCase();
        
        if (searchQuery.isEmpty()) {
            printError("Please enter a movie name!");
            return null;
        }

        List<Movie> matchingMovies = movieDAO.searchByTitle(searchQuery);
        
        if (matchingMovies.isEmpty()) {
            printInfo("No movies found matching '" + searchQuery + "'");
            return null;
        }

        printSubHeader("MATCHING MOVIES");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre());
        }

        if (allowBooking) {
            return selectMovieForBooking(uniqueMovies);
        }
        
        return null;
    }

    private Movie searchByLanguage(boolean allowBooking) {
        printSubHeader("SEARCH BY LANGUAGE");
        System.out.println("Available Languages:");
        
        Set<String> languages = new HashSet<>();
        List<Movie> allMovies = movieDAO.getAllMovies();
        for (Movie m : allMovies) {
            languages.add(m.getLanguage());
        }
        
        if (languages.isEmpty()) {
            printInfo("No languages available (no movies in system).");
            return null;
        }

        List<String> langList = new ArrayList<>(languages);
        Collections.sort(langList);
        
        for (int i = 0; i < langList.size(); i++) {
            System.out.println((i + 1) + ". " + langList.get(i));
        }
        
        System.out.print("\nSelect Language (Number): ");
        int langChoice = InputUtil.getIntInput(sc);
        
        if (langChoice < 1 || langChoice > langList.size()) {
            printError("Invalid selection!");
            return null;
        }
        
        String selectedLanguage = langList.get(langChoice - 1);
        List<Movie> matchingMovies = movieDAO.searchByLanguage(selectedLanguage);
        
        if (matchingMovies.isEmpty()) {
            printInfo("No movies found in " + selectedLanguage + " language.");
            return null;
        }

        printSubHeader("MOVIES IN " + selectedLanguage.toUpperCase());
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }

        if (allowBooking) {
            return selectMovieForBooking(uniqueMovies);
        }
        
        return null;
    }

    private Movie searchByGenre(boolean allowBooking) {
        printSubHeader("SEARCH BY GENRE");
        System.out.println("Available Genres:");
        
        Set<String> genres = new HashSet<>();
        List<Movie> allMovies = movieDAO.getAllMovies();
        for (Movie m : allMovies) {
            if (m.getGenre() != null && !m.getGenre().isEmpty()) {
                genres.add(m.getGenre());
            }
        }
        
        if (genres.isEmpty()) {
            printInfo("No genres available (no movies in system).");
            return null;
        }

        List<String> genreList = new ArrayList<>(genres);
        Collections.sort(genreList);
        
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println((i + 1) + ". " + genreList.get(i));
        }
        
        System.out.print("\nSelect Genre (Number): ");
        int genreChoice = InputUtil.getIntInput(sc);
        
        if (genreChoice < 1 || genreChoice > genreList.size()) {
            printError("Invalid selection!");
            return null;
        }
        
        String selectedGenre = genreList.get(genreChoice - 1);
        
        List<Movie> matchingMovies = movieDAO.getAllMovies().stream()
            .filter(m -> m.getGenre() != null && m.getGenre().equalsIgnoreCase(selectedGenre))
            .collect(java.util.stream.Collectors.toList());
        
        if (matchingMovies.isEmpty()) {
            printInfo("No movies found in " + selectedGenre + " genre.");
            return null;
        }

        printSubHeader("MOVIES IN " + selectedGenre.toUpperCase() + " GENRE");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getDuration() + " mins");
        }

        if (allowBooking) {
            return selectMovieForBooking(uniqueMovies);
        }
        
        return null;
    }

    private Movie searchByCity(boolean allowBooking) {
        printSubHeader("SEARCH BY CITY");
        
        List<String> cities = theatreDAO.getAllCities();
        
        if (cities.isEmpty()) {
            printInfo("No cities available (no theatres in system).");
            return null;
        }

        System.out.println("Available Cities:");
        Collections.sort(cities);
        
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        System.out.print("\nSelect City (Number): ");
        int cityChoice = InputUtil.getIntInput(sc);
        
        if (cityChoice < 1 || cityChoice > cities.size()) {
            printError("Invalid selection!");
            return null;
        }
        
        String selectedCity = cities.get(cityChoice - 1);
        List<Theatre> theatres = theatreDAO.getTheatresByCity(selectedCity);
        
        if (theatres.isEmpty()) {
            printInfo("No theatres found in " + selectedCity + ".");
            return null;
        }

        Set<Movie> cityMovies = new HashSet<>();
        for (Theatre t : theatres) {
            List<Movie> theatreMovies = movieDAO.getMoviesByTheatre(t.getId());
            cityMovies.addAll(theatreMovies);
        }
        
        if (cityMovies.isEmpty()) {
            printInfo("No movies available in " + selectedCity + ".");
            return null;
        }

        printSubHeader("MOVIES IN " + selectedCity.toUpperCase());
        List<Movie> uniqueMovies = getUniqueMovies(new ArrayList<>(cityMovies));
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
        }

        if (allowBooking) {
            return selectMovieForBooking(uniqueMovies);
        }
        
        return null;
    }

    private Movie searchByTheatre(boolean allowBooking) {
        printSubHeader("SEARCH BY THEATRE");
        
        List<Theatre> allTheatres = theatreDAO.getAllTheatres();
        
        if (allTheatres.isEmpty()) {
            printInfo("No theatres available in the system.");
            return null;
        }

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
            printError("Invalid selection!");
            return null;
        }
        
        Theatre selectedTheatre = theatreList.get(theatreChoice - 1);
        List<Movie> movies = movieDAO.getMoviesByTheatre(selectedTheatre.getId());
        
        if (movies.isEmpty()) {
            printInfo("No movies available in " + selectedTheatre.getName() + ".");
            return null;
        }

        printSubHeader("MOVIES AT " + selectedTheatre.getName().toUpperCase());
        List<Movie> uniqueMovies = getUniqueMovies(movies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
        }

        if (allowBooking) {
            return selectMovieForBooking(uniqueMovies);
        }
        
        return null;
    }

    private Movie selectMovieForBooking(List<Movie> movies) {
        System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();
        
        if (choice.equals("yes")) {
            System.out.print("Select Movie (Number): ");
            int index = InputUtil.getIntInput(sc);
            
            if (index >= 1 && index <= movies.size()) {
                selectedMovie = movies.get(index - 1);
                printSuccess("Movie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                return selectedMovie;
            } else {
                printError("Invalid selection!");
            }
        }
        return null;
    }

    private List<Movie> getUniqueMovies(List<Movie> movies) {
        Map<String, Movie> uniqueMap = new LinkedHashMap<>();
        for (Movie m : movies) {
            String key = m.getTitle().toLowerCase() + "_" + m.getLanguage().toLowerCase();
            uniqueMap.putIfAbsent(key, m);
        }
        return new ArrayList<>(uniqueMap.values());
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }
    
    public void setSelectedMovie(Movie movie) {
        this.selectedMovie = movie;
    }

    public void clearSelectedMovie() {
        selectedMovie = null;
    }

    public List<String> getCitiesWithMovies() {
        Set<String> cities = new HashSet<>();
        List<Movie> movies = movieDAO.getAllMovies();
        
        for (Movie m : movies) {
            Theatre theatre = theatreDAO.getTheatreById(m.getTheatreId());
            if (theatre != null) {
                cities.add(theatre.getCity());
            }
        }
        
        List<String> cityList = new ArrayList<>(cities);
        Collections.sort(cityList);
        return cityList;
    }

    public List<Theatre> getTheatresForSelectedMovie(String city) {
        if (selectedMovie == null) {
            return new ArrayList<>();
        }
        
        List<Theatre> result = new ArrayList<>();
        List<Theatre> allTheatres = theatreDAO.getAllTheatres();
        
        for (Theatre t : allTheatres) {
            List<Movie> theatreMovies = movieDAO.getMoviesByTheatre(t.getId());
            boolean hasMovie = false;
            
            for (Movie m : theatreMovies) {
                if (m.getTitle().equalsIgnoreCase(selectedMovie.getTitle()) &&
                    m.getLanguage().equalsIgnoreCase(selectedMovie.getLanguage())) {
                    hasMovie = true;
                    break;
                }
            }
            
            if (hasMovie) {
                if (city == null || city.isEmpty() || t.getCity().equalsIgnoreCase(city)) {
                    result.add(t);
                }
            }
        }
        
        return result;
    }
}