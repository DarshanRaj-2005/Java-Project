package org.expleo.TicketBookingJavaProject.model;

public class Movie {
	private int id;
    private String title;

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
