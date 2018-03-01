package com.example.monishakram.snapandplot;

import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;

/**
 * Created by Monish Akram on 12/9/2017.
 * Handler for all actions Related to Touch.
 */

class MyTouchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    private GraphView context;
    private Graph graph;
    private float prev;
    private boolean scrollIsLock = false;

    MyTouchListener(GraphView context, Graph graph){
        this.context = context;
        this.graph = graph;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        graph.scaling((detector.getCurrentSpan() > prev)?1:-1);
        context.invalidate();
        graph.scaling((detector.getCurrentSpan() > prev)?1:-1);
        prev = detector.getCurrentSpan();
        return super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        prev = detector.getCurrentSpan();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        super.onScaleEnd(detector);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(scrollIsLock)
            graph.drawPoint(e2.getX());
        else
            graph.scrolling(distanceX, distanceY);
        context.invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    void setScrollIsLock(boolean status){
        scrollIsLock = status;
    }
}
