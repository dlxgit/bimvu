package com.project.lab2;


import android.os.AsyncTask;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class MyAsyncTask extends AsyncTask<Void, Void, VKList<VKApiComment>> {
    public MyAsyncTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(VKList<VKApiComment> vkApiComments) {
        super.onPostExecute(vkApiComments);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(VKList<VKApiComment> vkApiComments) {
        super.onCancelled(vkApiComments);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected VKList<VKApiComment> doInBackground(Void... params) {
        MainActivity.
        return null;
    }
}
