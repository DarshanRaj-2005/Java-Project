package org.expleo.TicketBookingJavaProject.model;

import java.time.LocalTime;

// Model class representing a Showtime entity
public class Showtime {

    // Unique identifier for the showtime
    private int id;

    // Time of the show (using LocalTime for better handling)
    private LocalTime time;

    // Movie associated with this showtime
    private Movie movie;

    // Theatre where this show is running
    private Theatre theatre;

    // Parameterized constructor to initialize all fields
    public Showtime(int id, LocalTime time, Movie movie, Theatre theatre) {
        this.id = id;
        this.time = time;
        this.movie = movie;
        this.theatre = theatre;
    }

    // Getter method to retrieve showtime ID
    public int getId() {
        return id;
    }

    // Getter method to retrieve showtime time
    public LocalTime getTime() {
        return time;
    }

    // Getter method to retrieve movie
    public Movie getMovie() {
        return movie;
    }

    // Getter method to retrieve theatre
    public Theatre getTheatre() {
        return theatre;
    }

    // toString method for easy display
    @Override
    public String toString() {
        return id + ". " + time + " - " + movie.getTitle() + " @ " + theatre.getName();
    }
}