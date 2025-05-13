package Models;

import java.time.LocalDate;

public class Income extends Transaction {

    public Income(String category, double amount, LocalDate date, User user) {
        super(category, amount, date, user);
    }

    @Override
    public String toCSV() {
        return "Income," + category + "," + amount + "," + date.toString();
    }

    public static Income fromCSV(String line,User user) {
        String[] parts = line.split(",");
        if (parts.length != 5) return null;
        String category = parts[2];
        double amount = Double.parseDouble(parts[3]);
        LocalDate date = LocalDate.parse(parts[4]);
        return new Income(category, amount, date, user);
    }

    @Override
    public String getType() {
        return "Income";
    }

}

