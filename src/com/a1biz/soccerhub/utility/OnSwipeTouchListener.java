package com.a1biz.soccerhub.utility;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {
	View view;
    @SuppressWarnings("deprecation")
	private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());
    
    public boolean onTouch(final View view1, final MotionEvent motionEvent) 
    {
    	view = view1;
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = true;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(view);
                        } else {
                            onSwipeLeft(view);
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom(view);
                        } else {
                            onSwipeTop(view);
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public boolean onSwipeRight(View view) {
    	return true;
    }

    public boolean onSwipeLeft(View view) {
    	return true;
    }

    public boolean onSwipeTop(View view) {
    	return true;
    }

    public boolean onSwipeBottom(View view) {
    	return true;
    }
    
}