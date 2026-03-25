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
    
    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }
    
    public String getSeatLabel() {
        return row + number;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}