package org.expleo.TicketBookingSystem;

/**
 * Hello world!
 *
 */


import service.AuthService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        while(true){

            System.out.println("1 Register");
            System.out.println("2 Login");
            System.out.println("3 Admin Login");
            System.out.println("4 Guest Access");
            System.out.println("5 Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    System.out.println("Enter name");
                    String name = sc.nextLine();

                    System.out.println("Enter email");
                    String email = sc.nextLine();

                    System.out.println("Enter phone");
                    String phone = sc.nextLine();

                    System.out.println("Enter password");
                    String password = sc.nextLine();

                    authService.register(name,email,phone,password);
                    break;

                case 2:
                    System.out.println("Enter email");
                    String loginEmail = sc.nextLine();

                    System.out.println("Enter password");
                    String loginPassword = sc.nextLine();

                    authService.login(loginEmail,loginPassword);
                    break;

                case 3:
                    System.out.println("Enter admin email");
                    String adminEmail = sc.nextLine();

                    System.out.println("Enter admin password");
                    String adminPassword = sc.nextLine();

                    authService.adminLogin(adminEmail,adminPassword);
                    break;

                case 4:
                    authService.guestAccess();
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }
}