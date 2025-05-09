package services;

import Models.Expense;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private final String FILE_PATH = "src/data/expenses.txt";
    private List<Expense> expenses;

    public ExpenseService() {
        expenses = new ArrayList<>();
        loadExpensesFromFile();
    }

    public void addExpense(String category, double amount, LocalDate date) {
        Expense expense = new Expense(category, amount, date);
        expenses.add(expense);
        saveExpenseToFile(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    private void loadExpensesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense expense = Expense.fromCSV(line);
                if (expense != null) {
                    expenses.add(expense);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }

    private void saveExpenseToFile(Expense expense) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
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
