package com.odde.securetoken;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BudgetDao {
    public List<MonthBudget> budgets = Arrays.asList(
            new MonthBudget(310, LocalDate.of(2018, 1, 1)),
            new MonthBudget(280, LocalDate.of(2018, 2, 1)),
            new MonthBudget(310, LocalDate.of(2018, 3, 1)),
            new MonthBudget(310, LocalDate.of(2018, 4, 1)),
            new MonthBudget(310, LocalDate.of(2018, 5, 1)),
            new MonthBudget(310, LocalDate.of(2018, 6, 1)),
            new MonthBudget(310, LocalDate.of(2018, 7, 1)),
            new MonthBudget(310, LocalDate.of(2018, 8, 1)),
            new MonthBudget(310, LocalDate.of(2018, 9, 1))
    );

    public List<MonthBudget> findAll(){
        return budgets;
    }
}
