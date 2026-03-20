package org.expleo.TicketBookingJavaProject.model;
import java.util.List;

public class Theatre {
	 private int id;
	    private String name;
	    private List<Movie> movies;

	    public Theatre(int id, String name, List<Movie> movies) {
	        this.id = id;
	        this.name = name;
	        this.movies = movies;
	    }

	    public String getName() {
	        return name;
	    }

	    public List<Movie> getMovies() {
	        return movies;
	    }
}
