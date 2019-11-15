package com.prography.prography_androidstudy.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todos")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    private String title;
    private String description;
    private Date dateTime;
    private boolean alarm0min;
    private boolean alarm10min;
    private boolean alarm30min;
    private boolean alarm60min;

    public Todo() {

    }

    public Todo(String title, String description, Date dateTime, boolean alarm0min, boolean alarm10min, boolean alarm30min, boolean alarm60min) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.alarm0min = alarm0min;
        this.alarm10min = alarm10min;
        this.alarm30min = alarm30min;
        this.alarm60min = alarm60min;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAlarm0min() {
        return alarm0min;
    }

    public void setAlarm0min(boolean alarm0min) {
        this.alarm0min = alarm0min;
    }

    public boolean isAlarm10min() {
        return alarm10min;
    }

    public void setAlarm10min(boolean alarm10min) {
        this.alarm10min = alarm10min;
    }

    public boolean isAlarm30min() {
        return alarm30min;
    }

    public void setAlarm30min(boolean alarm30min) {
        this.alarm30min = alarm30min;
    }

    public boolean isAlarm60min() {
        return alarm60min;
    }

    public void setAlarm60min(boolean alarm60min) {
        this.alarm60min = alarm60min;
    }
}
