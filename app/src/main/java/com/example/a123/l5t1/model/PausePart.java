package com.example.a123.l5t1.model;

public class PausePart extends WorkoutPartBase {

    private int time;

    @Override
    public int getTime() {
        return time;
    }

    public PausePart(int num){
        this.time = num;
    }

}
