package com.project.lab2;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class MyAsyncTaskLoader extends AsyncTaskLoader<VKList<VKApiComment>> {

    int mOffset;

    public MyAsyncTaskLoader(Context context, int offset) {
        super(context);
        this.mOffset = offset;
    }

    @Override
    public VKList<VKApiComment> loadInBackground() {
        VKList<VKApiComment> result = VkUtils.loadComments(mOffset);
        return result;
    }
}