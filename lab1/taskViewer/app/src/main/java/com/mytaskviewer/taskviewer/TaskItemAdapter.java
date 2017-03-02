package com.mytaskviewer.taskviewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TaskItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<TaskItem>  data = null;
    LayoutInflater layoutInflater;

    public TaskItemAdapter(Context context, ArrayList<TaskItem> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null)
        {
            view = layoutInflater.inflate(R.layout.item, parent, false);
            /*
                        holder = new TaskItemHolder();
            holder.name = (EditText)row.findViewById(R.id.textField_taskName);
            holder.description = (EditText)row.findViewById(R.id.textField_description);
            holder.priorityRadioGroup = (RadioGroup) row.findViewById(R.id.priorityRadioGroup);
            holder.date = (EditText)row.findViewById(R.id.textField_date);

            view.setTag(holder);
             */
        }



        TaskItem taskItem = getTaskItem(position);

        //TODO: каким образом происходит заполнение view, если мы лишь инициализируем nameTV??

        TextView nameTV = (TextView) view.findViewById(R.id.textView_name);
        nameTV.setText(taskItem.getHeader());
        TextView descriptionTV = (TextView) view.findViewById(R.id.textView_description);
        descriptionTV.setText(taskItem.getDescription());
        TextView dateTV = (TextView) view.findViewById(R.id.textView_date);
        dateTV.setText(taskItem.getDate());
//        CheckBox completeStatus = (CheckBox) view.findViewById(R.id.textView_name);
//        completeStatus.setText(taskItem.getHeader());
        TextView priorityTV = (TextView) view.findViewById(R.id.textView_priority);
        priorityTV.setText(String.valueOf(taskItem.getPriority()));


        return view;
    }

    private TaskItem getTaskItem(int position){
        return (TaskItem) getItem(position);
    }
}