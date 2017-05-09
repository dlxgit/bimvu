package com.project.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskRecyclerViewAdapter adapter;
    OnItemEditCallback onItemEditCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateTaskActivity();
            }
        });

        onItemEditCallback = new OnItemEditCallback() {
            @Override
            public void onItemEdit(int index) {
                Intent intent = new Intent(MainActivity.this, AddingTaskActivity.class);
                intent.putExtras(adapter.getItem(index).toBundle());
                intent.putExtra("oldItemId", index);
                startActivityForResult(intent, 2);
            }
        };


        recyclerView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        adapter = new TaskRecyclerViewAdapter(this, onItemEditCallback);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void startCreateTaskActivity() {
        Intent intent = new Intent(this, AddingTaskActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("OnActivityResult!");

        if(data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            TaskItem item = new TaskItem(bundle);
            if(resultCode == 1) {
                adapter.addItem(item);
            }
            else if (resultCode == 2) {
                adapter.onReplace(bundle.getInt("oldItemId"), item);
            }

            //adapter.add
//            items.add(new TaskItem(data));
//            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}