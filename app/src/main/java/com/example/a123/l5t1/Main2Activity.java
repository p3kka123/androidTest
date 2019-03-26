package com.example.a123.l5t1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.a123.l5t1.model.PausePart;
import com.example.a123.l5t1.model.WorkoutPartBase;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    int mode = 0;

    public TextToSpeech textToSpeech = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        WorkoutPartBase base = (WorkoutPartBase) intent.getSerializableExtra("com.example.myfirstapp.MESSAGE");


        final TextView textView = findViewById(R.id.timeID);
        textView.setText(Integer.toString(base.getTime()));

        final TextView textView3 = findViewById(R.id.workoutpausetext);

        if(base instanceof PausePart){
            textView3.setText("Pause");
        }else{
            textView3.setText("Workout");
            mode = 1;
        }


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    if(mode == 0) {
                        textToSpeech.speak("pause", TextToSpeech.QUEUE_FLUSH, null, "e");
                    }else{
                        textToSpeech.speak("workout", TextToSpeech.QUEUE_FLUSH, null, "e");
                    }
                }
                else {
                    Log.e("error", "no idea");
                }
            }
        });


        new CountDownTimer(base.getTime() * 1000, 1000){

            public void onTick(long millisUntilFinished){
                textView.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish(){
                finish();
            }

        }.start();


    }
}
