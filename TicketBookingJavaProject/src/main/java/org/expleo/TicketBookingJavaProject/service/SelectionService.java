package org.expleo.TicketBookingJavaProject.service;

import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.City;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;

/**
 * Service class for selection operations.
 * Note: Selection operations are handled in BookingController.
 * This service is kept for backward compatibility.
 */
public class SelectionService {

    /**
     * Gets all cities.
     * Note: Use TheatreRepositoryImpl.getAllCities() instead.
     */
    public List<City> getCities() {
        return BookingRepositoryImpl.getCities();
    }

    /**
     * Gets theatres in a city.
     * Note: Use TheatreRepositoryImpl.getTheatresByCity() instead.
     */
    public List<Theatre> getTheatresByCity(City city) {
        List<Theatre> result = new ArrayList<>();
        List<Theatre> allTheatres = TheatreRepositoryImpl.getAllTheatres();
        
        for (Theatre t : allTheatres) {
            if (t.getCity().equals(city.getName())) {
                result.add(t);
            }
        }
        return result;
    }
}
