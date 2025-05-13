import Models.Budget;
import Models.User;
import Models.Expense;
import Models.Income;
import services.*;
import strategies.MonthlyAnalysis;
import strategies.YearlyAnalysis;

import Models.Reminder;
import notifications.ConsoleNotifier;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AuthService authService = new AuthService();
        ReminderService reminderService = new ReminderService();
        ConsoleNotifier notifier = new ConsoleNotifier();
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
                            List<Reminder> userReminders = reminderService.getRemindersForUser(currentUser.getUsername());
                            notifier.notifyUser(userReminders);
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
                BudgetService budgetService = new BudgetService(currentUser);
                ExpenseService expenseService = new ExpenseService(currentUser);
                IncomeService incomeService = new IncomeService(currentUser);
                List<Income> incomes = incomeService.getAllIncomes();
                List<Expense> expenses = expenseService.getAllExpenses();

                System.out.println("\n--- Logged In Menu ---");
                System.out.println("1. Add Budget");
                System.out.println("2. View Budgets");
                System.out.println("3. Add Expense");
                System.out.println("4. Add Income");
                System.out.println("5. View Expenses");
                System.out.println("6. View Incomes");
                System.out.println("7. Add Reminder");
                System.out.println("8. View Reminders");
                System.out.println("9. Monthly Analysis");
                System.out.println("10. Yearly Analysis");
                System.out.println("11. Logout");
                System.out.print("Choose option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter budget category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter budget limit: ");
                        double limit = Double.parseDouble(scanner.nextLine());

                        budgetService.addBudget(category, limit, currentUser);
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

                        expenseService.addExpense(expCategory, expAmount, expDate, currentUser);
                        System.out.println("Expense added.");

                        Budget matchedBudget = budgetService.getBudgetByCategory(expCategory);
                        if (matchedBudget != null) {
                            double totalSpent = expenseService.getTotalExpenseForCategory(expCategory);
                            if (totalSpent > matchedBudget.getLimitAmount()) {
                                System.out.println("Warning: You exceeded the budget for '" + expCategory + "'!");
                            } else {
                                double remaining = matchedBudget.getLimitAmount() - totalSpent;
                                System.out.println("Remaining budget for '" + expCategory + "': " + remaining);
                            }
                        } else {
                            System.out.println("Note: No budget limit set for this category.");
                        }
                        break;

                    case "4":
                        System.out.print("Enter income category: ");
                        String incomeCategory = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double incomeAmount = Double.parseDouble(scanner.nextLine());
                        LocalDate incomeDate = LocalDate.now();

                        incomeService.addIncome(incomeCategory, incomeAmount, incomeDate, currentUser);
                        System.out.println("Income added.");
                        break;

                    case "5":
                        System.out.println("\n=== Your Expenses ===");
                        for (Expense e : expenses) {
                            System.out.println("- " + e.getCategory() + ": " + e.getAmount() + " on " + e.getDate());
                        }
                        break;

                    case "6":
                        System.out.println("\n=== Your Incomes ===");
                        for (Income i : incomes) {
                            System.out.println("- " + i.getCategory() + ": " + i.getAmount() + " on " + i.getDate());
                        }
                        break;

                    case "7":
                        System.out.print("Enter reminder message: ");
                        String message = scanner.nextLine();

                        System.out.print("Enter date and time (e.g. 10-5-2025 15:30 or 2025/5/10 15:30): ");
                        String dateTimeInput = scanner.nextLine();

                        LocalDateTime dateTime = null;
                        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                                new DateTimeFormatterBuilder()
                                        .appendValue(ChronoField.DAY_OF_MONTH)
                                        .appendLiteral('-')
                                        .appendValue(ChronoField.MONTH_OF_YEAR)
                                        .appendLiteral('-')
                                        .appendValue(ChronoField.YEAR)
                                        .appendLiteral(' ')
                                        .appendPattern("HH:mm")
                                        .toFormatter(),

                                new DateTimeFormatterBuilder()
                                        .appendValue(ChronoField.YEAR)
                                        .appendLiteral('/')
                                        .appendValue(ChronoField.MONTH_OF_YEAR)
                                        .appendLiteral('/')
                                        .appendValue(ChronoField.DAY_OF_MONTH)
                                        .appendLiteral(' ')
                                        .appendPattern("HH:mm")
                                        .toFormatter(),

                                DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm")
                        };

                        for (DateTimeFormatter formatter : formatters) {
                            try {
                                dateTime = LocalDateTime.parse(dateTimeInput, formatter);
                                break;
                            } catch (Exception ignored) {}
                        }

                        if (dateTime != null) {
                            Reminder reminder = new Reminder(currentUser.getUsername(), message, dateTime);
                            reminderService.addReminder(reminder);
                            System.out.println("Reminder saved successfully.");
                        } else {
                            System.out.println("Invalid date-time format.");
                        }
                        break;

                    case "8":
                        List<Reminder> reminders = reminderService.getRemindersForUser(currentUser.getUsername());
                        System.out.println("\n=== Your Reminders ===");
                        if (reminders.isEmpty()) {
                            System.out.println("You have no reminders.");
                        } else {
                            for (Reminder r : reminders) {
                                System.out.println("- " + r.getMessage() + " at " + r.getDateTime());
                            }
                        }
                        break;

                    case "9":
                        MonthlyAnalysis monthlyAnalysis = new MonthlyAnalysis();
                        System.out.println("\n=== Monthly Analysis ===");
                        monthlyAnalysis.analysis(currentUser, incomes, expenses);
                        break;

                    case "10":
                        YearlyAnalysis yearlyAnalysis = new YearlyAnalysis();
                        System.out.println("\n=== Yearly Analysis ===");
                        yearlyAnalysis.analysis(currentUser, incomes, expenses);
                        break;

                    case "11":
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
