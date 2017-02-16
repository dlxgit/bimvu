package com.mytaskviewer.taskviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adapter = new ArrayAdapter<TaskItem>(this, );

        //adapter = new BoxAdapter(this, items);

//        listView = (ListView) findViewById(R.id.taskListView);
//
//        Bundle extras = getIntent().getExtras();
//
//        item = new TaskItem(extras.getString("name"), extras.getString("description"), extras.getInt("priority"), extras.getString("date"));
//        header = extras.getString("name");
//        description = extras.getString("description");
//        priority = extras.getInt("priority");
//        date = extras.getString("date");


        //listView.getItems

        //listView.addFooterView();
    }

    public void goToNewActivity(View v) {
        Intent intent = new Intent(this, AddingTaskActivity.class);
        startActivity(intent);
    }
}
