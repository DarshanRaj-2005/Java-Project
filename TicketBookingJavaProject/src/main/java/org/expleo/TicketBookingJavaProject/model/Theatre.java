package org.expleo.TicketBookingJavaProject.model;

import java.util.List;

// Model class representing a Theatre entity
public class Theatre {

    // Unique identifier for the theatre
    private int id;

    // Name of the theatre
    private String name;

    // List of movies available in this theatre
    private List<Movie> movies;

    // Parameterized constructor to initialize theatre details
    public Theatre(int id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    // Getter method to retrieve theatre ID
    public int getId() {
        return id;
    }

    // Getter method to retrieve theatre name
    public String getName() {
        return name;
    }

    // Getter method to retrieve list of movies in this theatre
    public List<Movie> getMovies() {
        return movies;
    }

    // toString method for easy display of theatre details
    @Override
    public String toString() {
        return id + ". " + name;
    }
}