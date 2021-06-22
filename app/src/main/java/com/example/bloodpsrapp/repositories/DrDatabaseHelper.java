package com.example.bloodpsrapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bloodpsrapp.models.SessionReadings;


public class DrDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TAG DrDatabaseHelper ";
    private static final String TABLE_NAME = "Dr_Data_Table";
    private static final String COL0 = "ID";
    private static final String COL1 = "date";
    private static final String COL2 = "day";
    private static final String COL3 = "AMPM";
    private static final String COL4 = "count";
    private static final String COL5 = "avgSYS";
    private static final String COL6 = "avgDY";
    private static final String COL7 = "avgHR";
    private static final String COL8 = "Sent";

    //Constructor
    public DrDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //On create
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1 + " TEXT," + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, "
                + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT, " + COL8 + " TEXT)";

        db.execSQL(createTable);
        Log.d(TAG, "onCreate success");
    }

    //Update database value
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP  TABLE  IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Update "Sent" property of sessionReadings
    public void updateSent() {
        String newSent = "yes";
        String oldSent = "non";
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL8 + " = '" + newSent + "' WHERE " + COL8 + " = '" + oldSent + "'";

        db.execSQL(query);
        Log.d(TAG, "updateSent success");
    }

    //Add data
    public boolean addData(SessionReadings sessionReadings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, sessionReadings.getDate());
        contentValues.put(COL2, sessionReadings.getDay());
        contentValues.put(COL3, sessionReadings.getAMPM());
        contentValues.put(COL4, sessionReadings.getEntries());
        contentValues.put(COL5, sessionReadings.getSYSAvg());
        contentValues.put(COL6, sessionReadings.getDYAvg());
        contentValues.put(COL7, sessionReadings.getHRAvg());
        contentValues.put(COL8, sessionReadings.getSent());

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

    //Retrieve AM data
    public Cursor getAMData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {COL0, COL5, COL6};
        String selection = COL3 + " = ?";
        String[] selectionArgs = {"AM"};

        Cursor data = db.query(TABLE_NAME, cols, selection, selectionArgs, null, null, null);

        Log.d(TAG, "getAMData succcess");
        return data;
    }

    //Retrieve PM data
    public Cursor getPMData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {COL0, COL5, COL6};
        String selection = COL3 + " = ?";
        String[] selectionArgs = {"PM"};

        Cursor data = db.query(TABLE_NAME, cols, selection, selectionArgs, null, null, null);

        Log.d(TAG, "getPMData success");
        return data;
    }

    //Retrieve Unsent data
    public Cursor getUnsentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {COL1, COL2, COL3, COL5, COL6, COL7};
        String selection = COL8 + " = ?";
        String[] selectionArgs = {"non"};

        Cursor data = db.query(TABLE_NAME, cols, selection, selectionArgs, null, null, null);

        Log.d(TAG, "getUnsentData success");
        return data;
    }
}
