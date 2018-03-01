package com.example.monishakram.snapandplot;


class DataPoint {
    long x, y;
    DataPoint(long x, long y){
        this.x = x;
        this.y = y;
    }
    DataPoint(){
        x = y = 0;
    }
    void set(long x, long y){
        this.x = x;
        this.y = y;
    }
    DataPoint get(){
        return this;
    }
}
