package com.project.lab2;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public abstract class MyRecyclerScrollListener extends RecyclerView.OnScrollListener {

    static final int COUNT_UPD = 15;

    LinearLayoutManager layoutManager;
    int mDy = 0;
    int updatesCount = 0;

    public MyRecyclerScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int currentVisible = layoutManager.findFirstVisibleItemPosition();
        mDy += dy;
        if(mDy >  COUNT_UPD) {
            mDy = mDy % COUNT_UPD;
            //load();
            onNeedToLoad();

        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public abstract void onNeedToLoad();

}
