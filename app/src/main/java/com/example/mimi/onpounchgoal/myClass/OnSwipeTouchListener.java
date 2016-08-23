package com.example.mimi.onpounchgoal.myClass;

import android.media.Image;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mimi.onpounchgoal.R;

/**
 * Created by mimi林明昊 on 2016/5/8.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    @SuppressWarnings("deprecation")
    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());
    private LinearLayout ll_goal;
    private ImageView iv_complete, iv_improve,iv_edit;

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        ll_goal = (LinearLayout) v;
        iv_complete = (ImageView) ll_goal.findViewById(R.id.iv_complete);
        iv_improve = (ImageView) ll_goal.findViewById(R.id.iv_imporve);
        iv_edit = (ImageView) ll_goal.findViewById(R.id.iv_edit);
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD ) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    // onTouch(e);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            ll_goal.setX(e2.getX());
            if (e1.getX() > e2.getX()) {
//                if (e1.getX()-e2.getX() > 40)
//                    onSwipeLeft();
            } else {
//                if (e2.getX()-e1.getX() > 40)
//                    onSwipeRight();
            }
//            Log.i("OnSwipeTouchListener", "e1.getX:" + e1.getX() + "e2.getX:" + e2.getX() + "disX:" + distanceX);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }


    public void onSwipeRight() {
        if(iv_improve.getVisibility() == View.VISIBLE){
            iv_complete.setVisibility(View.GONE);
            iv_improve.setVisibility(View.GONE);
        }
        else{
            iv_edit.setVisibility(View.VISIBLE);
        }
    }

    public void onSwipeLeft() {
        if(iv_edit.getVisibility() == View.GONE) {
            iv_complete.setVisibility(View.VISIBLE);
            iv_improve.setVisibility(View.VISIBLE);
        }
        else{
            iv_edit.setVisibility(View.GONE);
        }
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}