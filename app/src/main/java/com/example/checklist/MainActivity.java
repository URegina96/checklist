package com.example.checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private HabitDao habitDao;
    private HabitAdapter habitAdapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        setupButtons();
        setupDatabase();
        setupViewModel();

        List<Habit> habits = habitDao.getHabits();

        habitAdapter.update(habits);
    }

    private void setupViewModel() {
        ViewModelProvider.Factory factory = new MainViewModel.Factory(habitDao);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        viewModel.observeHabits(this, habits -> {
            habitAdapter.update(habits);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.reloadHabits();
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        ResourceProvider resourceProvider = new ResourceProviderImpl(this);
        habitAdapter = new HabitAdapter(getLayoutInflater(), resourceProvider);

        RecyclerView recyclerHabits = findViewById(R.id.recyclerHabits);
        recyclerHabits.setLayoutManager(manager);
        recyclerHabits.addItemDecoration(decoration);
        habitDao = DBHelper.getInstance(this);
        recyclerHabits.setAdapter(habitAdapter);
        habitAdapter.setOnDeleteUserListener(new OnDeleteHabitListener() {
            @Override
            public void onDelete(long id) {
                deleteHabit(id);
            }
        });
    }

    private void deleteHabit(long id) {
        habitDao.deleteHabit(id);
        List<Habit> habits = habitDao.getHabits();
        habitAdapter.update(habits);
    }

    private void setupDatabase() {
        habitDao = DBHelper.getInstance(this);
    }

    private void setupButtons() {
        Button buttonСreate = findViewById(R.id.buttonСreateHabit);
        buttonСreate.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateHabitActivity.class);
            startActivity(intent);
        });
        Button buttonUpdate = findViewById(R.id.buttonUpgreid);
        buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}