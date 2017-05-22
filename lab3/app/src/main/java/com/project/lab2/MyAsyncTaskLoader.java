package com.project.lab2;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


public class MyAsyncTaskLoader extends AsyncTaskLoader<VkData> {

    private int mOffset;
    private int mItemCount;

    public MyAsyncTaskLoader(Context context, int offset, int count) {
        super(context);
        this.mOffset = offset;
        this.mItemCount = count;
    }

    @Override
    public VkData loadInBackground() {
        VkData result = VkUtils.loadComments(mOffset, mItemCount);
        return result;
    }
}