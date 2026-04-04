package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/**
 * Repository implementation for Movie database operations.
 * Handles all CRUD operations for movies..
 */
public class MovieRepositoryImpl {

    /**
     * Retrieves all movies from the database.
     * @return List of all Movie objects
     */
    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching movies: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Retrieves a movie by its ID.
     * @param movieId The movie ID to search for
     * @return Movie object if found, null otherwise
     */
    public static Movie getMovieById(String movieId) {
        String query = "SELECT * FROM movies WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, movieId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching movie: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new movie to the database.
     * @param movie Movie object to add
     */
    public static void addMovie(Movie movie) {
        String query = "INSERT INTO movies (id, title, genre, language, duration, releaseDate, theatre_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, movie.getId());
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getGenre());
            stmt.setString(4, movie.getLanguage());
            stmt.setInt(5, movie.getDuration());
            stmt.setString(6, movie.getReleaseDate());
            stmt.setInt(7, movie.getTheatreId());
            
            stmt.executeUpdate();
            System.out.println("Movie added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding movie: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing movie in the database.
     * Uses the movie ID to identify which record to update.
     * 
     * @param movieId The ID of the movie to update
     * @param newMovie Movie object with new values
     */
    public static void updateMovie(String movieId, Movie newMovie) {
        String query = "UPDATE movies SET title = ?, genre = ?, language = ?, duration = ?, releaseDate = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newMovie.getTitle());
            stmt.setString(2, newMovie.getGenre());
            stmt.setString(3, newMovie.getLanguage());
            stmt.setInt(4, newMovie.getDuration());
            stmt.setString(5, newMovie.getReleaseDate());
            stmt.setString(6, movieId);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully!");
            } else {
                System.out.println("Movie not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating movie: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a movie from the database.
     * @param movieId The ID of the movie to delete
     */
    public static void deleteMovie(String movieId) {
        String query = "DELETE FROM movies WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, movieId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Movie deleted successfully!");
            } else {
                System.out.println("Movie not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting movie: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Searches movies by title (partial match, case-insensitive).
     * @param title Title to search for
     * @return List of matching movies
     */
    public static List<Movie> searchByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE LOWER(title) LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching movies: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Searches movies by language (exact match, case-insensitive).
     * @param language Language to search for
     * @return List of matching movies
     */
    public static List<Movie> searchByLanguage(String language) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE LOWER(language) = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, language.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching movies: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Searches movies by genre (exact match, case-insensitive).
     * @param genre Genre to search for
     * @return List of matching movies
     */
    public static List<Movie> searchByGenre(String genre) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE LOWER(genre) = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, genre.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching movies by genre: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Retrieves movies by theatre ID.
     * @param theatreId Theatre ID to filter by
     * @return List of movies in the specified theatre
     */
    public static List<Movie> getMoviesByTheatre(int theatreId) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE theatre_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, theatreId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(new Movie(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("language"),
                    rs.getInt("duration"),
                    rs.getString("releaseDate"),
                    rs.getInt("theatre_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching movies by theatre: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }
}
