package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

/**
 * Service class for user-related operations.
 * Contains business logic for user management.
 */
public class UserService {

    /**
     * Updates user profile information.
     * Saves changes to the database.
     * 
     * @param user User object with updated information
     * @param newName New name (if empty, keeps current name)
     * @param newPhone New phone (if empty, keeps current phone)
     * @param newPassword New password (if empty, keeps current password)
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
        UserRepositoryImpl.updateProfile(user);
    }
}
