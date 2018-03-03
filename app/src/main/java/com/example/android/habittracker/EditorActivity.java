package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDBHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText habit_name;
    private TextView habit_begin_date;
    private TextView habit_do_it_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        habit_name = (EditText)findViewById(R.id.edit_habit_name);
        //设置日期
        habit_begin_date = (TextView)findViewById(R.id.begin_date);
        habit_begin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getFragmentManager(), "datePicker");
            }
        });

        //设置习惯执行时间
        habit_do_it_time = (TextView)findViewById(R.id.do_it_time);
        habit_do_it_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog2 = new TimePickerFragment();
                dialog2.show(getFragmentManager(), "timePicker");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    //插入语句
    private void insertHabit(){
        String nameString = habit_name.getText().toString().trim();
        String dateString = habit_begin_date.getText().toString().trim();
        String timeString = habit_do_it_time.getText().toString().trim();

        // Create database helper
        HabitDBHelper mDbHelper = new HabitDBHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, nameString);
        contentValues.put(HabitContract.HabitEntry.COLUMN_HABIT_CREATEDATE, dateString);
        contentValues.put(HabitContract.HabitEntry.COLUMN_HABIT_DOITTIME, timeString);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, contentValues);
        if(newRowId == -1){
            Toast.makeText(this, "Error insert", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Habit saved with row id:" + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                //退出编辑
                finish();
                return true;
            case R.id.action_delete:
                // Do nothing for now
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
