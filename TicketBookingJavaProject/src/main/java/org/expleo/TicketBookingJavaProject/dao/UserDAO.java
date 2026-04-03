package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.User;

/**
 * Data Access Object interface for User operations.
 * Defines the contract for user-related database operations.
 */
public interface UserDAO {
    
    /**
     * Retrieves all users from the database.
     * @return List of all users
     */
    List<User> getAllUsers();
    
    /**
     * Retrieves a user by their ID.
     * @param userId The unique identifier of the user
     * @return User object if found, null otherwise
     */
    User getUserById(int userId);
    
    /**
     * Retrieves a user by their email.
     * @param email The email address to search for
     * @return User object if found, null otherwise
     */
    User getUserByEmail(String email);
    
    /**
     * Adds a new user to the database.
     * @param user The user object to add
     */
    void addUser(User user);
    
    /**
     * Updates an existing user in the database.
     * @param userId The ID of the user to update
     * @param user The updated user object
     */
    void updateUser(int userId, User user);
    
    /**
     * Deletes a user from the database.
     * @param userId The ID of the user to delete
     */
    void deleteUser(int userId);
    
    /**
     * Updates user profile information.
     * @param user The user object with updated information
     */
    void updateProfile(User user);
    
    /**
     * Authenticates a user with email and password.
     * @param email User's email
     * @param password User's password
     * @return User object if credentials are valid, null otherwise
     */
    User authenticate(String email, String password);
}
