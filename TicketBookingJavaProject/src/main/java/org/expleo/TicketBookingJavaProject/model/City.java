package org.expleo.TicketBookingJavaProject.model;

// Model class representing a City entity
public class City {

    // Unique identifier for the city
    private int id;

    // Name of the city
    private String name;

    // Parameterized constructor to initialize city details
    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter method to retrieve city name
    public String getName() {
        return name;
    }

    // Getter method to retrieve city ID
    public int getId() {
        return id;
    }
}