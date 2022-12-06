package com.tejaandroid.medicare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MedDatabase extends SQLiteOpenHelper {
    public MedDatabase(Context context) {
        super(context, "Records", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table1 = "CREATE TABLE MEDICINE(MED_NAME TEXT , TIMES TEXT , TIME1 TEXT , TIME2 TEXT DEFAULT NULL , TIME3 TEXT DEFAULT NULL)";
        db.execSQL(create_table1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MEDICINE");
        onCreate(db);
    }

    public boolean insertData(String med, String times, String t1, String t2, String t3) {
        SQLiteDatabase sdb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("MED_NAME", med);
        cv.put("TIMES", times);
        cv.put("TIME1", t1);
        cv.put("TIME2", t2);
        cv.put("TIME3", t3);
        sdb.insert("MEDICINE", null, cv);
        return true;
    }

    public ArrayList<String> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        Cursor res = db.rawQuery("SELECT * FROM MEDICINE", null);

        while (res.moveToNext())
        {
            list.add(res.getString(res.getColumnIndex("MED_NAME")));
        }
        res.close();
        return list;
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM MEDICINE", null);
        res.moveToFirst();
        int count = res.getInt(0);
        res.close();
        return count;
    }

    public ArrayList<String[]> getData(){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM MEDICINE", null);
        while (res.moveToNext())
        {
            String[] alist = new String[] {
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)
            };
            arrayList.add(alist);
        }
        return arrayList;
    }

    public Boolean deleteMed (String med) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("MEDICINE", "MED_NAME=?", new String[]{med});
        return true;
    }
}
