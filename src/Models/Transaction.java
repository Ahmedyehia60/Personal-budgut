package Models;

import java.time.LocalDate;

public abstract class Transaction {
    protected String category;
    protected double amount;
    protected LocalDate date;
    protected User user;

    public Transaction(String category, double amount, LocalDate date, User user) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.user = user;
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

    public String getUsername() {
        return user.getUsername();
    }

    // Template method (can be overridden)
    public abstract String toCSV();

    public abstract String getType(); // "Expense" or "Income"
}
