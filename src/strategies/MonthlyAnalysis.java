package strategies;

import Models.User;
import Models.Income;
import Models.Expense;

import java.time.LocalDate;
import java.util.List;

public class MonthlyAnalysis implements BudgetAnalysisStrategy {
    @Override
    public void analysis(User user, List<Income> incomes, List<Expense> expenses) {
        double totalIncome = calculateTotalIncome(user, incomes);
        double totalExpense = calculateTotalExpense(user, expenses);
        double netIncome = totalIncome - totalExpense;
        double savingsRate = netIncome / totalIncome * 100;
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Your NetIncome this Month is: " + netIncome + "\n");
        System.out.println("Savings Rate: " + savingsRate);
    }

    @Override
    public double calculateTotalIncome(User user, List<Income> incomes) {
        double totalIncome = 0.0;
        for (Income income : incomes) {
            if(income.getDate().getYear() == LocalDate.now().getYear() && income.getDate().getMonth() == LocalDate.now().getMonth() && user.getUsername().equals(income.getUsername())){
                totalIncome += income.getAmount();
            }
        }
        return totalIncome;
    }

    @Override
    public double calculateTotalExpense(User user, List<Expense> expenses) {
        double totalExpense = 0.0;
        for (Expense expense : expenses) {
            if(expense.getDate().getYear() == LocalDate.now().getYear() && expense.getDate().getMonth() == LocalDate.now().getMonth() && user.getUsername().equals(expense.getUsername())){
                totalExpense += expense.getAmount();
            }
        }
        return totalExpense;
    }
}
