package com.example.checklist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Habit>> habitsLiveData = new MutableLiveData<>(); //   содержит список объектов User. Это будет использоваться для наблюдения за изменениями в списке пользователей и соответствующего обновления нашего пользовательского интерфейса
    private final HabitDao habitDao;

    public MainViewModel(HabitDao habitDao) {
        this.habitDao = habitDao;
        reloadHabits();
    }

    void observeHabits(LifecycleOwner owner, Observer<List<Habit>> observer) {
        this.habitsLiveData.observe(owner, observer);
    }

    public void reloadHabits() {
        List<Habit> habits = habitDao.getHabits();
        habitsLiveData.postValue(habits);
    }


    static class Factory implements ViewModelProvider.Factory {
        private final HabitDao habitDao;

        public Factory(HabitDao habitDao) {
            this.habitDao = habitDao;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainViewModel.class)) {
                return (T) new MainViewModel(habitDao);
            }
            throw new IllegalArgumentException("view model not found");
        }
    }
}

