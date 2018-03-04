package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDBHelper;

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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        db.execSQL(getString(R.string.update)
                + " "
                + HabitContract.HabitEntry.TABLE_NAME
                + " "
                + getString(R.string.set)
                + " "
                + HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES
                + getString(R.string.equal)
                + HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES
                + getString(R.string.add1));
    }

    //读取
    private void displayDatabaseInfo() {
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
            displayView.setText(getString(R.string.titleSumHabits)
                    + cursor.getCount()
                    + getString(R.string.enter)
                    + getString(R.string.enter));
            displayView.append(HabitContract.HabitEntry._ID + getString(R.string.slash) +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + getString(R.string.slash) +
                    HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE + getString(R.string.slash) +
                    HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME + getString(R.string.slash) +
                    HabitContract.HabitEntry.COLUMN_HABIT_DONETIMES + getString(R.string.enter));

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

                displayView.append(getString(R.string.enter) + currentId + getString(R.string.slash) +
                        currentName + getString(R.string.slash) +
                        currentAlarmTime + getString(R.string.slash) +
                        currentCreateTime + getString(R.string.slash) +
                        currentDoneTimes + getString(R.string.enter));
            }

        } finally {
            cursor.close();
        }
    }
}
