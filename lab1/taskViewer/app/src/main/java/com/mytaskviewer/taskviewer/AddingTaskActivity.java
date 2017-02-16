package com.mytaskviewer.taskviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class AddingTaskActivity extends Activity {

    EditText name;
    EditText description;
    EditText date;
    RadioGroup priorityRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addingtask_layout);
        name = (EditText) findViewById(R.id.textField_taskName);
        description = (EditText) findViewById(R.id.textField_description);
        date = (EditText) findViewById(R.id.textField_date);
        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);
    }

    private int getPriority() {
        return priorityRadioGroup.getCheckedRadioButtonId();
//        priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case R.id.radioButtonLow:
//                        return 0;
//                        break;
//                    case R.id.radioButtonMedium:
//                        // do operations specific to this selection
//                        break;
//                    case R.id.radioButtonHigh:
//                        // do operations specific to this selection
//                        break;
//                }
//            }
//        });
    }


    public void goToPrevActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("header", name.getText().toString());
        intent.putExtra("description", description.getText().toString());
        intent.putExtra("date", date.getText().toString());
        intent.putExtra("priority", String.valueOf(getPriority()));
        setResult(RESULT_OK, intent);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }
}
