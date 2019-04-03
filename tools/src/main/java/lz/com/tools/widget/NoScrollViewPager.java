package lz.com.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-10       设置不滑动对的viewpager
 */
public class NoScrollViewPager extends ViewPager {

    private boolean mNoScroll;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll) {
        mNoScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mNoScroll) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mNoScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }
}
