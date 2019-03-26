package com.example.a123.l5t1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.a123.l5t1.model.PausePart;
import com.example.a123.l5t1.model.WorkoutPart;
import com.example.a123.l5t1.model.WorkoutPartBase;

public class AddWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        findViewById(R.id.addButtonId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int length = 0;
        if(!((EditText)findViewById(R.id.secondEnter)).getText().toString().equals("")) {
            length = Integer.parseInt(((EditText) findViewById(R.id.secondEnter)).getText().toString());
        }
        if(((RadioButton)findViewById(R.id.pauseButtonRadio)).isChecked()){
            if(length > 0){
                PausePart pause = new PausePart(length);
                returnData(pause);
            }
        }else if(((RadioButton)findViewById(R.id.workoutButtonRadio)).isChecked()){
            if(length > 0){
                WorkoutPart workout = new WorkoutPart(length);
                returnData(workout);
            }
        }
    }

    private void returnData(WorkoutPartBase data){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("WORKOUT", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
