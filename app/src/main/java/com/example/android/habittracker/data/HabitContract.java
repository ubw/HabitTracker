package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {
    public abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_habits";
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_CREATEDATE="createdate";
        public static final String COLUMN_HABIT_DOITTIME="do_it_time";
        public static final String COLUMN_HABIT_DONETIMES="done_times";
        public static final String _ID = BaseColumns._ID;
    }
}
