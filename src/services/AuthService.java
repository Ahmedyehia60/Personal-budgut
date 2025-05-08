package services;

import Models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private final String FILE_PATH = "src/data/users.txt";
    private List<User> users;

    private int failedAttempts = 0;
    private long lockoutTime = 0;
    private final int MAX_ATTEMPTS = 3;
    private final int LOCKOUT_DURATION = 60 * 1000; // 1 minute in milliseconds

    public AuthService() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    // Sign-up method with password confirmation and validation
    public boolean register(User newUser, String confirmPassword) {
        if (!newUser.getPassword().equals(confirmPassword)) {
            System.out.println("Password and confirmation do not match.");
            return false;
        }

        if (!isValidEmail(newUser.getEmail()) || !isValidUsername(newUser.getUsername())) {
            System.out.println("Invalid email or username format.");
            return false;
        }

        if (!isValidPassword(newUser.getPassword())) {
            System.out.println("Weak password. It must include uppercase, lowercase, number, and special character.");
            return false;
        }

        if (isEmailTaken(newUser.getEmail()) || isUsernameTaken(newUser.getUsername())) {
            System.out.println("Email or username already exists.");
            return false;
        }

        users.add(newUser);
        saveUserToFile(newUser);
        System.out.println("Verification code sent to phone: 1234 (simulation)");
        return true;
    }

    // Login method with 3-attempt lockout
    public User login(String emailOrUsername, String password) {
        if (System.currentTimeMillis() < lockoutTime) {
            System.out.println("Account is temporarily locked. Try again later.");
            return null;
        }

        for (User user : users) {
            boolean emailMatch = user.getEmail().equalsIgnoreCase(emailOrUsername);
            boolean usernameMatch = user.getUsername().equalsIgnoreCase(emailOrUsername);

            if ((emailMatch || usernameMatch) && user.getPassword().equals(password)) {
                failedAttempts = 0;
                return user;
            }
        }

        failedAttempts++;
        System.out.println("Invalid credentials. Attempt " + failedAttempts + "/" + MAX_ATTEMPTS);

        if (failedAttempts >= MAX_ATTEMPTS) {
            lockoutTime = System.currentTimeMillis() + LOCKOUT_DURATION;
            System.out.println("Account locked for 1 minute due to multiple failed attempts.");
        }

        return null;
    }

    // Email format validation
    public boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    // Password strength validation
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$");
    }

    // Username validation
    public boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_-]{3,50}$");
    }

    private boolean isEmailTaken(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCSV(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}
