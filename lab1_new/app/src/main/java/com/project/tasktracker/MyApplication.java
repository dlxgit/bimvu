package com.project.tasktracker;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {
    private static MyApplication instance;
    private static ArrayList<TaskItem> data;

    public static MyApplication getInstance() {
        return instance;
    }

    public ArrayList<TaskItem> getData() {
        return data;
    }

    public void reloadData() {
        //data = DataManager.loadData(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        data = DataManager.loadData(this);
        instance = this;
    }

    public void saveData() {
        DataManager.saveData(data, this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        saveData();
    }
}