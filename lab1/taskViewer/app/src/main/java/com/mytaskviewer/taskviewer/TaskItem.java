package com.mytaskviewer.taskviewer;



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
}