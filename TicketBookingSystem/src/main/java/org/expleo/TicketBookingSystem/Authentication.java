package org.expleo.TicketBookingSystem;

import org.expleo.TicketBookingSystem.Customer;
import org.expleo.TicketBookingSystem.User;

import java.util.ArrayList;
import java.util.List;

public class Authentication {

    private List<User> users = new ArrayList<>();

    public void register(String name, String email, String phone, String password) {

        if(validateUser(name,email,password)) {
            Customer customer = new Customer(name,email,phone,password);
            users.add(customer);
            System.out.println("Registration Successful");
        }
        else{
            System.out.println("Invalid user details");
        }
    }

    public boolean login(String email,String password){

        for(User user:users){

            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                System.out.println("Login Successful");
                return true;
            }
        }

        System.out.println("Invalid Login");
        return false;
    }

    public boolean adminLogin(String email,String password){

        if(email.equals("admin@system.com") && password.equals("admin123")){
            System.out.println("Admin Login Successful");
            return true;
        }

        System.out.println("Admin Login Failed");
        return false;
    }

    public void guestAccess(){
        System.out.println("Guest Access Granted");
    }

    private boolean validateUser(String name,String email,String password){

        if(name.isEmpty() || email.isEmpty() || password.length()<4){
            return false;
        }

        return true;
    }
}