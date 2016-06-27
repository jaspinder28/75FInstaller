package com.x75f.installer.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;

/**
 * Created by JASPINDER on 6/22/2016.
 */
public class StopViewPagerSwipe extends ViewPager {

    public StopViewPagerSwipe(Context context) {
        super(context);
    }

    public StopViewPagerSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
