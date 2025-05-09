package Models;



public class Budget {
    private String category;
    private double limitAmount;

    public Budget(String category, double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
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
    public static Budget fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) return null;
        String category = parts[0];
        double amount = Double.parseDouble(parts[1]);
        return new Budget(category, amount);
    }
}
