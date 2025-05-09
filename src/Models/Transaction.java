package Models;

import java.time.LocalDate;

public abstract class Transaction {
    protected String category;
    protected double amount;
    protected LocalDate date;

    public Transaction(String category, double amount, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    // Template method (can be overridden)
    public abstract String toCSV();

    public abstract String getType(); // "Expense" or "Income"
}
