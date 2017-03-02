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

        //TODO: this is KOSTIL
        items = DataManager.loadData(getBaseContext());
        if(items.size() > 0) {
            items.remove(items.size() - 1);
        }

        adapter = new TaskItemAdapter(this, items);
        listView = (ListView) findViewById(R.id.taskListView);
        listView.setAdapter(adapter);

//        if(items == null) {
//            items = DataManager.loadData(getBaseContext());
//            //items = new ArrayList<>();
//            adapter = new TaskItemAdapter(this, items);
//            listView = (ListView) findViewById(R.id.taskListView);
//            listView.setAdapter(adapter);
////adapter.notifyDataSetChanged();
//            items.add(new TaskItem("asd", "asdd", 1, "1.1.1"));
//
//
//            items.add(new TaskItem("asdd", "asddd", 1, "1.1.1"));
//            //onDestroy();
//        }

        //TODO: как лучше проверять на реально пришедший интент(непустой бундл)?  UPD: fix с onNewIntent()
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            items.add(new TaskItem(intent));
        }

    }

    public void goToNewActivity(View v) {
        startActivityForResult(new Intent(this, AddingTaskActivity.class), 1);
    }

    @Override
    protected void onStop() {
        DataManager.saveData(items, getBaseContext());
        //onCreate(this.getIntent().getExtras());
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("OnActivityResult!");
        if(data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            items.add(new TaskItem(data));
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        System.out.println("ImDestroying!");
        //DataManager.saveData(items, getBaseContext());
        super.onDestroy();
    }
}
