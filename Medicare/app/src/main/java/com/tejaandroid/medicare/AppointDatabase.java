package com.tejaandroid.medicare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AppointDatabase extends SQLiteOpenHelper {
    public AppointDatabase(Context context) {
        super(context, "medicare", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table1 = "CREATE TABLE APPOINTMENT(DOC_NAME TEXT , CLINIC_NAME TEXT , DATE TEXT , TIME TEXT)";
        db.execSQL(create_table1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS APPOINTMENT");
        onCreate(db);
    }

    public boolean insertData(String doc, String clinic, String date, String time) {
        SQLiteDatabase sdb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("DOC_NAME", doc);
        cv.put("CLINIC_NAME", clinic);
        cv.put("DATE", date);
        cv.put("TIME", time);
        sdb.insert("APPOINTMENT", null, cv);
        return true;
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM APPOINTMENT", null);
        res.moveToFirst();
        int count = res.getInt(0);
        res.close();
        return count;
    }

    public ArrayList<String> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        Cursor res = db.rawQuery("SELECT * FROM APPOINTMENT", null);

        while (res.moveToNext())
        {
            list.add(res.getString(res.getColumnIndex("DOC_NAME")));
        }
        res.close();
        return list;
    }

    public ArrayList<String[]> getData(){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM APPOINTMENT", null);
        while (res.moveToNext())
        {
            String[] alist = new String[] {
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
            };
            arrayList.add(alist);
        }
        return arrayList;
    }
}
