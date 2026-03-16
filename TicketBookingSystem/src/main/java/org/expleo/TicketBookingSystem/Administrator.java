package org.expleo.TicketBookingSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator {

    List<Movie> movieList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addMovie() {

        System.out.print("Enter Movie ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        System.out.print("Enter Language: ");
        String language = sc.nextLine();

        System.out.print("Enter Duration: ");
        int duration = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Release Date: ");
        String releaseDate = sc.nextLine();

        Movie movie = new Movie(id, title, genre, language, duration, releaseDate);
        movieList.add(movie);

        System.out.println("Movie added successfully!");
    }

    public void viewMovies() {

        if (movieList.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("Available Movies:");

        for (Movie m : movieList) {
            System.out.println(m.getTitle());
        }
    }
}
