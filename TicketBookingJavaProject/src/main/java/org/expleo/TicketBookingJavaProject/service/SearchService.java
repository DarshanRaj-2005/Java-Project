package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;

import java.util.*;

public class SearchService {

	public List<Movie> searchByTitle(String title) {

		List<Movie> result = new ArrayList<>();

		for (Movie m : BookingRepositoryImpl.movies) {
			if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
				result.add(m);
			}
		}
		return result;
	}

	public List<Movie> searchByLanguage(String language) {

		List<Movie> result = new ArrayList<>();

		for (Movie m : BookingRepositoryImpl.movies) {
			if (m.getLanguage().equalsIgnoreCase(language)) {
				result.add(m);
			}
		}
		return result;
	}

	public void displayMovies(List<Movie> list) {

		if (list.isEmpty()) {
			System.out.println("No movies found.");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			Movie m = list.get(i);
			System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ")");
		}
	}
}