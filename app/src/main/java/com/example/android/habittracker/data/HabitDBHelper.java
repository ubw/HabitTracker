package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="habits.db";
    public HabitDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME + " TEXT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES + " INTEGER DEFAULT 0); ";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteDatabase(SQLiteDatabase sqLiteDatabase){
        String SQL_DELETE_ENTRIES = "DROP TABLE" + HabitContract.HabitEntry.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
    }
}
