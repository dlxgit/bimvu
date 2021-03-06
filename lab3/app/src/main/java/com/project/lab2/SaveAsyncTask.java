package com.project.lab2;

import android.content.Context;
import android.os.AsyncTask;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;


public class SaveAsyncTask extends AsyncTask<Void, Void, Void> {

    private VkData mData;
    private Context mContext;

    public SaveAsyncTask(VkData data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DataManager.saveData(mData, mContext);
        return null;
    }
}
