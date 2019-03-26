package com.example.a123.l5t1.model;

public class WorkoutPart extends WorkoutPartBase {

    private int time;

    @Override
    public int getTime() {
        return time;
    }

    public WorkoutPart(int num){
        this.time = num;
    }
}
