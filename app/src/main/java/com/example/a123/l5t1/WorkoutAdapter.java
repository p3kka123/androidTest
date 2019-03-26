package com.example.a123.l5t1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a123.l5t1.model.WorkoutPart;
import com.example.a123.l5t1.model.WorkoutPartBase;

import java.util.ArrayList;

public class WorkoutAdapter extends ArrayAdapter<WorkoutPartBase> {

    static final int VIEW_TYPE_PAUSE = 0;
    static final int VIEW_TYPE_WORKOUT = 1;
    static final int VIEW_TYPE_COUNT = 2;

    public WorkoutAdapter(Context context, ArrayList<WorkoutPartBase> workoutparts){
        super(context, 0, workoutparts);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        WorkoutPartBase base = getItem(position);
        if(base instanceof WorkoutPart){
            return VIEW_TYPE_WORKOUT;
        }else{
            return VIEW_TYPE_PAUSE;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkoutPartBase base = getItem(position);

        if(convertView == null){
            int layoutId = 0;
            if (getItemViewType(position) == VIEW_TYPE_WORKOUT){
                layoutId = R.layout.workout_list_item;
            }else{
                layoutId = R.layout.pause_list_item;
            }
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TextView time = convertView.findViewById(R.id.time1);
        time.setText(String.valueOf(base.getTime()));

        return convertView;
    }
}
