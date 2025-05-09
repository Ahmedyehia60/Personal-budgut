package services;

import Models.Budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    private final String FILE_PATH = "src/data/budgets.txt";
    private List<Budget> budgets;

    public BudgetService() {
        budgets = new ArrayList<>();
        loadBudgetsFromFile();
    }

    public void addBudget(String category, double amount) {
        Budget budget = new Budget(category, amount);
        budgets.add(budget);
        saveBudgetToFile(budget);
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

    private void loadBudgetsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Budget budget = Budget.fromCSV(line);
                if (budget != null) {
                    budgets.add(budget);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading budgets: " + e.getMessage());
        }
    }

    private void saveBudgetToFile(Budget budget) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(budget.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving budget: " + e.getMessage());
        }
    }
}
