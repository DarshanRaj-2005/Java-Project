package org.expleo.TicketBookingJavaProject.model;

/**
 * Model class representing a Theatre.
 * Contains information about theatres including name, city, and admin assignment.
 */
public class Theatre {

    // Unique identifier
    private int id;
    
    // Theatre name
    private String name;
    
    // City where theatre is located
    private String city;
    
    // Admin user ID (0 if no admin assigned)
    private int adminId;

    /**
     * Constructor to initialize theatre.
     * @param id Theatre ID
     * @param name Theatre name
     * @param city City name
     */
    public Theatre(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.adminId = 0; // Default: no admin assigned
    }

    // Getter and Setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    /**
     * String representation of theatre.
     */
    @Override
    public String toString() {
        return id + ". " + name + " (" + city + ")";
    }
}
