package com.mytaskviewer.taskviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/*
    если в дату ввести .... он не будет ругаться

 */



public class MainActivity extends AppCompatActivity {

    private ListView listView;

   // private BoxAdapter adapter;
    private TaskItem item;
    ArrayList<TaskItem> items;

    String header;
    String description;
    int priority;
    String date;

    TaskItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        listView = (ListView) findViewById(R.id.taskListView);
//
//
//
//        item = new TaskItem(extras.getString("name"), extras.getString("description"), extras.getInt("priority"), extras.getString("date"));
//        header = extras.getString("name");
//        description = extras.getString("description");
//        priority = extras.getInt("priority");
//        date = extras.getString("date");


        //listView.getItems

        //listView.addFooterView();

        if(items == null) {
            items = new ArrayList<>();
            items.add(new TaskItem("asd", "asdd", 1, "1.1.1"));
            adapter = new TaskItemAdapter(this, items);
            listView = (ListView) findViewById(R.id.taskListView);
            listView.setAdapter(adapter);
            items.add(new TaskItem("asdd", "asddd", 1, "1.1.1"));
        }

        //TODO: как лучше проверять на реально пришедший интент(непустой бундл)?  UPD: fix с onNewIntent()
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            items.add(new TaskItem(intent));
        }
        //intent.


//        Bundle extras = getIntent().getExtras();
//        item = new TaskItem(extras.getString("name"), extras.getString("description"), extras.getInt("priority"), extras.getString("date"));
//        header = extras.getString("name");
//        description = extras.getString("description");
//        priority = extras.getInt("priority");
//        date = extras.getString("date");
//
//        items.add(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //items.add(new TaskItem(intent));

    }

    public void goToNewActivity(View v) {
        Intent intent = new Intent(this, AddingTaskActivity.class);
        startActivity(intent);
    }
}
