package com.project.lab2;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class MyService extends Service {

    private static final int NOTIFICATION_ID = 234;

    private Handler mHandler;
    int time = 0;
    int itemCount;

    public MyService() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //VKList<VKApiComment> DataManager.loadData(getBaseContext());
        //IntentService
//        HandlerThread thread = new HandlerThread("");
//        thread.start();
//        mHandler = new Handler(thread.getLooper());
        mHandler = new Handler();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service is active!");
        final int delay = 5000; //milliseconds
        mHandler.postDelayed(new Runnable(){
            public void run(){
                //do something
                System.out.println("Service is active!");

                Intent mainActivityIntent = new Intent(MyService.this, MainActivity.class);
                TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MyService.this)
                                                    .addParentStack(MainActivity.class)
                                                    .addNextIntent(mainActivityIntent);

                PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification mNotification =
                        new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.ic_ab_app)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!").build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, mNotification);


                mHandler.postDelayed(this, delay);
            }
        }, delay);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //@Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //final Handler h = new Handler();
        final int delay = 5000; //milliseconds
        mHandler.postDelayed(new Runnable(){
            public void run(){
                //do something
                System.out.println("Service is active!");
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.ic_ab_app)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");

                mHandler.postDelayed(this, delay);
            }
        }, delay);


    }
}
