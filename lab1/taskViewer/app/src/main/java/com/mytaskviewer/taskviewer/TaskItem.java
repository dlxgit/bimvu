package com.mytaskviewer.taskviewer;


import android.content.Intent;
import android.os.Bundle;

public class TaskItem {
    public enum Priority {
        LOW(0),
        MEDIUM(1),
        HIGHT(2);

        private Integer intValue;

        Priority(int intValue) {
            this.intValue = intValue;
        }
    }
    String header;
    String description;
    int priority;
    String date;
    boolean status = false;

    public TaskItem(String header, String description, int priority, String date) {
        this.header = header;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    public TaskItem(Intent intent){
        if (intent.getExtras().containsKey("header")) {
            //boolean isNew = extras.getBoolean("isNewItem", false);
            this.header = intent.getStringExtra("header");
            this.description = intent.getStringExtra("description");
            this.priority = intent.getIntExtra("priority", 0);
            this.date = intent.getStringExtra("date");
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}