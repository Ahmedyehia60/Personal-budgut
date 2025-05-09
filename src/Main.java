import Models.Budget;
import Models.User;
import Models.Expense;
import services.AuthService;
import services.BudgetService;
import services.ExpenseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        BudgetService budgetService = new BudgetService();
        ExpenseService expenseService = new ExpenseService();
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
                System.out.println("\n--- Logged In Menu ---");
                System.out.println("1. Add Budget");
                System.out.println("2. View Budgets");
                System.out.println("3. Add Expense");
                System.out.println("4. View Expenses");
                System.out.println("5. Logout");
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
                        System.out.print("Enter expense category: ");
                        String expCategory = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double expAmount = Double.parseDouble(scanner.nextLine());
                        LocalDate expDate = LocalDate.now();

                        expenseService.addExpense(expCategory, expAmount, expDate);
                        System.out.println("Expense added.");

                        // Budget check
                        Budget matchedBudget = budgetService.getBudgetByCategory(expCategory);
                        if (matchedBudget != null) {
                            double totalSpent = expenseService.getTotalExpenseForCategory(expCategory);
                            if (totalSpent > matchedBudget.getLimitAmount()) {
                                System.out.println("⚠️ Warning: You exceeded the budget for '" + expCategory + "'!");
                            } else {
                                double remaining = matchedBudget.getLimitAmount() - totalSpent;
                                System.out.println("✅ Remaining budget for '" + expCategory + "': " + remaining);
                            }
                        } else {
                            System.out.println("Note: No budget limit set for this category.");
                        }
                        break;

                    case "4":
                        List<Expense> expenses = expenseService.getAllExpenses();
                        System.out.println("\n=== Your Expenses ===");
                        for (Expense e : expenses) {
                            System.out.println("- " + e.getCategory() + ": " + e.getAmount() + " on " + e.getDate());
                        }
                        break;

                    case "5":
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
