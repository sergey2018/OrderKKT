package com.sergey.root.orderkkt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GoodsClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mOnItemClickListener;
    GestureDetector mGestureDetector;

    public GoodsClickListener(Context context, OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(),e.getY());
        if(view != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)){
            mOnItemClickListener.onItemClick(view,rv.getChildLayoutPosition(view));
            return true;
        }
        return false;
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}
