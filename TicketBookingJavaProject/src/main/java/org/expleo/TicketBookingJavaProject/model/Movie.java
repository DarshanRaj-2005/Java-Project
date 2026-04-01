package org.expleo.TicketBookingJavaProject.model;

/**
 * Model class representing a Movie.
 * Contains all information about a movie.
 */
public class Movie {

    // Unique identifier for the movie
    private String id;
    
    // Title of the movie
    private String title;
    
    // Genre (Action, Comedy, Drama, etc.)
    private String genre;
    
    // Language (Tamil, English, Hindi, etc.)
    private String language;
    
    // Duration in minutes
    private int duration;
    
    // Release date (stored as String)
    private String releaseDate;
    
    // Theatre ID where this movie is playing
    private int theatreId;

    /**
     * Constructor to initialize all movie details.
     */
    public Movie(String id, String title, String genre, String language, int duration, String releaseDate, int theatreId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.theatreId = theatreId;
    }

    // Getter methods
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
    public int getDuration() { return duration; }
    public String getReleaseDate() { return releaseDate; }
    public int getTheatreId() { return theatreId; }

    /**
     * String representation of movie.
     */
    @Override
    public String toString() {
        return title + " (" + language + ") | " + genre + " | " + duration + " mins";
    }
}
