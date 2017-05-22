package com.project.lab2;

import android.content.Context;
import android.os.AsyncTask;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;


public class SaveAsyncTask extends AsyncTask<Void, Void, Void> {

    VKList<VKApiComment> mData;
    Context mContext;

    public SaveAsyncTask(VKList<VKApiComment> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DataManager.saveData(mData, mContext);
        return null;
    }
}
