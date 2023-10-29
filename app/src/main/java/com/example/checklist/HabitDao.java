package com.example.checklist;

import java.util.List;

public interface HabitDao {
    List<Habit> getHabits();

    void createHabit(Habit habit);

    void deleteHabit(long id);
}

