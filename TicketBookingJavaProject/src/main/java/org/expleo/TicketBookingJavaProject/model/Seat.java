package org.expleo.TicketBookingJavaProject.model;

public class Seat {
	private String row;
    private int number;
    private String status; 

    public Seat(String row, int number, String status) {
        this.row = row;
        this.number = number;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
