package lz.com.kit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-06-10       创建class
 */
public class HorizontalScrollViews extends HorizontalScrollView {

    public HorizontalScrollViews(Context context) {
        this(context,null);
    }

    public HorizontalScrollViews(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public HorizontalScrollViews(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,-1);
    }

    public HorizontalScrollViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private View parentView;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //注意 getParent() 是 ViewParent 但不一定是 View
        if (parentView == null
                && getParent() != null
                && getParent() instanceof View) {
            parentView = (View) getParent();
        }

        if (parentView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    parentView.onTouchEvent(ev);  //使父容器也能响应本次按下事件
                    break;

                case MotionEvent.ACTION_MOVE: //当触发滑动时，将父容器的按下响应失效
                    //修改动作为ACTION_CANCEL
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    parentView.onTouchEvent(ev);
                    //父容器响应后恢复事件原动作
                    ev.setAction(MotionEvent.ACTION_MOVE);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    parentView.onTouchEvent(ev);  //将松手事件，先行传递给父容器响应
                    break;
            }
        }
        return super.onTouchEvent(ev);  //无论如何 本身都响应事件
    }


}
