package com.example.a123.l5t1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a123.l5t1.model.PausePart;
import com.example.a123.l5t1.model.WorkoutPart;
import com.example.a123.l5t1.model.WorkoutPartBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    static final int ADD_NEW_WORKOUT_ID = 123;

    ArrayList<WorkoutPartBase> workoutparts = new ArrayList<>();

    ListView listView = null;

    private int totalTime = 0;

    public static String filename = "filename";

    FileOutputStream outputStream;

    FileInputStream fileInputStream;

    public WorkoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listWorkout);
        findViewById(R.id.startWorkoutButton).setOnClickListener(this);

        adapter = new WorkoutAdapter(this, workoutparts);
        listView.setAdapter(adapter);

        try {
            InputStream fis = openFileInput(filename);
            if(fis != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                while (line != null && !line.isEmpty()){
                    ((TextView)findViewById(R.id.nopartsadded)).setVisibility(View.INVISIBLE);
                    int iend = line.indexOf(",");
                    String start = line.substring(0, iend);
                    String end = line.substring(iend+1, iend+2);
                    Log.d("asdf", start);
                    Log.d("asdf", end);
                    if(end.equals("p")){
                        PausePart p = new PausePart(Integer.parseInt(start));
                        workoutparts.add(p);
                        totalTime += p.getTime();
                        ((TextView)findViewById(R.id.totalLengthId)).setText("Total length " + (int)java.lang.Math.floor((double)totalTime/60) +  " minutes " + totalTime % 60 + " seconds");
                    }if(end.equals("w")){
                        WorkoutPart w = new WorkoutPart(Integer.parseInt(start));
                        workoutparts.add(w);
                        totalTime += w.getTime();
                        ((TextView)findViewById(R.id.totalLengthId)).setText("Total length " + (int)java.lang.Math.floor((double)totalTime/60) +  " minutes " + totalTime % 60 + " seconds");
                    }
                    line = reader.readLine();
                }
            }
        }catch (Exception e){
            Log.d("asdf", "error");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.new_workout) {
            Intent intent = new Intent(this, AddWorkoutActivity.class);
            startActivityForResult(intent, ADD_NEW_WORKOUT_ID);
            return true;
        }else{
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear workouts")
                    .setMessage("Do you really want to delete your workouts?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            workoutparts.clear();
                            adapter.notifyDataSetChanged();
                            ((TextView)findViewById(R.id.nopartsadded)).setVisibility(View.VISIBLE);
                            totalTime = 0;
                            ((TextView)findViewById(R.id.totalLengthId)).setText("Total length " + (int)java.lang.Math.floor((double)totalTime/60) +  " minutes " + totalTime % 60 + " seconds");
                            /*try{
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                String empty = "";
                                outputStream.write(empty.getBytes());
                                outputStream.close();
                            }catch (Exception e){
                                Log.d("asdf", "filewrite fail");
                            }*/
                            deleteFile(filename);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startWorkoutButton){
            for (int i = workoutparts.size() - 1 ; i >= 0 ; i--) {
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra(EXTRA_MESSAGE, workoutparts.get(i));
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String fileContents = "";


        for(int i = 0; i < workoutparts.size(); i++){
            fileContents += workoutparts.get(i).getTime() + ",";
            if(workoutparts.get(i) instanceof PausePart){
                fileContents += "p\n";
            }else{
                fileContents += "w\n";
            }
        }
        //FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
            Log.d("asdf", this.getFilesDir().toString());
        }catch (Exception e){
            Log.d("asdf", "filewrite fail");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_WORKOUT_ID && resultCode == RESULT_OK){
            WorkoutPartBase base = (WorkoutPartBase) data.getSerializableExtra("WORKOUT");
            workoutparts.add(base);
            ((TextView)findViewById(R.id.nopartsadded)).setVisibility(View.INVISIBLE);
            totalTime += base.getTime();
            ((TextView)findViewById(R.id.totalLengthId)).setText("Total length " + (int)java.lang.Math.floor((double)totalTime/60) +  " minutes " + totalTime % 60 + " seconds");
        }
    }
}
