import Models.User;
import services.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            System.out.println("\n=== Personal Budgeting App ===");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    System.out.print("Confirm password: ");
                    String confirmPassword = scanner.nextLine();

                    User newUser = new User(username, email, password, phone);
                    boolean registered = authService.register(newUser, confirmPassword);

                    if (registered) {
                        System.out.println("Sign Up successful!");
                    } else {
                        System.out.println("Sign Up failed.");
                    }
                    break;

                case "2":
                    System.out.print("Enter email or username: ");
                    String loginInput = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    currentUser = authService.login(loginInput, loginPassword);

                    if (currentUser != null) {
                        System.out.println("Welcome, " + currentUser.getUsername() + "!");
                        // You can navigate to other features here...
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
