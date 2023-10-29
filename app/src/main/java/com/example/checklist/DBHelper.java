package com.example.checklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper implements HabitDao {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "trackers";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DAY = "day";
    private static DBHelper instance;

    public static HabitDao getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTS + " (" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " + KEY_DAY + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    @Override
    public List<Habit> getHabits() {
        List<Habit> users = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_CONTACTS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(KEY_ID);
            long id = cursor.getLong(index);
            index = cursor.getColumnIndex(KEY_NAME);
            String name = cursor.getString(index);
            index = cursor.getColumnIndex(KEY_DAY);
            int day = cursor.getInt(index);
            Habit habit = new Habit(name, day);
            habit.setId(id);
            users.add(habit);
        }
        cursor.close();
        return users;
    }

    @Override
    public void createHabit(Habit habit) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, habit.getName());
        values.put(KEY_DAY, habit.getDay());
        this.getWritableDatabase().insert(TABLE_CONTACTS, null, values);
    }

    @Override
    public void deleteHabit(long id) {
        this.getWritableDatabase().delete(TABLE_CONTACTS, "id=?", new String[]{"" + id});
    }
}
