package com.example.monishakram.snapandplot;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by Monish Akram on 12/9/2017.
 * My Own GraphView design for all related activities of graphs.
 */

public class GraphView extends SurfaceView{
    DataPoint origin;
    String TAG = "GraphView";
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;
    MyTouchListener myTouchListener;
    Graph graph;
    SurfaceHolder holder;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Log.i(TAG, "In Function Init");
        graph = new Graph();
        graph.setExpression("x");
        origin = new DataPoint();
        myTouchListener = new MyTouchListener(this, graph);
        gestureDetector = new GestureDetector(getContext(), myTouchListener);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), myTouchListener);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            graph.drawGraph(canvas);
            graph.drawCurve(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            graph.setExpression("x");
            invalidate();
            Toast.makeText(getContext(), "Invalid Expression", Toast.LENGTH_SHORT).show();
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public void setExpression(String expression){
        graph.setExpression(expression);
        invalidate();
    }

    public void setScrollLock(boolean scrollLock) {
        myTouchListener.setScrollIsLock(scrollLock);
    }
}
