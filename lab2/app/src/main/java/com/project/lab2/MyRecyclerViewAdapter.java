package com.project.lab2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.model.VKApiComment;

import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {
    List<VKApiComment> mItems;

    public MyRecyclerViewAdapter(List<VKApiComment> m_items) {
        this.mItems = m_items;
    }

    public List<VKApiComment> getItems() {
        return mItems;
    }

    public void setItems(List<VKApiComment> m_items) {
        this.mItems = m_items;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyRecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        holder.bindData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
