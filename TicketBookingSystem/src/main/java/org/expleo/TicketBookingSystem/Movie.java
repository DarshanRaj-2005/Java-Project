package org.expleo.TicketBookingSystem;

public class Movie {

    private int movieId;
    private String title;
    private String genre;
    private String language;
    private int duration;
    private String releaseDate;

    public Movie(int movieId, String title, String genre, String language, int duration, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }
}
