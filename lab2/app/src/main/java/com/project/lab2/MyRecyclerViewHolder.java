package com.project.lab2;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiComment;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
    private View root;
    private TextView textView;

    private VKApiComment item;
    private MyRecyclerViewAdapter adapter;


    public MyRecyclerViewHolder(final View root, final MyRecyclerViewAdapter adapter) {
        super(root);
        this.root = root;
        this.adapter = adapter;
        textView = (TextView) root.findViewById(R.id.recycler_view_item_textview);
    }

    public void bindData(VKApiComment item) {
        this.item = item;
        textView.setText(item.text);

    }

    public VKApiComment getData(){
        return item;
    }
}
