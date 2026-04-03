package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Theatre;

/**
 * Data Access Object interface for Theatre operations..
 * Defines the contract for theatre-related database operations.
 */
public interface TheatreDAO {
    
    /**
     * Retrieves all theatres from the database.
     * @return List of all theatres
     */
    List<Theatre> getAllTheatres();
    
    /**
     * Retrieves a theatre by its ID.
     * @param theatreId The unique identifier of the theatre
     * @return Theatre object if found, null otherwise
     */
    Theatre getTheatreById(int theatreId);
    
    /**
     * Adds a new theatre to the database.
     * @param theatre The theatre object to add
     */
    void addTheatre(Theatre theatre);
    
    /**
     * Updates an existing theatre in the database.
     * @param theatreId The ID of the theatre to update
     * @param theatre The updated theatre object
     */
    void updateTheatre(int theatreId, Theatre theatre);
    
    /**
     * Deletes a theatre from the database.
     * @param theatreId The ID of the theatre to delete
     */
    void deleteTheatre(int theatreId);
    
    /**
     * Retrieves theatres by city.
     * @param city The city name to filter by
     * @return List of theatres in the specified city
     */
    List<Theatre> getTheatresByCity(String city);
    
    /**
     * Gets all unique cities from theatres.
     * @return List of city names
     */
    List<String> getAllCities();
    
    /**
     * Updates the admin ID for a theatre.
     * @param theatreId The theatre ID
     * @param adminId The admin user ID to assign
     */
    void updateTheatreAdmin(int theatreId, int adminId);
}
