package Models;

public class User {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

    // Constructor
    public User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // To save user as a line in a file (CSV-style)
    public String toCSV() {
        return username + "," + email + "," + password + "," + phoneNumber;
    }

    // To read user from a CSV line
    public static User fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 4) return null;
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
