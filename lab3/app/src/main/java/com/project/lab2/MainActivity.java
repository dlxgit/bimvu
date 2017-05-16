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


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<VkResultModel> {
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;
    VKList<VKApiComment> mItems;
    int mTotalCommentsCount = 0;
    boolean isFirstLoad = true;
    boolean serviceConnected = false;

    //Service mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //mItems = DataManager.loadData(getApplicationContext());
        mItems = new VKList<>();
        //DataManager.saveData(mItems, this);

        mAdapter = new MyRecyclerViewAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

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

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener(layoutManager, mItems) {
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
        DataManager.saveData(mItems, getApplicationContext());
    }

    @Override
    public Loader<VkResultModel> onCreateLoader(int id, Bundle args) {
        int commentsOffset = args.getInt("offset");
        boolean isLoadingMostRecent = args.getBoolean("isLoadingMostRecent");
        int itemCount = args.getInt("itemCount");
        return new MyAsyncTaskLoader(this, commentsOffset, itemCount, isLoadingMostRecent);
    }

    @Override
    public void onLoadFinished(Loader<VkResultModel> loader, VkResultModel data) {
        if(isFirstLoad) {
            isFirstLoad = !isFirstLoad;
            mTotalCommentsCount = data.getmTotalItemCount();
            startLoadingPrevComments();
        }
        else {
            mItems.addAll(data.getmComments());
            System.out.println("LOADED_ITEMS: " + data.getmComments().size());
            System.out.println("WHOLE: " + data.getmTotalItemCount());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<VkResultModel> loader) {}

    public void startLoadingPrevComments() {
        Bundle args = new Bundle();
        args.putBoolean("isLoadingMostRecent", false);
        args.putInt("itemCount", VkUtils.REQUEST_COMMENTS_COUNT);
        if(isFirstLoad) {
            args.putInt("offset", 0);

            }
        else {
            args.putInt("offset", mTotalCommentsCount - mItems.size() - VkUtils.REQUEST_COMMENTS_COUNT);
        }

        Loader<VkResultModel> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }

    public void startLoadingNextComments(int lastTotalCount) {
        Bundle args = new Bundle();
        args.putBoolean("isLoadingMostRecent", true);
        args.putInt("itemCount", mItems.size());

        int paramItemsCount = lastTotalCount - mItems.size();
        if(paramItemsCount > VkUtils.REQUEST_COMMENTS_COUNT) {
            paramItemsCount = VkUtils.REQUEST_COMMENTS_COUNT;
        }

        args.putInt("offset", paramItemsCount);

        Loader<VkResultModel> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }
}