package com.odde.securetoken;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetQueryTest {

    @Test
    public void query() {
        BudgetDao budgetDao = mock(BudgetDao.class);
        BugetQuery budgetQuery = new BugetQuery(budgetDao);

        List<MonthBudget> budgets =Arrays.asList(
                new MonthBudget(310, LocalDate.of(2016, 1, 1)),
                new MonthBudget(290, LocalDate.of(2016, 2, 1)),
                new MonthBudget(310, LocalDate.of(2016, 3, 1)),
                new MonthBudget(310, LocalDate.of(2016, 5, 1)),
                new MonthBudget(300, LocalDate.of(2016, 6, 1)),
                new MonthBudget(310, LocalDate.of(2018, 1, 1)),
                new MonthBudget(280, LocalDate.of(2018, 2, 1)),
                new MonthBudget(310, LocalDate.of(2018, 3, 1))
        );

        when(budgetDao.findAll()).thenReturn(budgets);

        int budgetVal = 0;

        budgetVal = budgetQuery.query(
                LocalDate.of(2018,1,1),
                LocalDate.of(2018,1,1));
        assertTrue(budgetVal == 10);

        budgetVal = budgetQuery.query(
                LocalDate.of(2015,1,1),
                LocalDate.of(2015,2,15));
        assertTrue(budgetVal == 0);

        when(budgetDao.findAll()).thenReturn(budgets);
        budgetVal = budgetQuery.query(
                LocalDate.of(2018,1,1),
                LocalDate.of(2018,1,15));
        assertTrue(budgetVal == 150);

        budgetVal = budgetQuery.query(
                LocalDate.of(2016,1,1),
                LocalDate.of(2016,2,15));
        assertTrue(budgetVal == 460);

        budgetVal = budgetQuery.query(
                LocalDate.of(2016,1,1),
                LocalDate.of(2016,5,10));
        assertTrue(budgetVal == 1010);

        budgetVal = budgetQuery.query(
                LocalDate.of(2016,2,20),
                LocalDate.of(2018,2,10));
        assertTrue(budgetVal == 1430);

        budgetVal = budgetQuery.query(
                LocalDate.of(2019,1,1),
                LocalDate.of(2019,2,15));
        assertTrue(budgetVal == 0);

        budgetVal = budgetQuery.query(
                LocalDate.of(2015,1,1),
                LocalDate.of(2016,2,15));
        assertTrue(budgetVal == 460);

        budgetVal = budgetQuery.query(
                LocalDate.of(2018,3,1),
                LocalDate.of(2019,2,15));
        assertTrue(budgetVal == 310);

        budgetVal = budgetQuery.query(
                LocalDate.of(2014,3,1),
                LocalDate.of(2019,2,15));
        assertTrue(budgetVal == 2420);
        

    }


}
