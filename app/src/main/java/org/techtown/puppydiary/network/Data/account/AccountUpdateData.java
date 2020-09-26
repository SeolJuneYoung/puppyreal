package org.techtown.puppydiary.network.Data.account;

public class AccountUpdateData {

    private int year;
    private int month;
    private int date;
    private String item;
    private int price;

    public AccountUpdateData(int year, int month, int date, String item, int price){
        this.year = year;
        this.month = month;
        this.date = date;
        this.item = item;
        this.price = price;
    }

}
