package com.example.monishakram.snapandplot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Monish Akram on 12/9/2017.
 * Related to all Graph operation to Draw on Graph
 */

class Graph {

    private DataPoint origin, center, value;
    private boolean originIsSet = false;
    private Paint axisColor;
    private Paint curveColor;
    Paint valueColor;
    private short labelDistance;
    private short levelOfMagnification;
    private final short MIN_LABEL_DISTANCE = 100;
    private final short MAX_LABEL_DISTANCE = 300;
    private final short MAX_MAGNIFICATION_LEVEL = 33;
    private final short MIN_MAGNIFICATION_LEVEL = -9;
    private int[] factor = new int[3];
    private String expression = "";
    private long height, width;
    private DecimalFormat df;

    Graph(){
        axisColor = new Paint();
        axisColor.setColor(Color.parseColor("#eeeeee"));  //Red
        axisColor.setStrokeWidth(3.0f);
        axisColor.setTextSize(27.0f);
        axisColor.setFakeBoldText(false);
        axisColor.setAntiAlias(true);
        curveColor = new Paint();
        curveColor.setColor(Color.parseColor("#00b8d4"));
        curveColor.setStrokeWidth(3.0f);
        curveColor.setStrokeCap(Paint.Cap.ROUND);
        curveColor.setAntiAlias(true);
        curveColor.setTextSize(20.0f);
        valueColor = new Paint();
        valueColor.setColor(Color.parseColor("#FFFF00"));
        valueColor.setStrokeWidth(2.0f);
        origin = new DataPoint(0, 0);
        value = new DataPoint(-10000, -20000);
        labelDistance = (MAX_LABEL_DISTANCE +MIN_LABEL_DISTANCE)/2;
        levelOfMagnification = 0;
        factor[0] = 1;
        factor[1] = 2;
        factor[2] = 5;
        expression = Conversion.infixToPostFix("x");
        df = new DecimalFormat(".####");

    }

    private void drawAxis(Canvas canvas){
        //Height and Width of canvas
        double difference = getFactor(levelOfMagnification), label;
        DataPoint p = new DataPoint();
        height = canvas.getHeight();
        width = canvas.getWidth();
        center = new DataPoint();
        center.x = width/2;
        center.y = height/2;

        //Draw Axis Lines
        if(inBound(new DataPoint(0, origin.y)))
            canvas.drawLine(0, origin.y, width, origin.y, axisColor);
        if(inBound(new DataPoint(origin.x, 0)))
            canvas.drawLine(origin.x, 0, origin.x, height, axisColor);

        //Draw Label for Origin
        canvas.drawText("0", origin.x +3, origin.y +27, axisColor);

        //Draw Label for left of X-Axis
        label = -difference;
        if(origin.x < width)
            p.set(origin.x -labelDistance, origin.y +27);
        else{
            label = label -difference *((origin.x -width)/labelDistance);
            p.set(width +((origin.x -width) %labelDistance) -labelDistance, origin.y +27);

        }

        while(inBound(p)){
            if(levelOfMagnification < 0)
                canvas.drawText(String.valueOf(Math.round(label *1000000)/1000000.0D), p.x, p.y, axisColor);
            else
                canvas.drawText(String.valueOf((long) label), p.x -20, p.y, axisColor);
            label -= difference;
            p.x -= labelDistance;
        }

        //Draw Label for right of X-Axis
        label = difference;
        if(origin.x > 0)
            p.set(origin.x +labelDistance, origin.y +27);
        else{
            label = label +difference *(-origin.x/labelDistance);
            p.set(labelDistance -(-origin.x) %labelDistance, origin.y +27);
        }
        while(inBound(p)){
            if(levelOfMagnification < 0)
                canvas.drawText(String.valueOf(Math.round(label *1000000)/1000000.0D), p.x -20, p.y, axisColor);
            else
                canvas.drawText(String.valueOf((long) label), p.x -5, p.y, axisColor);
            label += difference;
            p.x += labelDistance;
        }


        //Draw Label for down of Y-Axis
        label = -difference;
        if(origin.y > 0)
            p.set(origin.x +2, 10 +origin.y +labelDistance);
        else{
            label = label +difference *(origin.y/labelDistance);
            p.set(origin.x +2, 10 +labelDistance +(origin.y %labelDistance));

        }
        while(inBound(p)){
            if(levelOfMagnification < 0)
                canvas.drawText(String.valueOf(Math.round(label *1000000)/1000000.0D), p.x, p.y, axisColor);
            else
                canvas.drawText(String.valueOf((long) label), p.x, p.y, axisColor);
            label -= difference;
            p.y += labelDistance;
        }

        //Draw Label for Top of Y-Axis
        label = difference;
        if(origin.y < height)
            p.set(origin.x +2, origin.y +10 -labelDistance);
        else{
            label = label +difference *((origin.y -height)/labelDistance);
            p.set(origin.x +2, height -labelDistance +((origin.y -height) %labelDistance) +10);

        }
        while(inBound(p)){
            if(levelOfMagnification < 0)
                canvas.drawText(String.valueOf(Math.round(label *1000000)/1000000.0D), p.x, p.y, axisColor);
            else
                canvas.drawText(String.valueOf((long) label), p.x, p.y, axisColor);
            label += difference;
            p.y -= labelDistance;
        }
    }

    private double getFactor(short levelOfMagnification) {
        int i = levelOfMagnification %3;
        i = i < 0?3 +i:i;
        return Math.pow(10, Math.floor(levelOfMagnification/3.0f)) *factor[i];
    }

    private boolean inBound(DataPoint point){
        return     point.x >= -1.1*labelDistance && point.x < width +1.1*labelDistance
                && point.y >= -1.1*labelDistance && point.y < height +1.1*labelDistance;
    }

    void drawGraph(Canvas canvas) throws Exception {
        if(!originIsSet) {
            origin.set(canvas.getWidth() / 2, canvas.getHeight() / 2);
            originIsSet = true;
        }
        drawAxis(canvas);
    }

    void drawCurve(Canvas canvas) throws Exception {
        double difference = getFactor(levelOfMagnification)/ labelDistance;
        double lowerBound = -difference *origin.x;
        double factor = labelDistance/getFactor(levelOfMagnification);
        float x1, x2, y1, y2;
        x1 = (float) lowerBound;
        y1 = (float) (origin.y -f(x1) *factor);
        for(int i = 0; i <= width; i++){
            x2 = (float) (x1 +difference);
            y2 = (float) (origin.y -f(x2) *factor);
            canvas.drawLine(i, y1, i +1, y2, curveColor);
            x1 = x2;
            y1 = y2;
        }
        if(inBound(new DataPoint(value.x, 0))){
            value.y = (long) (origin.y -f((value.x -origin.x) *difference) *factor);
            canvas.drawLine(value.x, value.y, value.x, origin.y, valueColor);
            Paint x = new Paint();
            x.setStrokeWidth(10.f);
            x.setStrokeCap(Paint.Cap.ROUND);
            x.setAntiAlias(true);
            x.setColor(curveColor.getColor());
            String s = "(" + df.format((value.x -origin.x) *difference) +", " +df.format(f((value.x -origin.x) *difference)) +")";
            if(value.y -origin.y < 0)
                canvas.drawText(s, value.x -60, origin.y +50, curveColor);
            else
                canvas.drawText(s, value.x -60, origin.y -10, curveColor);
            canvas.drawPoint(value.x, value.y, x);
            value.x = value.y = -20000;
        }
    }

    private double f(double x) throws Exception {
        return Conversion.computePostFixExpression(expression, x);
    }

    void setExpression(String s){
        expression = Conversion.infixToPostFix(s);

    }

    void scrolling(float x, float y){
        origin.set((long)(origin.x -x), (long)(origin.y -y));
    }

    void scaling(float magnify){
        if(magnify < 1.0f){
            if(levelOfMagnification == MAX_MAGNIFICATION_LEVEL)
                return;
            labelDistance -= 2;
            if(labelDistance <= MIN_LABEL_DISTANCE){
                levelOfMagnification++;
                labelDistance = (MIN_LABEL_DISTANCE +MAX_LABEL_DISTANCE)/2;
            }
        }
        else{
            if(levelOfMagnification == MIN_MAGNIFICATION_LEVEL)
                return;
            labelDistance += 2;
            if(labelDistance >= MAX_LABEL_DISTANCE){
                levelOfMagnification--;
                labelDistance = (MIN_LABEL_DISTANCE +MAX_LABEL_DISTANCE)/2;
            }
        }
    }

    void drawPoint(float distanceX) {
        value.x = (long) distanceX;
    }
}