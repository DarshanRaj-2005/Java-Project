package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Movie;

/**
 * Data Access Object interface for Movie operations.
 * Defines the contract for movie-related database operations.
 */
public interface MovieDAO {
    
    /**
     * Retrieves all movies from the database.
     * @return List of all movies
     */
    List<Movie> getAllMovies();
    
    /**
     * Retrieves a movie by its ID.
     * @param movieId The unique identifier of the movie
     * @return Movie object if found, null otherwise
     */
    Movie getMovieById(String movieId);
    
    /**
     * Adds a new movie to the database.
     * @param movie The movie object to add
     */
    void addMovie(Movie movie);
    
    /**
     * Updates an existing movie in the database.
     * @param movieId The ID of the movie to update
     * @param movie The updated movie object
     */
    void updateMovie(String movieId, Movie movie);
    
    /**
     * Deletes a movie from the database.
     * @param movieId The ID of the movie to delete
     */
    void deleteMovie(String movieId);
    
    /**
     * Retrieves movies by theatre ID.
     * @param theatreId The theatre identifier
     * @return List of movies in the specified theatre
     */
    List<Movie> getMoviesByTheatre(int theatreId);
    
    /**
     * Searches movies by title.
     * @param title The title to search for (partial match)
     * @return List of matching movies
     */
    List<Movie> searchByTitle(String title);
    
    /**
     * Searches movies by language.
     * @param language The language to search for
     * @return List of movies in the specified language
     */
    List<Movie> searchByLanguage(String language);
}
