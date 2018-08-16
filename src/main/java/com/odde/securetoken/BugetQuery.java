package com.odde.securetoken;

import java.time.LocalDate;
import java.util.List;

public class BugetQuery {

    private BudgetDao budgetDao;
    public BugetQuery(BudgetDao bugetDao) {
        this.budgetDao = bugetDao;
    }
    public int query(LocalDate start, LocalDate end) {
        List<MonthBudget> allBugets = this.budgetDao.findAll();
        int begIndex = 0;
        int endIndex = 0;
        MonthBudget startMonthBudget = null;
        MonthBudget endMonthBudget = null;

        if(allBugets.size() == 0) {
            return 0;
        }

        // query end date is smaller than date of first MonthBudget
        if((allBugets.get(0).getDate().getYear() > end.getYear()) ||
                ((allBugets.get(0).getDate().getYear() == end.getYear()) &&
                        (allBugets.get(0).getDate().getMonth().getValue() > end.getMonth().getValue()))) {
            return 0;
        }

        // query start date is bigger than date of last MonthBudget
        if((allBugets.get(allBugets.size() - 1).getDate().getYear() < start.getYear()) ||
                ((allBugets.get(allBugets.size() - 1).getDate().getYear() == start.getYear()) &&
                        (allBugets.get(allBugets.size() - 1).getDate().getMonth().getValue() < start.getMonth().getValue()))) {
            return 0;
        }

        // query start date is smaller than date of first MonthBudget
        if((allBugets.get(0).getDate().getYear() > start.getYear()) ||
                ((allBugets.get(0).getDate().getYear() == start.getYear()) &&
                        (allBugets.get(0).getDate().getMonth().getValue() > start.getMonth().getValue()))) {
            startMonthBudget = allBugets.get(0);
            begIndex = 0;
            start = LocalDate.of(startMonthBudget.getDate().getYear(), startMonthBudget.getDate().getMonth(), 1);
        }

        // query end date is bigger than date of last MonthBudget
        if((allBugets.get(allBugets.size() - 1).getDate().getYear() < end.getYear()) ||
                ((allBugets.get(allBugets.size() - 1).getDate().getYear() == end.getYear()) &&
                        (allBugets.get(allBugets.size() - 1).getDate().getMonth().getValue() < end.getMonth().getValue()))) {
            endMonthBudget = allBugets.get(allBugets.size() - 1);
            endIndex = allBugets.size() - 1;
            end = LocalDate.of(endMonthBudget.getDate().getYear(), endMonthBudget.getDate().getMonth(), endMonthBudget.getDate().lengthOfMonth());
        }

        for(int i=0; i<allBugets.size(); i++) {
            MonthBudget budget = allBugets.get(i);
            if((startMonthBudget == null) &&
                    ((budget.getDate().getYear() > start.getYear()) ||
                            ((budget.getDate().getMonth().getValue() >= start.getMonth().getValue()) &&
                             (budget.getDate().getYear() >= start.getYear())))) {
                startMonthBudget = budget;
                begIndex = i;
            }

            if((endMonthBudget == null) &&
                    ((budget.getDate().getYear() > end.getYear()) ||
                            ((budget.getDate().getMonth().getValue() >= end.getMonth().getValue()) &&
                                    (budget.getDate().getYear() >= end.getYear())))) {

                endMonthBudget = budget;
                endIndex = i;
            }

            if(startMonthBudget != null && endMonthBudget != null) {
                break;
            }
        }

        if(startMonthBudget == null || endMonthBudget == null) {
            return 0;
        }

        // start and end in the same month
        if(startMonthBudget.getDate() == endMonthBudget.getDate()) {
            // start and end is the same day
            if(end.getDayOfMonth() != start.getDayOfMonth()) {
                return startMonthBudget.getBudget() / startMonthBudget.getDate().lengthOfMonth() *
                        (end.getDayOfMonth() - start.getDayOfMonth() + 1);
            }
            else
            {
                return startMonthBudget.getBudget() / startMonthBudget.getDate().lengthOfMonth();
            }
        }
        // different month
        else {
            int startMonthBudgetVal =
                    (startMonthBudget.getBudget() / startMonthBudget.getDate().lengthOfMonth()) *
                            (startMonthBudget.getDate().lengthOfMonth() - start.getDayOfMonth() + 1);
            int endMonthBudgetVal =
                    (endMonthBudget.getBudget() / endMonthBudget.getDate().lengthOfMonth()) *
                            (end.getDayOfMonth());
            int midMonthsBudgetVal = 0;

            for(int j=begIndex+1; j<endIndex; j++) {
                midMonthsBudgetVal += allBugets.get(j).getBudget();
            }

            return startMonthBudgetVal + midMonthsBudgetVal + endMonthBudgetVal;
        }

    }


}
