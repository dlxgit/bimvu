package com.project.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter adapter;
    private OnItemEditCallback onItemEditCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.sortItems();
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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
                startEditTaskActivity(index);
            }
        };


        recyclerView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        adapter = new TaskRecyclerViewAdapter(this, onItemEditCallback);
        adapter.setItems(DataManager.loadData(this));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void startCreateTaskActivity() {
        Intent intent = new Intent(this, AddingTaskActivity.class);
        startActivityForResult(intent, 1);
    }

    private void startEditTaskActivity(int itemPosition) {
        Intent intent = new Intent(MainActivity.this, AddingTaskActivity.class);
        intent.putExtras(adapter.getItem(itemPosition).toBundle());
        intent.putExtra("oldItemId", itemPosition);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.saveData(adapter.getItems(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            TaskItem item = new TaskItem(bundle);
            if(resultCode == 1) {
                adapter.addItem(item);
            }
            else if (resultCode == 2) {
                adapter.onReplace(bundle.getInt("oldItemId"), item);
            }
        }
    }
}