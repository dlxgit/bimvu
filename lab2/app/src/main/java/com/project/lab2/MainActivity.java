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


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<VKList<VKApiComment>> {
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;
    VKList<VKApiComment> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //mItems = new VKList<>();
        mItems = DataManager.loadData(getApplicationContext());
        mAdapter = new MyRecyclerViewAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        startLoadingComments();

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener(layoutManager, mItems) {
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
        DataManager.saveDataInBackground(mItems, getApplicationContext());
    }

    @Override
    public Loader<VKList<VKApiComment>> onCreateLoader(int id, Bundle args) {
        int commentsOffset = args.getInt("offset");
        return new MyAsyncTaskLoader(this, commentsOffset);
    }

    @Override
    public void onLoadFinished(Loader<VKList<VKApiComment>> loader, VKList<VKApiComment> data) {
        mItems.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<VKList<VKApiComment>> loader) {}

    public void startLoadingComments() {
        Bundle args = new Bundle();
        args.putInt("offset", mItems.size());
        Loader<VKList<VKApiComment>> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }
}