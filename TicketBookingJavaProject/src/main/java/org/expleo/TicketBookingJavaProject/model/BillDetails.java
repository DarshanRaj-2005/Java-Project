package org.expleo.TicketBookingJavaProject.model;

/**
 * Model class representing Bill Details.
 * Contains the complete breakdown of ticket pricing including GST and fees.
 */
public class BillDetails {

    // Number of tickets booked
    private int ticketCount;
    
    // Total ticket amount before GST and fees
    private double ticketAmount;
    
    // GST percentage applied
    private double gstPercentage;
    
    // GST amount calculated
    private double gstAmount;
    
    // Application fee per ticket
    private double applicationFeePerTicket;
    
    // Total application fee
    private double totalApplicationFee;
    
    // Total amount to pay
    private double totalAmount;

    /**
     * Constructor to initialize bill details.
     */
    public BillDetails(int ticketCount, double ticketAmount, double gstPercentage, 
                       double applicationFeePerTicket) {
        this.ticketCount = ticketCount;
        this.ticketAmount = ticketAmount;
        this.gstPercentage = gstPercentage;
        this.gstAmount = (ticketAmount * gstPercentage) / 100;
        this.applicationFeePerTicket = applicationFeePerTicket;
        this.totalApplicationFee = applicationFeePerTicket * ticketCount;
        this.totalAmount = ticketAmount + gstAmount + totalApplicationFee;
    }

    // Getter methods
    public int getTicketCount() { return ticketCount; }
    public double getTicketAmount() { return ticketAmount; }
    public double getGstPercentage() { return gstPercentage; }
    public double getGstAmount() { return gstAmount; }
    public double getApplicationFeePerTicket() { return applicationFeePerTicket; }
    public double getTotalApplicationFee() { return totalApplicationFee; }
    public double getTotalAmount() { return totalAmount; }

    /**
     * Prints the bill details in a formatted manner.
     */
    public void printBill() {
        System.out.println("\n===========================================");
        System.out.println("           BILL DETAILS                   ");
        System.out.println("===========================================");
        System.out.println("Tickets Booked        : " + ticketCount);
        System.out.println("Ticket Amount         : Rs. " + String.format("%.2f", ticketAmount));
        System.out.println("GST (" + gstPercentage + "%)         : Rs. " + String.format("%.2f", gstAmount));
        System.out.println("Application Fee       : Rs. " + String.format("%.2f", totalApplicationFee));
        System.out.println("-------------------------------------------");
        System.out.println("TOTAL AMOUNT          : Rs. " + String.format("%.2f", totalAmount));
        System.out.println("===========================================");
    }
}
