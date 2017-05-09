package com.project.tasktracker;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class TaskRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    View root;
    TextView name;
    TextView description;
    CheckBox completeCheckBox;

    View priorityView;

    //int priority;
    //int position;
    TaskItem taskItem;
    TaskRecyclerViewAdapter adapter;


    public TaskRecyclerViewHolder(View root, TaskRecyclerViewAdapter adapter) {
        super(root);
        this.root = root;
        this.adapter = adapter;
        name = (TextView) root.findViewById(R.id.textView_name);
        description = (TextView) root.findViewById(R.id.textView_description);
        completeCheckBox = (CheckBox) root.findViewById(R.id.checkBox_status);
        priorityView = (View) root.findViewById(R.id.task_item_priority_colored_view);
        root.setOnCreateContextMenuListener(this);
        //root.setMen
    }

    public void bindData(TaskItem taskItem, int position) {
        this.taskItem = taskItem;
        name.setText(taskItem.getName());
        description.setText(taskItem.getDescription());
        completeCheckBox.setChecked(taskItem.isFinished());
        changeColorDependingOnItemPriority(taskItem.getPriority());

        //this.position = position;
        //root.setOnCreateContextMenuListener(this);
    }

    public TaskItem getData(){
        return taskItem;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "Edit").setOnMenuItemClickListener(this);
        menu.add(0, 1, 0, "Delete").setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        adapter.onMenuItemClick(item, taskItem);

        return false;
    }

    private void changeColorDependingOnItemPriority(int priority) {
        switch (priority) {
            case 0:
                priorityView.setBackgroundColor(0);
                break;
            case 1:
                priorityView.setBackgroundColor(Color.parseColor("#ffcc66"));
                break;
            case 2: default:
                priorityView.setBackgroundColor(Color.parseColor("#ff704d"));
                break;
        }
    }
}