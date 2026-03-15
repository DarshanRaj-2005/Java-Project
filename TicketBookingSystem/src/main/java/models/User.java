package models;

public class User {
	    protected int userId;
	    protected String name;
	    protected String email;
	    protected String phone;
	    protected String password;

	    public User(String name, String email, String phone, String password) {
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.password = password;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public String getPassword() {
	        return password;
	    }
}
