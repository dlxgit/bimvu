package com.project.lab2;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        mItems = new VKList<VKApiComment>();
        mAdapter = new MyRecyclerViewAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        View v = findViewById(R.id.root_view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("startLoading (root)");
                //startLoadingComments();
            }
        });

        startLoadingComments();

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onNeedToLoad() {
                System.out.println("wow");
                //startLoadingComments();
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
    public Loader<VKList<VKApiComment>> onCreateLoader(int id, Bundle args) {
        int commentsOffset = args.getInt("offset");

        return new MyAsyncTaskLoader(this, commentsOffset);
    }

    @Override
    public void onLoadFinished(Loader<VKList<VKApiComment>> loader, VKList<VKApiComment> data) {
        System.out.println("loading finished");
        mItems.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<VKList<VKApiComment>> loader) {
        //.reset();
    }


    public void startLoadingComments() {
        Bundle args = new Bundle();
        args.putInt("offset", mItems.size());

        Loader<VKList<VKApiComment>> loader = getSupportLoaderManager().restartLoader(1, args, this);
        //Loader<VKList<VKApiComment>> loader = getSupportLoaderManager().restartLoader(1, args, this);
        loader.forceLoad();
    }
}

