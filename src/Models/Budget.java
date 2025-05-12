package Models;



public class Budget {
    private String category;
    private double limitAmount;
    private User user;

    public Budget(String category, double limitAmount, User user) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    // Convert to CSV string for file saving
    public String toCSV() {
        return category + "," + limitAmount;
    }

    // Load Budget from a CSV string
    public static Budget fromCSV(String line, User user) {
        String[] parts = line.split(",");
        if (parts.length != 3) return null;
        String category = parts[1];
        double amount = Double.parseDouble(parts[2]);
        return new Budget(category, amount, user);
    }
}
