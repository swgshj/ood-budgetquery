package com.odde.securetoken;

import java.time.LocalDate;

public class MonthBudget {
    private int budget = 0;
    private LocalDate date = null;

    public MonthBudget(int budget, LocalDate date) {
        this.budget = budget;
        this.date = date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
