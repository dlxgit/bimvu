package com.project.tasktracker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<TaskItem> m_items;
    Activity activity;
    OnItemEditCallback onItemEditCallback;

    public TaskRecyclerViewAdapter(Activity activity, OnItemEditCallback onItemEditCallback) {
        super();
        this.onItemEditCallback = onItemEditCallback;
        layoutInflater = LayoutInflater.from(activity);

        ArrayList<TaskItem> items = new ArrayList<>();
        items.add(new TaskItem("asd", "desc", 0));
        items.add(new TaskItem("111", "222", 2));

        this.m_items = items;
    }

    @Override
    public TaskRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);

        return new TaskRecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(TaskRecyclerViewHolder holder, int position) {
        holder.bindData(m_items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }


    public void onDeleteTask(TaskItem taskItem) {
        //delete
    }

    public void addItem(TaskItem item) {
        m_items.add(item);
        sortData();
        notifyItemInserted(m_items.indexOf(item));
    }

    public TaskItem getItem(int id){
        return m_items.get(id);
    }

    private void sortData() {

    }

    public void deleteItem() {

    }

    public void onMenuItemClick(MenuItem menuItem, TaskItem taskItem) {
        if(menuItem.getTitle().equals("Edit")) {
            //adapter.on
            onItemEditCallback.onItemEdit(m_items.indexOf(taskItem));
        }
        else if (menuItem.getTitle().equals("Delete")) {
            onDeleteTask(taskItem);
        }
    }

    public void onReplace(int oldItemId, TaskItem newItem) {
        if(m_items.isEmpty()) {
            m_items.add(newItem);
        }
        else {
            m_items.remove(oldItemId);
            m_items.add(oldItemId, newItem);
            notifyItemRangeChanged(oldItemId, oldItemId + 1);
        }
    }

}