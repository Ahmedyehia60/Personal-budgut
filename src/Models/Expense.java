package Models;

import java.time.LocalDate;

public class Expense extends Transaction {

    public Expense(String category, double amount, LocalDate date) {
        super(category, amount, date);
    }

    @Override
    public String toCSV() {
        return "Expense," + category + "," + amount + "," + date.toString();
    }

    public static Expense fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) return null;
        String category = parts[1];
        double amount = Double.parseDouble(parts[2]);
        LocalDate date = LocalDate.parse(parts[3]);
        return new Expense(category, amount, date);
    }

    @Override
    public String getType() {
        return "Expense";
    }
}

