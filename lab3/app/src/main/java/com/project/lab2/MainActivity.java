package com.project.lab2;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<VkData> {
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;
    VkData mData;
    int mTotalCommentsCount = 0;
    boolean isFirstLoad = true;
    boolean serviceConnected = false;

    int firstOffset;

    //Service mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //mItems = DataManager.loadData(getApplicationContext());
        mData = new VkData();
        //DataManager.saveData(mItems, this);

        mAdapter = new MyRecyclerViewAdapter(mData.getmComments());
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        //MyApplication app = (MyApplication) getApplication();

/*
        ServiceConnection  serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((MyService.LocalBinder) service).getService();
                serviceConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        */

        //startLoadingPrevComments(); //preload
        startLoadingPrevComments(); //loading

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener(layoutManager, mData.getmComments()) {
            @Override
            public void onLoadItems() {
                startLoadingPrevComments();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.saveData(mData, getApplicationContext());
    }

    @Override
    public Loader<VkData> onCreateLoader(int id, Bundle args) {
        int commentsOffset = args.getInt("offset");
        boolean isLoadingMostRecent = args.getBoolean("isLoadingMostRecent");
        int itemCount = args.getInt("itemCount");
        return new MyAsyncTaskLoader(this, commentsOffset, itemCount, isLoadingMostRecent);
    }

    @Override
    public void onLoadFinished(Loader<VkData> loader, VkData data) {
        if(isFirstLoad) {
            isFirstLoad = !isFirstLoad;
            mTotalCommentsCount = data.getmTotalItemCount();
            startLoadingPrevComments();
        }
        else {
            mData.getmComments().addAll(data.getmComments());
            System.out.println("LOADED_ITEMS: " + data.getmComments().size());
            System.out.println("WHOLE: " + data.getmTotalItemCount());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<VkData> loader) {}

    public void startLoadingPrevComments() {
        Bundle args = new Bundle();
        args.putBoolean("isLoadingMostRecent", false);
        args.putInt("itemCount", VkUtils.REQUEST_COMMENTS_COUNT);
        if(isFirstLoad) {
            args.putInt("offset", 0);
            }
        else {
            args.putInt("offset", mTotalCommentsCount - mData.getmComments().size() - VkUtils.REQUEST_COMMENTS_COUNT);
        }

        Loader<VkData> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }

    public void startLoadingNextComments(int lastTotalCount) {
        Bundle args = new Bundle();
        args.putBoolean("isLoadingMostRecent", true);
        args.putInt("itemCount", mData.getmComments().size());

        int paramItemsCount = lastTotalCount - mData.getmComments().size();
        if(paramItemsCount > VkUtils.REQUEST_COMMENTS_COUNT) {
            paramItemsCount = VkUtils.REQUEST_COMMENTS_COUNT;
        }

        args.putInt("offset", paramItemsCount);

        Loader<VkData> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }
}