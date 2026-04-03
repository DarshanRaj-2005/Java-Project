package org.expleo.TicketBookingJavaProject.model;

/**
 * Model class representing a User.
 * Base class for all user types (Super Admin, Theatre Admin, Officer, Customer).
 */
public class User {

    // User ID
    protected int userId;
    
    // User's full name
    protected String name;
    
    // User's email (unique)
    protected String email;
    
    // User's phone number
    protected String phone;
    
    // User's password
    protected String password;
    
    // User role (Super Admin, Theatre Admin, Officer, Customer)
    protected String role;

    /**
     * Constructor to initialize user.
     */
    public User(int userId, String name, String email, String phone, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Getter and Setter methods
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
