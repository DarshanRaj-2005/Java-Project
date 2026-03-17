package org.expleo.TicketBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
	
	    public static List<Movie> movieList = new ArrayList<>();
	    
	    public  static void viewMovies() {
  
	    if (MovieService.movieList.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("Available Movies:");

        for (Movie m : MovieService.movieList) {
            System.out.println(m.getTitle());
        }
	}
}
