package org.expleo.TicketBookingJavaProject.model;

// Model class representing a Movie entity
public class Movie {

    // Unique identifier for the movie
    private int id;

    // Title of the movie
    private String title;

    // Genre of the movie (e.g., Action, Comedy, Drama)
    private String genre;

    // Language of the movie (e.g., Tamil, English)
    private String language;

    // Duration of the movie in minutes
    private int duration;

    // Release date of the movie (stored as String for simplicity)
    private String releaseDate;

    // Parameterized constructor to initialize all movie details
    public Movie(int id, String title, String genre, String language, int duration, String releaseDate) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    // Getter method to retrieve movie ID
    public int getId() {
        return id;
    }

    // Getter method to retrieve movie title
    public String getTitle() {
        return title;
    }

    // Getter method to retrieve movie genre
    public String getGenre() {
        return genre;
    }

    // Getter method to retrieve movie language
    public String getLanguage() {
        return language;
    }

    // Getter method to retrieve movie duration
    public int getDuration() {
        return duration;
    }

    // Getter method to retrieve movie release date
    public String getReleaseDate() {
        return releaseDate;
    }

    // toString method for easy display of movie details
    @Override
    public String toString() {
        return id + ". " + title + " (" + language + ") | " + genre +
               " | " + duration + " mins | Released: " + releaseDate;
    }
}