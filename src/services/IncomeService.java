package services;

import Models.Income;
import Models.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeService {
    private final String FILE_PATH = "src/data/incomes.txt";
    private List<Income> incomes;

    public IncomeService(User user) {
        incomes = new ArrayList<>();
        loadIncomesFromFile(user);
    }

    public void addIncome(String category, double amount, LocalDate date, User user) {
        Income income = new Income(category, amount, date, user);
        incomes.add(income);
        saveIncomeToFile(income, user);
    }

    public List<Income> getAllIncomes() {
        return incomes;
    }

    private void loadIncomesFromFile(User user) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(user.getUsername())) {
                    Income income = Income.fromCSV(line, user);
                    if (income != null) {
                        incomes.add(income);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading incomes: " + e.getMessage());
        }
    }

    private void saveIncomeToFile(Income income, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ",");
            writer.write(income.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving income: " + e.getMessage());
        }
    }


    public double getTotalIncomeForCategory(String category) {
        double total = 0;
        for (Income e : incomes) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                total += e.getAmount();
            }
        }
        return total;
    }

}
