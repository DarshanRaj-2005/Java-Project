package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.service.SearchService;

public class SearchController {

	private Scanner sc = new Scanner(System.in);
	private SearchService service = new SearchService();

	public void searchMovie() {

		System.out.println("1. Search by Title");
		System.out.println("2. Search by Language");

		int choice = sc.nextInt();
		sc.nextLine();

		if (choice == 1) {
			System.out.println("Enter title:");
			String title = sc.nextLine();

			List<Movie> result = service.searchByTitle(title);
			service.displayMovies(result);

		} else if (choice == 2) {
			System.out.println("Enter language:");
			String lang = sc.nextLine();

			List<Movie> result = service.searchByLanguage(lang);
			service.displayMovies(result);

		} else {
			System.out.println("Invalid choice");
		}
	}
}
