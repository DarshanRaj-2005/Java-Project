/*
 * FILE: BaseController.java
 * PURPOSE: Abstract base class for all controllers
 * 
 * OOPS CONCEPTS USED:
 * - Abstraction: Provides common functionality for all controllers
 * - Encapsulation: Protected fields with controlled access
 * - Inheritance: All controllers extend this class
 * - Polymorphism: Abstract methods implemented by subclasses
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

public abstract class BaseController {

    protected Scanner sc;
    protected String controllerName;
    protected boolean isRunning;

    public BaseController() {
        this.sc = new Scanner(System.in);
        this.controllerName = this.getClass().getSimpleName();
        this.isRunning = true;
    }

    public BaseController(Scanner sharedScanner) {
        this.sc = sharedScanner;
        this.controllerName = this.getClass().getSimpleName();
        this.isRunning = true;
    }

    protected int getValidChoice(int min, int max) {
        int choice = InputUtil.getIntInput(sc);
        if (choice < min || choice > max) {
            printError("Invalid choice. Please enter between " + min + " and " + max);
            return -1;
        }
        return choice;
    }

    protected boolean confirmAction(String message) {
        System.out.print(message + " (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    protected void printHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("         " + title);
        System.out.println("========================================");
    }

    protected void printSubHeader(String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
    }

    protected void printSeparator() {
        System.out.println("-------------------------------------------");
    }

    protected void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    protected void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    protected void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    protected void printMenu(String[] options) {
        System.out.println("\n--- " + controllerName + " MENU ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public abstract void showMenu();

    public void stop() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public String getControllerName() {
        return this.controllerName;
    }
}