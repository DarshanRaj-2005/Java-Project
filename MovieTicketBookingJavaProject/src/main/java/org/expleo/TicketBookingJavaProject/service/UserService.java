/*
 * FILE: UserService.java
 * PURPOSE: Handles user-related business logic.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Simple interface
 * - Composition: Uses UserRepositoryImpl
 */



//------------Author Name: Krishna Prasath---------------



package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

public class UserService {

    private UserRepositoryImpl userDAO = UserRepositoryImpl.getInstance();

    /*
     * updateProfile - Updates user profile information
     * 
     * Parameters:
     * - user: User object with updated information
     * - newName: New name (empty = keep current)
     * - newPhone: New phone (empty = keep current)
     * - newPassword: New password (empty = keep current)
     * 
     * Saves changes to the database.
     */
    public void updateProfile(User user, String newName, String newPhone, String newPassword) {
        // Update local object
        if (newName != null && !newName.isEmpty()) {
            user.setName(newName);
        }
        if (newPhone != null && !newPhone.isEmpty()) {
            user.setPhone(newPhone);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        
        // Save to database
        userDAO.updateProfile(user);
    }
}
