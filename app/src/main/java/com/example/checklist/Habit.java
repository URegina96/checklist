package com.example.checklist;

public class Habit {
    private long id;
    private String name;
    private int day;


    public Habit(String name, int day) {
        this.name = name;
        this.day = day;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDays(int day) {
        this.day = day;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", day=" + day +
                '}';
    }
}
