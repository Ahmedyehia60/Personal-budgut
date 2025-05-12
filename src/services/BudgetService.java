package services;

import Models.Budget;
import Models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    private final String FILE_PATH = "src/data/budgets.txt";
    private List<Budget> budgets;

    public BudgetService(User user) {
        budgets = new ArrayList<>();
        loadBudgetsFromFile(user);
    }

    public void addBudget(String category, double amount, User user) {
        Budget budget = new Budget(category, amount, user);
        budgets.add(budget);
        saveBudgetToFile(budget, user);
    }

    public List<Budget> getAllBudgets() {
        return budgets;
    }

    public Budget getBudgetByCategory(String category) {
        for (Budget b : budgets) {
            if (b.getCategory().equalsIgnoreCase(category)) {
                return b;
            }
        }
        return null;
    }

    private void loadBudgetsFromFile(User user) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(user.getUsername())) {
                    Budget budget = Budget.fromCSV(line, user);
                    if (budget != null) {
                        budgets.add(budget);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading budgets: " + e.getMessage());
        }
    }

    private void saveBudgetToFile(Budget budget, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ",");
            writer.write(budget.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving budget: " + e.getMessage());
        }
    }
}
