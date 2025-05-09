import Models.User;
import Models.Budget;
import services.AuthService;
import services.BudgetService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        BudgetService budgetService = new BudgetService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            System.out.println("\n=== Personal Budgeting App ===");

            if (currentUser == null) {
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
            } else {
                System.out.println("1. Add Budget");
                System.out.println("2. View Budgets");
                System.out.println("3. Logout");
                System.out.print("Choose option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter budget category: ");
                        String category = scanner.nextLine();

                        System.out.print("Enter budget limit: ");
                        double limit = Double.parseDouble(scanner.nextLine());

                        budgetService.addBudget(category, limit);
                        System.out.println("Budget added successfully.");
                        break;

                    case "2":
                        List<Budget> budgets = budgetService.getAllBudgets();
                        System.out.println("\n=== Your Budgets ===");
                        for (Budget b : budgets) {
                            System.out.println("- " + b.getCategory() + ": " + b.getLimitAmount());
                        }
                        break;

                    case "3":
                        currentUser = null;
                        System.out.println("Logged out.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }
}
