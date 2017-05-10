package com.project.tasktracker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;


public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<TaskItem> m_items;
    private OnItemEditCallback onItemEditCallback;

    public TaskRecyclerViewAdapter(Activity activity, OnItemEditCallback onItemEditCallback) {
        super();
        this.onItemEditCallback = onItemEditCallback;
        layoutInflater = LayoutInflater.from(activity);
    }

    public ArrayList<TaskItem> getItems() {
        return m_items;
    }

    public void setItems(ArrayList<TaskItem> m_items) {
        this.m_items = m_items;
        sortItems();
    }

    @Override
    public TaskRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskRecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(TaskRecyclerViewHolder holder, final int position) {
        holder.bindData(m_items.get(position));
    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }


    public void onDeleteTask(TaskItem taskItem) {
        int position = m_items.indexOf(taskItem);
        m_items.remove(position);
        notifyItemRemoved(position);
        MyApplication.getInstance().saveData();
    }

    public void addItem(TaskItem item) {
        m_items.add(item);
        sortItems();
        notifyItemInserted(m_items.indexOf(item));
    }

    public TaskItem getItem(int id){
        return m_items.get(id);
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
            m_items.add(newItem);
            sortItems();
        }
        notifyDataSetChanged();
        MyApplication.getInstance().saveData();
    }

    public void sortItems() {
        Collections.sort(m_items);
    }
}