package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import java.util.*;

/**
 * Service class for search operations.
 * Note: Search operations are handled in SearchController.
 * This service is kept for potential future business logic.
 */
public class SearchService {

    /**
     * Searches movies by title.
     * Note: SearchController handles the actual search.
     */
    public List<Movie> searchByTitle(String title) {
        return MovieRepositoryImpl.searchByTitle(title);
    }

    /**
     * Searches movies by language.
     * Note: SearchController handles the actual search.
     */
    public List<Movie> searchByLanguage(String language) {
        return MovieRepositoryImpl.searchByLanguage(language);
    }
}
