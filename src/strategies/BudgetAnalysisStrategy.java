package strategies;

import Models.User;
import Models.Income;
import Models.Expense;

import java.util.List;

public interface BudgetAnalysisStrategy {

    void analysis(User user, List<Income> incomes, List<Expense> expenses);
    double calculateTotalIncome(User user, List<Income> incomes);
    double calculateTotalExpense(User user, List<Expense> expenses);
}
