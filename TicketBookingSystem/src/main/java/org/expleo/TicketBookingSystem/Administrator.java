
package org.expleo.TicketBookingSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User  {
  
    Scanner sc = new Scanner(System.in);

    public Administrator(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }
  
   

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
        MovieService.movieList.add(movie);

        System.out.println("Movie added successfully!");
    }

    public static void viewMovies() {
        MovieService.viewMovies();

    }
}

