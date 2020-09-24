package org.techtown.puppydiary.accountmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class DBHelper_money extends SQLiteOpenHelper {
    Cursor cursor = null;
    int pos = 0;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper_money(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        //db.execSQL("CREATE TABLE IF NOT EXISTS mt6 (pos INTEGER, year INTEGER, month INTEGER, day INTEGER, price INTEGER, memo TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbmoneytest (useridx INTEGER, pos INTEGER, year INTEGER, month INTEGER, day INTEGER, price INTEGER, memo TEXT, CONSTRAINT useridx_fk FOREIGN KEY(useridx) REFERENCES user(useridx));");

    }

    public void insert(int useridx, int year, int month, int day, int price, String memo) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO dbmoneytest(useridx, year, month, day, price, memo) VALUES(" + useridx + ", " + year + ", " + month + ", " + day + ", " + price + ", '" + memo + "');");
        //db.execSQL("UPDATE dbmoneytest SET useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + " and price = " + price + " and memo = '" + memo + "';");
        //cursor = db.rawQuery("SELECT * FROM mt6 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);
        cursor = db.rawQuery("SELECT * FROM dbmoneytest WHERE useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                pos = cursor.getCount();
            } while (cursor.moveToNext());
        }
        //db.execSQL("UPDATE mt6 SET pos = " + pos + " WHERE year = " + year + " and month = " + month + " and day = " + day + " and price = " + price + " and memo = '" + memo + "';");
        db.execSQL("UPDATE dbmoneytest SET pos = " + pos + " WHERE useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + " and price = " + price + " and memo = '" + memo + "';");
        db.close();
    }

    /*
    public void update(int useridx, int pos, int year, int month, int day, int price, String memo){
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();

        //db.execSQL("REPLACE INTO mt4(price, memo) SELECT price, memo FROM mt4 WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        //db.execSQL("INSERT OR REPLACE INTO mt6 VALUES( " + pos + ", " + year + ", " + month + ", " + day + ", " + price + ", '" + memo + "');");
        //db.execSQL("DELETE FROM mt4 WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        //db.execSQL("INSERT OR REPLACE INTO mt4(pos, year, month, day, price, memo) VALUES(" + pos + ", " + year + ", " + month + ", " + day + ", '" + price + "', '" + memo + "');");
        db.execSQL("UPDATE mt6 SET price = " + price + " and memo = '" + memo + "' WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        db.close();
    }
    */

    public void delete(int useridx, int pos, int year, int month, int day) {

        SQLiteDatabase db = getWritableDatabase();

        //db.execSQL("DELETE FROM mt6 WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        db.execSQL("DELETE FROM dbmoneytest WHERE useridx = " + useridx + " and pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        //db.execSQL("UPDATE mt6 SET pos = pos-1 WHERE pos > " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        db.execSQL("UPDATE dbmoneytest SET pos = pos-1 WHERE useridx = " + useridx + " and pos > " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";");
        db.close();
    }

    //같은 날짜에 같은 항목 있는지 체크
    public int check(int useridx, int year, int month, int day, int price, String memo) {
        SQLiteDatabase db = getReadableDatabase();
        int check = 0; //중복 item 체크, 1이면 중복
        //cursor = db.rawQuery("SELECT pos from mt6 WHERE year = " + year + " and month = " + month + " and day = " + day + " and price = " + price + " and memo = '" + memo + "';", null);
        cursor = db.rawQuery("SELECT pos from dbmoneytest WHERE useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + " and price = " + price + " and memo = '" + memo + "';", null);
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex("pos")) != null) {
                check = 1;
            }
        }
        return check;
    }

    public String memo(int useridx, int pos, int year, int month, int day) {
        SQLiteDatabase db = getReadableDatabase();
        String memo = "";

        //cursor = db.rawQuery("SELECT memo from mt6 WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        cursor = db.rawQuery("SELECT memo from dbmoneytest WHERE useridx = " + useridx + " and pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                memo = cursor.getString(cursor.getColumnIndex("memo"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return memo;
    }

    public int price(int useridx, int pos, int year, int month, int day) {
        SQLiteDatabase db = getReadableDatabase();
        int price = 0;

        //cursor = db.rawQuery("SELECT price from mt6 WHERE pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        cursor = db.rawQuery("SELECT price from dbmoneytest WHERE useridx = " + useridx + " and pos = " + pos + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                price = cursor.getInt(cursor.getColumnIndex("price"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return price;
    }

    public int getSum(int useridx, int year, int month, int day) {

        SQLiteDatabase db = getReadableDatabase();
        int sum = 0;

        //cursor = db.rawQuery("SELECT price from mt6 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);
        cursor = db.rawQuery("SELECT price from dbmoneytest WHERE useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                sum += cursor.getInt(cursor.getColumnIndex("price"));
            } while (cursor.moveToNext());
        }

        return sum;
    }

/*
    public ArrayList<MoneyTab.MoneytabItem> getResult(int year, int month, int day) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MoneyTab.MoneytabItem> result = new ArrayList<MoneyTab.MoneytabItem>();

        cursor = db.rawQuery("SELECT * from mt4 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(new MoneyTab.MoneytabItem(
                        cursor.getString(cursor.getColumnIndex("memo")),
                        cursor.getString(cursor.getColumnIndex("price"))));
            } while (cursor.moveToNext());

            System.out.println("RESULT:" + result);
        }

        cursor.close();
        db.close();
        return result;

    }



    public String getResultmemo(int year, int month, int day){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String memo = null;
        cursor = db.rawQuery("SELECT * from mt4 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                memo = cursor.getString(cursor.getColumnIndex("memo"));
            } while(cursor.moveToNext());
        }

        return memo;
    }

    public String getResultprice(int year, int month, int day){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String price = null;
        cursor = db.rawQuery("SELECT * from mt4 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                price = cursor.getString(cursor.getColumnIndex("price"));
                System.out.println("db price : " + price);
            } while(cursor.moveToNext());
        }
        System.out.println("db price : " + price);
        return price;
    }


*/

    public Cursor getResult(int useridx, int year, int month, int day) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용
        //cursor = db.rawQuery("SELECT * from mt6 WHERE year = " + year + " and month = " + month + " and day = " + day + ";", null);
        cursor = db.rawQuery("SELECT * from dbmoneytest WHERE useridx = " + useridx + " and year = " + year + " and month = " + month + " and day = " + day + ";", null);
        return cursor;

    }
}


