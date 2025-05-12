package services;

import Models.Expense;
import Models.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private final String FILE_PATH = "src/data/expenses.txt";
    private List<Expense> expenses;

    public ExpenseService(User user) {
        expenses = new ArrayList<>();
        loadExpensesFromFile(user);
    }

    public void addExpense(String category, double amount, LocalDate date, User user) {
        Expense expense = new Expense(category, amount, date, user);
        expenses.add(expense);
        saveExpenseToFile(expense, user);
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    private void loadExpensesFromFile(User user) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(user.getUsername())) {
                    Expense expense = Expense.fromCSV(line, user);
                    if (expense != null) {
                        expenses.add(expense);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }

    private void saveExpenseToFile(Expense expense, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ",");
            writer.write(expense.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving expense: " + e.getMessage());
        }
    }


    public double getTotalExpenseForCategory(String category) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                total += e.getAmount();
            }
        }
        return total;
    }

}
