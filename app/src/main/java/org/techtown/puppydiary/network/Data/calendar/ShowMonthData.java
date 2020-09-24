package org.techtown.puppydiary.network.Data.calendar;

public class ShowMonthData {

    private int year;
    private int month;
    private int date;
    private String memo;
    private int inject;
    private int water;

    public ShowMonthData(int year, int month, int date, String memo, int inject, int water){
        this.year = year;
        this.month = month;
        this.date = date;
        this.memo = memo;
        this.inject = inject;
        this.water = water;
    }
}
