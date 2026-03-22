package org.expleo.TicketBookingJavaProject.model;

public class Movie {
	private int id;
	private String title;
	private String language;

	public Movie(int id, String title, String language) {
		this.id = id;
		this.title = title;
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public String getLanguage() {
		return language;
	}

	public int getId() {
		return id;
	}
}
