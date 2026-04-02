package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

// Business logic layer for user profile operations
public class UserService {
    
    // Updates user profile with new values (only non-null, non-empty fields)
    public void updateProfile(User user, String newName, String newPhone, String newPassword) {
        if (newName != null && !newName.isEmpty()) { user.setName(newName); }
        if (newPhone != null && !newPhone.isEmpty()) { user.setPhone(newPhone); }
        if (newPassword != null && !newPassword.isEmpty()) { user.setPassword(newPassword); }
        UserRepositoryImpl.updateProfile(user);
    }
}
