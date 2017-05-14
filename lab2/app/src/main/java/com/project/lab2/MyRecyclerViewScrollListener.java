package com.project.lab2;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;


public abstract class MyRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;
    int mLoadCount = 0;
    VKList<VKApiComment> mItems;


    public MyRecyclerViewScrollListener(LinearLayoutManager layoutManager, VKList<VKApiComment> items) {
        super();
        this.layoutManager = layoutManager;
        this.mItems = items;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        load();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        load();
    }

    private void load() {
        int currentVisible = layoutManager.findLastVisibleItemPosition();
        if(currentVisible >= mItems.size() - VkUtils.REQUEST_COMMENTS_COUNT) {
            System.out.println("LoadingItems");
            onLoadItems();
            ++mLoadCount;
        }
    }

    public abstract void onLoadItems();
}