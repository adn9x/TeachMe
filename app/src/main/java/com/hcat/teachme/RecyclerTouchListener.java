package com.hcat.teachme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Admin on 3/10/2017.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView mList;
    private ClickListener mClickListener;
    private GestureDetector mGestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        mList = recyclerView;
        mClickListener = clickListener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View touchedView= mList.findChildViewUnder(e.getX(), e.getY());
                if(touchedView!= null && mClickListener != null){
                    mClickListener.onLongClick(touchedView, mList.getChildAdapterPosition(touchedView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View touchedView= mList.findChildViewUnder(e.getX(), e.getY());
        if(touchedView!= null && mClickListener != null){
            mClickListener.onClick(touchedView, mList.getChildAdapterPosition(touchedView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
