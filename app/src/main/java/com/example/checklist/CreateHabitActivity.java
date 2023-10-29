package com.example.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateHabitActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextData;
    private HabitDao habitDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        setupViews();
        setupDatabase();
    }

    private void setupViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextData = findViewById(R.id.editTextDays);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            createEntry();
        });
    }

    private void setupDatabase() {
        habitDao = DBHelper.getInstance(this);
    }

    private void createEntry() {
        String name = editTextName.getText().toString().trim();
        String dayString = editTextData.getText().toString().trim();

        if (!checkValidData(name, dayString)) {
            Toast.makeText(this, "Данные введены не корректно", Toast.LENGTH_SHORT).show();
            return;
        }

        int day = NumberUtils.stringToInt(dayString);
        Habit habit = new Habit(name, day);
        habitDao.createHabit(habit);

        Toast.makeText(this, "Привычка и количество дней добавлены в Ваш список", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean checkValidData(String name, String day) {
        boolean nameValid = !name.isEmpty() && name.length() >= 1;
        boolean dayValid = !day.isEmpty() && NumberUtils.isNumber(day);
        return nameValid && dayValid;
    }
}