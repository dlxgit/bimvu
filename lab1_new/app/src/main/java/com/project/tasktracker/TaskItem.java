package com.project.tasktracker;


import android.os.Bundle;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class TaskItem implements Comparable<TaskItem> {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private String name;
    private String description;
    private boolean isFinished;
    private int priority;
    private Date deadline;

    public TaskItem() {
        this.name = new String();
        this.description = new String();
        this.deadline = new Date();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("header", name);
        bundle.putString("descriptionEditText", description);
        bundle.putLong("dateEditText", deadline.getTime());
        bundle.putInt("priority", priority);
        bundle.putBoolean("isFinished", isFinished);
        return bundle;
    }

    public TaskItem(Bundle bundle) {
        this.name = bundle.getString("header");
        this.description = bundle.getString("descriptionEditText");
        this.priority = bundle.getInt("priority");
        this.deadline = new Date(bundle.getLong("dateEditText"));
        this.isFinished = bundle.getBoolean("isFinished");
    }

    public String getStringDate() {
        return DATE_FORMAT.format(deadline);
    }

    @Override
    public int compareTo(@NonNull TaskItem o) {
        if (isFinished() != o.isFinished()) {
            return isFinished() ? 1 : -1;
        }
        int priorityComparsionResult = Integer.compare(o.getPriority(), getPriority());
        if(priorityComparsionResult != 0) {
            return priorityComparsionResult;
        }
        int dateComparsionResult = getDeadline().compareTo(o.getDeadline());
        return dateComparsionResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}