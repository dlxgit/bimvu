package com.mytaskviewer.taskviewer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<TaskItem> objects;

    BoxAdapter(Context context, ArrayList<TaskItem> taskItems) {
        ctx = context;
        objects = taskItems;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.addingtask_layout, parent, false);
        }

        TaskItem p = getTaskItem(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.textField_taskName)).setText(p.header);
        ((TextView) view.findViewById(R.id.textField_description)).setText(p.description);
        ((TextView) view.findViewById(R.id.textField_date)).setText(p.date);
        ((TextView) view.findViewById(R.id.textField_date)).setText(p.date);

        return view;
    }

    // товар по позиции
    TaskItem getTaskItem(int position) {
        return ((TaskItem) getItem(position));
    }

    // содержимое корзины
    ArrayList<TaskItem> getBox() {
        ArrayList<TaskItem> box = new ArrayList<TaskItem>();
        for (TaskItem p : objects) {
            // если в корзине
//            if (p.box)
//                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
//    RadioGroup.OnCheckedChangeListener myCheckChangeList = new RadioGroup.OnCheckedChangeListener() {
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            // меняем данные товара (в корзине или нет)
//            //getTaskItem((Integer) buttonView.getTag()).box = isChecked;
//        }
//    };
}