package com.example.checklist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HabitAdapter extends RecyclerView.Adapter<HabitViewHolder> {
    private final List<Habit> habits = new ArrayList<>();
    private final LayoutInflater inflater;
    private final ResourceProvider resources;

    private OnDeleteHabitListener OnDeleteHabitListener;

    public HabitAdapter(LayoutInflater inflater, ResourceProvider resources) {
        this.inflater = inflater;
        this.resources = resources;
    }

    public void setOnDeleteUserListener(OnDeleteHabitListener listener) {
        this.OnDeleteHabitListener = listener;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.textId.setText(String.format(Locale.getDefault(), "%d", habit.getId()));
        holder.textName.setText(resources.string(R.string.habit_s, habit.getName()));
        holder.textDay.setText(resources.string(R.string.habit_d, habit.getDay()));
        holder.imageViewDelete.setOnClickListener(v -> {
            OnDeleteHabitListener.onDelete(habit.getId());
        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Habit> newHabits) {
        this.habits.clear();
        this.habits.addAll(newHabits);
        notifyDataSetChanged();
    }
}


class HabitViewHolder extends RecyclerView.ViewHolder {
    TextView textId;
    TextView textName;
    TextView textDay;
    ImageView imageViewDelete;

    public HabitViewHolder(@NonNull View itemView) {
        super(itemView);
        textId = itemView.findViewById(R.id.text_habit_id);
        textName = itemView.findViewById(R.id.text_name);
        textDay = itemView.findViewById(R.id.text_day);
        imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
    }
}

interface OnDeleteHabitListener {
    void onDelete(long id);
}
