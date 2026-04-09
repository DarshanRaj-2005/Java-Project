/*
 * FILE: MovieRepositoryImpl.java
 * PURPOSE: Handles all movie database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Data Access Object (DAO) Pattern
 * - Static methods for easy access
 * 
 * WHAT THIS FILE DOES:
 * - CRUD operations for movies
 * - Search movies by title, language, genre
 * - Filter movies by theatre
 * 
 * DATABASE TABLE: movies
 */



//------------Author Name: Krishna Prasath, Darshan Raj---------------



package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.expleo.TicketBookingJavaProject.config.DBConnection;
import org.expleo.TicketBookingJavaProject.dao.MovieDAO;
import org.expleo.TicketBookingJavaProject.model.Movie;

/*
 * Repository implementation for Movie database operations.
 * Implements MovieDAO interface for proper DAO pattern.
 */
public class MovieRepositoryImpl implements MovieDAO {

    /*
     * getAllMovies - Gets all movies (DAO implementation)
     */
    @Override
    public List<Movie> getAllMovies() {
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

    /*
     * getMovieById - Gets movie by ID
     */
    @Override
    public Movie getMovieById(String movieId) {
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

    /*
     * addMovie - Adds new movie
     */
    @Override
    public void addMovie(Movie movie) {
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

    /*
     * updateMovie - Updates movie information
     */
    @Override
    public void updateMovie(String movieId, Movie newMovie) {
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

    /*
     * deleteMovie - Removes movie
     */
    @Override
    public void deleteMovie(String movieId) {
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

    /*
     * searchByTitle - Finds movies by title (partial match)
     */
    @Override
    public List<Movie> searchByTitle(String title) {
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

    /*
     * searchByLanguage - Finds movies by language
     */
    @Override
    public List<Movie> searchByLanguage(String language) {
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

    /*
     * searchByGenre - Finds movies by genre
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

    /*
     * getMoviesByTheatre - Gets movies for a specific theatre
     */
    @Override
    public List<Movie> getMoviesByTheatre(int theatreId) {
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
