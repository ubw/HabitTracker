package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private HabitDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //需在build中添加compile 'com.android.support:design:26.1.0'
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDBHelper(this);

        Button button = (Button) findViewById(R.id.sign_all);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatebaseInfo();
                displayDatabaseInfo();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    //更新
    private void updateDatebaseInfo() {
        HabitDBHelper mDbHelper = new HabitDBHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        db.execSQL("update " + HabitContract.HabitEntry.TABLE_NAME + " set " + HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES
                + "=" + HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES + "+1;");
    }

    //读取
    private void displayDatabaseInfo() {
        HabitDBHelper mDbHelper = new HabitDBHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE,
                HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME,
                HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES
        };
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        try {
            displayView.setText("Number of rows in pets database table: " + cursor.getCount() + "\n\n");
            displayView.append(HabitContract.HabitEntry._ID + "-" +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + "-" +
                    HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE + "-" +
                    HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME + "-" +
                    HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int alarmtimeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME);
            int createtimeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE);
            int doneTimesColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentAlarmTime = cursor.getString(alarmtimeColumnIndex);
                String currentCreateTime = cursor.getString(createtimeColumnIndex);
                int currentDoneTimes = cursor.getInt(doneTimesColumnIndex);

                displayView.append("\n" + currentId + "-" +
                        currentName + "-" +
                        currentAlarmTime + "-" +
                        currentCreateTime + "-" +
                        currentDoneTimes + "\n");
            }

        } finally {
            cursor.close();
        }
    }
}
