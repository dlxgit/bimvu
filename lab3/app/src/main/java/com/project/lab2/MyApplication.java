package com.project.lab2;

import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;


public class MyApplication extends android.app.Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VkAccessToken is invalid
            }
        }
    };

    private MyApplication instance;
    private VkData data;


    public MyApplication getInstance() {
        return instance;
    }

    public VkData getData() {
        return data;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        instance = this;

        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
    }
}