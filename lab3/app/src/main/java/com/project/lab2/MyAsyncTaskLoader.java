package com.project.lab2;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class MyAsyncTaskLoader extends AsyncTaskLoader<VkResultModel> {

    int mOffset;

    public MyAsyncTaskLoader(Context context, int offset) {
        super(context);
        this.mOffset = offset;
    }

    @Override
    public VkResultModel loadInBackground() {
        VkResultModel result = VkUtils.loadComments(mOffset);
        return result;
    }
}