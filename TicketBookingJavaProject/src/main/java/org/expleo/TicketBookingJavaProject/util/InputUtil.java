package org.expleo.TicketBookingJavaProject.util;

import java.util.Scanner;

/**
 * Utility class for handling user input.
 * Provides safe methods to read different types of input from console.
 */
public class InputUtil {
    
    /**
     * Reads an integer input from the user.
     * Handles empty input and invalid format gracefully.
     * 
     * @param sc Scanner object to read from
     * @return The integer entered by user, or -1 if invalid
     */
    public static int getIntInput(Scanner sc) {
        try {
            String input = sc.nextLine().trim();
            
            // Check for empty input
            if (input.isEmpty()) {
                return -1;
            }
            
            // Parse and return the integer
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Reads a string input from the user.
     * Trims whitespace from the input.
     * 
     * @param sc Scanner object to read from
     * @return The string entered by user (trimmed)
     */
    public static String getStringInput(Scanner sc) {
        return sc.nextLine().trim();
    }
    
    /**
     * Reads a yes/no confirmation from the user.
     * 
     * @param sc Scanner object to read from
     * @param defaultValue Default value if no input is given
     * @return true for yes, false for no
     */
    public static boolean getConfirmation(Scanner sc, boolean defaultValue) {
        String input = sc.nextLine().trim().toLowerCase();
        
        if (input.isEmpty()) {
            return defaultValue;
        }
        
        return input.equals("yes") || input.equals("y");
    }
    
    /**
     * Reads double input from the user.
     * 
     * @param sc Scanner object to read from
     * @return The double entered by user, or -1 if invalid
     */
    public static double getDoubleInput(Scanner sc) {
        try {
            String input = sc.nextLine().trim();
            
            if (input.isEmpty()) {
                return -1;
            }
            
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
