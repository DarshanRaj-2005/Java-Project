package org.expleo.TicketBookingJavaProject.model;

public class Movie {
	private int id;
	private String title;
	private String language;
	private String genre;
	private int duration;
	private String releaseDate;

	public Movie(int id, String title, String language) {
		this.id = id;
		this.title = title;
		this.language = language;
	}
    public Movie(int movieId, String title, String genre, String language, int duration, String releaseDate) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
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
