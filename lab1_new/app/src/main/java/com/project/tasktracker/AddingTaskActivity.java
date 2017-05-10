package com.project.tasktracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.ParseException;
import java.util.Date;

public class AddingTaskActivity extends Activity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    RadioGroup priorityRadioGroup;
    int priority;
    CheckBox completionCheckBox;

    SingleDateAndTimePickerDialog dlg;

    boolean isCreating = true; //to know where we came from

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        nameEditText = (EditText) findViewById(R.id.textField_taskName);
        descriptionEditText = (EditText) findViewById(R.id.textField_description);
        dateEditText = (EditText) findViewById(R.id.textField_date);
        completionCheckBox = (CheckBox) findViewById(R.id.completion_checkbox);

        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);

        priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void  onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonLow:
                        priority = 0;
                        break;
                    case R.id.radioButtonMedium:
                        priority = 1;
                        break;
                    case R.id.radioButtonHigh:
                        priority = 2;
                        break;
                }
            }
        });

        dlg = new SingleDateAndTimePickerDialog.Builder(this).title("Simple")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        dateEditText.setText(TaskItem.DATE_FORMAT.format(date).toString());
                    }
                }).build();


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.display();
            }
        });

        initializeViewsFromBundle(getIntent().getExtras());

        Button addButton = (Button) findViewById(R.id.adding_task_activity_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskItem taskItem = new TaskItem();
                taskItem.setName(nameEditText.getText().toString());
                taskItem.setDescription(descriptionEditText.getText().toString());
                taskItem.setFinished(completionCheckBox.isChecked());
                taskItem.setPriority(priority);

                if(dateEditText.getText().length() > 0)
                {
                    try {
                        taskItem.setDeadline(taskItem.DATE_FORMAT.parse(dateEditText.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(taskItem.getName().isEmpty() || taskItem.getDescription().isEmpty() || taskItem.getDeadline() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddingTaskActivity.this);
                    builder.setTitle("Error")
                            .setMessage("Please, fill all the fields.")
                            .setCancelable(false)
                            .setNegativeButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                    return;
                }

                Intent intent = new Intent();
                intent.putExtras(taskItem.toBundle());

                setResult(isCreating ? 1 : 2, intent);
                finish();
            }
        });


        priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void  onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonLow:
                        priority = 0;
                        break;
                    case R.id.radioButtonMedium:
                        priority = 1;
                        break;
                    case R.id.radioButtonHigh:
                        priority = 2;
                        break;
                }
            }
        });
    }

    private int getPriority() {
        return priorityRadioGroup.getCheckedRadioButtonId();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void initializeViewsFromBundle(Bundle bundle) {
        if(bundle == null) {
            return;
        }
        TaskItem item = new TaskItem(bundle);

        isCreating = false;

//        nameEditText.setText(bundle.getString("header"));
//        descriptionEditText.setText(bundle.getString("descriptionEditText"));
//        priorityRadioGroup.check(getRadioButton(bundle.getInt("priority")).getId());
//        completionCheckBox.setChecked(bundle.getBoolean("isFinished"));
//        dateEditText.setText(new Date(bundle.getLong("deadline")).toString());
        nameEditText.setText(item.getName());
        descriptionEditText.setText(item.getDescription());
        priorityRadioGroup.check(getRadioButton(item.priority).getId());
        completionCheckBox.setChecked(item.isFinished());
        dateEditText.setText(item.getStringDate());
    }

    private RadioButton getRadioButton(int index) {
        switch (index) {
            case 0: default:
                return (RadioButton) findViewById(R.id.radioButtonLow);
            case 1:
                return (RadioButton) findViewById(R.id.radioButtonMedium);
            case 2:
                return (RadioButton) findViewById(R.id.radioButtonHigh);
        }
    }
}
