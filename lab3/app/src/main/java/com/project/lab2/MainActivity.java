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
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;
    VkData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData = DataManager.loadData(getApplicationContext());
        //mData = new VkData();
        handleServiceIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(mData.getmComments().isEmpty()) {
            startLoadingComments();
        }
        else {
            restartService(mData.getmFirstOffset());
        }

        //mData = new VkData();

        //DataManager.saveData(mData, this);

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
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                System.out.println("onActivityResult()");
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                System.out.println("onActivityResulterr()");
            }
        }))
        {
            super.onActivityResult(requestCode, resultCode, data);
            System.out.println("onActivityResultt()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.saveDataInBackground(mData, getApplicationContext());
        //DataManager.saveData(mData, getApplicationContext());
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
        if(mData.getmFirstOffset() < data.getmFirstOffset() || data.getmComments().isEmpty()) { //if new elements are at top
            isFirstLoad = true;
        }

        VKList<VKApiComment> currentComments = mData.getmComments();
        VKList<VKApiComment> newComments = data.getmComments();
        Collections.reverse(newComments);
        mData.setTotalItemCount(data.getmTotalItemCount());
        mData.setmFirstOffset(data.getmFirstOffset());

        System.out.println("LOADED_ITEMS: " + data.getmComments().size() + " newTotal: " + data.getmTotalItemCount());

        //currentComments.addAll(0, newComments);
        currentComments.addAll(newComments);
//            if(data.getmFirstOffset() > mData.getmFirstOffset()) {
//                mData.setmFirstOffset(data.getmFirstOffset());
//                restartService(mData.getmFirstOffset());
//            }
        //mData.setmFirstOffset(data.getmFirstOffset());
        mAdapter.notifyDataSetChanged();
        if(isFirstLoad) {
            if(data.getmComments().isEmpty()) {
                startLoadingComments();
            }
            else {
                restartService(mData.getmFirstOffset());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<VkData> loader) {}

    private void startLoadingComments() {
        Bundle args = new Bundle();
        args.putInt("itemCount", VkUtils.REQUEST_COMMENTS_COUNT);

        int requestedOffset = mData.getmTotalItemCount() - mData.getmComments().size() - VkUtils.REQUEST_COMMENTS_COUNT;
        if(requestedOffset < 0) {
            requestedOffset = 0;
        }

        System.out.println("requestedOffset = " + requestedOffset);

        args.putInt("offset", requestedOffset);
        Loader<VkData> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }

    private void restartService(int newOffset) {
        System.out.println("Restarting service with newoffset: " + newOffset);
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("firstOffset", newOffset);
        startService(intent);
    }

    private void handleServiceIntent() {
        Intent intent = getIntent();
        System.out.println("OnCreate() intent");
        VkData serviceItems = new VkData();
        if(intent != null) {
            System.out.println("not_null");
            String jsonComments = intent.getStringExtra("newData");
            if(jsonComments == null) {
                return;
            }

            System.out.println("json = " + jsonComments);
            serviceItems = new Gson().fromJson(jsonComments, VkData.class);
            if(serviceItems.getmFirstOffset() > 0) {
                mData.getmComments().addAll(0, serviceItems.getmComments());
                mData.setTotalItemCount(serviceItems.getmTotalItemCount());
                mData.setmFirstOffset(serviceItems.getmFirstOffset());
                restartService(mData.getmFirstOffset());
            }
        }
    }
}