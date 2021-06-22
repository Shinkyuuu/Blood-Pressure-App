package com.example.bloodpsrapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bloodpsrapp.models.Readings;

public class AllDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TAG AllDatabaseHelper ";
    private static final String TABLE_NAME = "All_Data_Table";
    private static final String COL0 = "ID";
    private static final String COL1 = "date";
    private static final String COL2 = "day";
    private static final String COL3 = "time";
    private static final String COL4 = "AMPM";
    private static final String COL5 = "SYS";
    private static final String COL6 = "DY";
    private static final String COL7 = "HR";

    //Constructor
    public AllDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //On create
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                                           + COL1 + " TEXT," + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 +" TEXT, "
                                            + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT)";

        db.execSQL(createTable);

        Log.d(TAG, "onCreate success");
    }

    //Update database value
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP  TABLE  IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add data
    public boolean addData(Readings reading) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, reading.getDate());
        contentValues.put(COL2, reading.getDay());
        contentValues.put(COL3, reading.getTime());
        contentValues.put(COL4, reading.getAMPM());
        contentValues.put(COL5, reading.getSYS());
        contentValues.put(COL6, reading.getDY());
        contentValues.put(COL7, reading.getHR());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Log.e(TAG, "addData failure");
            return false;
        } else {
            Log.d(TAG, "addData success");
            return true;
        }
    }

    //Retrieve data
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        Log.d(TAG, "getData success");
        return data;
    }
}
