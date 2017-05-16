package com.project.lab2;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


public class MyAsyncTaskLoader extends AsyncTaskLoader<VkResultModel> {

    boolean mIsLoadingMostRecent;
    int mOffset;
    int mItemCount;

    public MyAsyncTaskLoader(Context context, int offset, int count, boolean isLoadingMostRecent) {
        super(context);
        this.mOffset = offset;
        this.mIsLoadingMostRecent = isLoadingMostRecent;
        this.mItemCount = count;
    }

    @Override
    public VkResultModel loadInBackground() {
        VkResultModel result = VkUtils.loadComments(mOffset, mItemCount, mIsLoadingMostRecent);
        return result;
    }
}