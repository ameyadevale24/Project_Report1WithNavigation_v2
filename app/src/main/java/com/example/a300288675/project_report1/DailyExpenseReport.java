package com.example.a300288675.project_report1;

final public class DailyExpenseReport {
    double[] dailyBalance = new double[31];
    String[] days = new String[31];

    public DailyExpenseReport(double[] dailyBalance, String[] days) {
        this.dailyBalance = dailyBalance;
        this.days = days;
    }

    public double[] getDailyBalance() {
        return dailyBalance;
    }

    public String[] getDays() {
        return days;
    }
}
