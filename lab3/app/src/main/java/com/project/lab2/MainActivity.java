package com.project.lab2;


import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import java.util.Collections;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<VkData> {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private VkData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = DataManager.loadData(getApplicationContext());
        handleServiceIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(mData.getmComments().isEmpty()) {
            startLoadingComments();
        }
        else {
            restartService(mData.getFirstOffset());
        }

        mAdapter = new MyRecyclerViewAdapter(mData.getmComments());
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener(layoutManager, mData.getmComments()) {
            @Override
            public void onLoadItems() {
                startLoadingComments();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {}
            @Override
            public void onError(VKError error) {}
        })){}
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.saveDataInBackground(mData, getApplicationContext());
    }

    @Override
    public Loader<VkData> onCreateLoader(int id, Bundle args) {
        int commentsOffset = args.getInt("offset");
        int itemCount = args.getInt("itemCount");
        return new MyAsyncTaskLoader(this, commentsOffset, itemCount);
    }

    @Override
    public void onLoadFinished(Loader<VkData> loader, VkData data) {
        boolean isFirstLoad = false;
        if(mData.getFirstOffset() < data.getFirstOffset() || data.getmComments().isEmpty()) { //if new elements are at top
            isFirstLoad = true;
        }
        VKList<VKApiComment> currentComments = mData.getmComments();
        VKList<VKApiComment> newComments = data.getmComments();
        Collections.reverse(newComments);

        mData.setTotalItemCount(data.getTotalItemCount());
        mData.setFirstOffset(data.getFirstOffset());

        currentComments.addAll(newComments);
        mAdapter.notifyDataSetChanged();

        if(isFirstLoad) {
            if(data.getmComments().isEmpty()) {
                startLoadingComments();
            }
            else {
                restartService(mData.getFirstOffset());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<VkData> loader) {}

    private void startLoadingComments() {
        Bundle args = new Bundle();
        args.putInt("itemCount", VkUtils.REQUEST_COMMENTS_COUNT);

        int requestedOffset = mData.getTotalItemCount() - mData.getmComments().size() - VkUtils.REQUEST_COMMENTS_COUNT;
        if(requestedOffset < 0) {
            requestedOffset = 0;
        }

        args.putInt("offset", requestedOffset);
        Loader<VkData> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }

    private void restartService(int newOffset) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("firstOffset", newOffset);
        startService(intent);
    }

    private void handleServiceIntent() {
        Intent intent = getIntent();
        VkData serviceItems = new VkData();
        if(intent != null) {
            String jsonComments = intent.getStringExtra("newData");
            if(jsonComments == null) {
                return;
            }

            System.out.println("json = " + jsonComments);
            serviceItems = new Gson().fromJson(jsonComments, VkData.class);
            if(serviceItems.getFirstOffset() > 0) {
                mData.getmComments().addAll(0, serviceItems.getmComments());
                mData.setTotalItemCount(serviceItems.getTotalItemCount());
                mData.setFirstOffset(serviceItems.getFirstOffset());
                restartService(mData.getFirstOffset());
            }
        }
    }
}