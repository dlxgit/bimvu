package com.project.tasktracker;


import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TaskItem {
    String name;
    String description;
    boolean isFinished;
    int priority;
    Date deadline;



    public TaskItem() {
        this.name = new String();
        this.description = new String();
        this.deadline = new Date();
    }

    public TaskItem(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.isFinished = false;

        this.deadline = new Date(1,1,1);
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

    public static ArrayList<TaskItem> getItemsFromBundle(Bundle bundle) {
        //Serializable serializable = bundle.getSerializable("allitems");
        ArrayList<Bundle> source = (ArrayList<Bundle>) bundle.getSerializable("allitems");
        ArrayList<TaskItem> result = new ArrayList<>();
        for(int i = 0; i < source.size(); ++i) {
            result.add(new TaskItem(source.get(i)));
        }

        return result;
    }

    public TaskItem(Bundle bundle) {
        if (bundle.containsKey("header")) {

            Date date = new Date();
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            //try {
            String strr = bundle.getString("dateEditText");
            //this.deadline = format.parse(str);
            this.deadline = new Date(222222);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            }
//            catch (){
//
//            }
        }

        this.name = bundle.getString("header");
        this.description = bundle.getString("descriptionEditText");
        this.priority = bundle.getInt("priority");
        this.deadline = new Date();
        this.isFinished = bundle.getBoolean("isFinished");
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