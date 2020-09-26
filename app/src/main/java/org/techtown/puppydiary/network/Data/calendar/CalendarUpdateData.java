package org.techtown.puppydiary.network.Data.calendar;

public class CalendarUpdateData {

    private int year;
    private int month;
    private int date;
    private String memo;
    private int inject;
    private int water;

    public CalendarUpdateData(int year, int month, int date, String memo, int inject, int water){
        this.year = year;
        this.month = month;
        this.date = date;
        this.memo = memo;
        this.inject = inject;
        this.water = water;
    }
}
