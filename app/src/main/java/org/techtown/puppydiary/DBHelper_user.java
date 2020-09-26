package org.techtown.puppydiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper_user extends SQLiteOpenHelper {

    Cursor cursor = null;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper_user(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("CREATE TABLE IF NOT EXISTS usertest (useridx INTEGER PRIMARY KEY);");
    }

    public void insert(int useridx) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO usertest (useridx) VALUES(" + useridx + ");");

        System.out.println("insert useridx !!!!!!! : " + useridx);

        db.close();
    }

    public int getuseridx(){

        SQLiteDatabase db = getReadableDatabase();
        int useridx = 0;

        cursor = db.rawQuery("SELECT * from usertest;", null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                useridx = cursor.getInt(cursor.getColumnIndex("useridx"));
            }
        }
        cursor.close();
        db.close();
        return useridx;
    }


}
