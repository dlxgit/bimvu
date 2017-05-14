package com.project.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    VKList<VKApiComment> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        items = new VKList<>();
        adapter = new MyRecyclerViewAdapter(items);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        loadComments();

        recyclerView.addOnScrollListener(new MyRecyclerScrollListener(layoutManager) {
            @Override
            public void onNeedToLoad() {
                System.out.println("wow");
                loadComments();
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

    public void loadComments() {
        final VKRequest requestComments;
        final VKParameters params = new VKParameters();
        params.put(VKApiConst.OWNER_ID, "1");
        params.put(VKApiConst.POST_ID, "1725537");
        params.put(VKApiConst.COUNT, "15");
        params.put(VKApiConst.OFFSET, items.size());

        requestComments = new VKRequest("wall.getComments", params);
        requestComments.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                items.addAll(parseResult(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }


    private VKList<VKApiComment> parseResult(VKResponse response) {
        Gson gson = new Gson();
        VKList<VKApiComment> result = new VKList<VKApiComment>();
        try {
            JSONArray commentsArray = response.json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < commentsArray.length(); ++i) {
                JSONObject currentJsonComment = (JSONObject) commentsArray.get(i);
                currentJsonComment.remove("attachments");
                String js = String.valueOf(currentJsonComment);

                VKApiComment currentComment = gson.fromJson(js, VKApiComment.class);
                result.add(currentComment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

