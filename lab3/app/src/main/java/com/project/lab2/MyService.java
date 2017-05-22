package com.project.lab2;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.vk.sdk.api.model.VKApiComment;

import java.util.Collection;
import java.util.Collections;

public class MyService extends Service {

    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }


    private static final int NOTIFICATION_ID = 234;

    private Handler mHandler;
    VkData mCollectedData;

    public MyService() {
        super();
        mCollectedData = new VkData();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand()");
        if(intent == null) {
            System.out.println("Service [null]");
            return START_NOT_STICKY;
        }
        mCollectedData.setmFirstOffset(intent.getIntExtra("firstOffset", 0));

        final int delay = 5000; //milliseconds
        mHandler.postDelayed(new Runnable(){
            public void run(){
                VkData newData = VkUtils.loadComments(mCollectedData.getmFirstOffset() + 1, VkUtils.REQUEST_COMMENTS_COUNT);
                System.out.println("Tick.");
                if(!newData.getmComments().isEmpty()) {
                    System.out.println("successful(" + newData.getmFirstOffset() + ")");
                    Collections.reverse(newData.getmComments());
                    mCollectedData.getmComments().addAll(0, newData.getmComments());
                    mCollectedData.setTotalItemCount(newData.getmTotalItemCount());
                    mCollectedData.setmFirstOffset(newData.getmFirstOffset());

                    System.out.println("Need to add some!");
                    sendCollectedData();
                } else {
                    System.out.println("bad");
                }

                System.out.println("[service] offset:" + String.valueOf(mCollectedData.getmFirstOffset()) + "/" + String.valueOf(newData.getmFirstOffset()));
                mHandler.postDelayed(this, delay);
            }
        }, delay);

        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void debugPrintLst() {
        System.out.println("_____");
        for(VKApiComment com : mCollectedData.getmComments()) {
            System.out.println(com.text);
        }
        System.out.println("_____");
    }

    private void sendCollectedData() {
        int newFirstOffset = mCollectedData.getmFirstOffset();
        System.out.println("COLLECTED: " + newFirstOffset);
        debugPrintLst();

        Intent mainActivityIntent = new Intent(MyService.this, MainActivity.class);


        mainActivityIntent.putExtra("newData", mCollectedData.toJsonString());

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MyService.this)
                                                            .addParentStack(MainActivity.class)
                                                            .addNextIntent(mainActivityIntent);

        //Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_SINGLE_TOP
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification mNotification =
                new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.ic_ab_app)
                        .setContentTitle("Content changed")
                        .setContentText("There are new posts!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

        //mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification);
        //this.firstItemOffset = newFirstOffset;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service destroy");

    }
}