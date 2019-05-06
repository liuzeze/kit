package lz.com.tools.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import lz.com.tools.refresh.BaseRefrashHeader;
import lz.com.tools.refresh.RefreshState;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public class ReboundScrollView extends ScrollView {

    private static final String TAG = "ReboundScrollView";

    private   float MOVE_FACTOR = 0.3f;
    private static final int ANIM_TIME = 300;

    public interface MyScrollListener {
        void onMyScrollEvent(int action, float y);
    }

    private MyScrollListener listener;

    private View childView;
    private boolean canPullUp;
    private boolean canPullDown;
    private boolean havaMoved;

    private int changeY;

    private Rect originalRect = new Rect();

    private float startY;


    private boolean isEnableRefrash = false;

    //头部刷新控件
    private BaseRefrashHeader mRefreshHeader;
    //头部控件param
    private ViewGroup.LayoutParams mLayoutParams;
    //刷新控件高低
    private int mRefreshViewHeight;

    private int mRefreshState = RefreshState.None;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");

    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        if (listener != null) {
            listener.onMyScrollEvent(-1, scrollY);
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onlayout");
        if (getChildCount() > 0)
            childView = getChildAt(0);
        if (childView == null)
            return;

        originalRect.set(childView.getLeft(), childView.getTop(),
                childView.getRight(), childView.getBottom());
    }


    public ReboundScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReboundScrollView(Context context) {
        super(context);
    }


    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (childView == null) {
            return super.onTouchEvent(ev);
        }

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();

                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!havaMoved)
                    break;

                if (isEnableRefrash) {

                    final int height = mLayoutParams.height;
                    if (height < mRefreshViewHeight) {
                        closeRefresh();
                    } else {
                        if (mRefreshState != RefreshState.RefreshReleased) {
                            mRefreshHeader.onStateChanged(mRefreshHeader, mRefreshState, RefreshState.RefreshReleased);
                            mRefreshState = RefreshState.RefreshReleased;
                        }
                    }
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(height, height > mRefreshViewHeight ? mRefreshViewHeight : 0);
                    valueAnimator.setDuration(ANIM_TIME);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int animatedValue = (int) animation.getAnimatedValue();
                            mLayoutParams.height = animatedValue;
                            mRefreshHeader.setLayoutParams(mLayoutParams);
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
                            layoutParams.setMargins(0, animatedValue - mRefreshViewHeight, 0, 0);
                            mRefreshHeader.getChildAt(0).setLayoutParams(layoutParams);

                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (mOnRefreshListener != null && height > mRefreshViewHeight) {
                                mOnRefreshListener.onRefreshing(mRefreshViewHeight);
                            }
                        }
                    });
                    valueAnimator.start();
                } else {
                    TranslateAnimation anim = new TranslateAnimation(0, 0,
                            childView.getTop(), originalRect.top);
                    anim.setDuration(ANIM_TIME);

                    childView.startAnimation(anim);

                    resetViewLayout();
                    if (listener != null) {
                        listener.onMyScrollEvent(action, changeY);
                    }
                }

                // 将标志位设回false
                canPullDown = false;
                canPullUp = false;
                havaMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:

                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();

                    break;
                }

                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);
                changeY = deltaY;
                boolean shouldMove = (canPullDown && deltaY > 0)
                        || (canPullUp && deltaY < 0) || (canPullUp && canPullDown);

                if (shouldMove) {
                    int offset = (int) (deltaY * MOVE_FACTOR);
                    if (isEnableRefrash) {
                        if ((canPullDown && deltaY > 0)) {
                            //随着手指的移动而移动布局
                            mLayoutParams.height = offset;
                            mRefreshHeader.setLayoutParams(mLayoutParams);
                            if (mRefreshState != RefreshState.PullDownToRefresh) {
                                mRefreshHeader.onStateChanged(mRefreshHeader, mRefreshState, RefreshState.PullDownToRefresh);
                                mRefreshState = RefreshState.PullDownToRefresh;
                            }
                            mRefreshHeader.onMoving(true, (float) offset / (float) mRefreshViewHeight, offset, mRefreshViewHeight, offset);
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
                            layoutParams.setMargins(0, offset - mRefreshViewHeight, 0, 0);
                            mRefreshHeader.getChildAt(0).setLayoutParams(layoutParams);
                        }
                    } else {
                        childView.layout(originalRect.left, originalRect.top + offset,
                                originalRect.right, originalRect.bottom + offset);

                    }
                    havaMoved = true;
                }

                break;
            default:
                break;
        }

        return havaMoved || super.onTouchEvent(ev);
    }

    public void resetViewLayout() {
        childView.layout(originalRect.left, originalRect.top,
                originalRect.right, originalRect.bottom);
    }

    public void setListener(MyScrollListener listener) {
        this.listener = listener;
    }

    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0
                || childView.getHeight() < getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return childView.getHeight() <= getHeight() + getScrollY();
    }

    public ReboundScrollView setMOVE_FACTOR(float MOVE_FACTOR) {
        this.MOVE_FACTOR = MOVE_FACTOR;
        return this;
    }

    public ReboundScrollView setEnableRefrash(boolean enableRefrash) {
        isEnableRefrash = enableRefrash;
        return this;
    }

    public void setRefreshView(BaseRefrashHeader dfRefreshHeader) {
        if (dfRefreshHeader == null) {
            return;
        }
        this.mRefreshHeader = dfRefreshHeader;
        mLayoutParams = dfRefreshHeader.getLayoutParams();
        if (mLayoutParams == null) {
            throw new NullPointerException("必须先添加到父布局");
        }
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mRefreshHeader.measure(w, h);
        mRefreshViewHeight = mRefreshHeader.getMeasuredHeight();

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
        layoutParams.setMargins(0, -mRefreshViewHeight, 0, 0);
        mRefreshHeader.getChildAt(0).setLayoutParams(layoutParams);


        mLayoutParams.height = 0;
        dfRefreshHeader.setLayoutParams(mLayoutParams);
    }

    public void closeRefresh() {
        if (mRefreshHeader == null) {
            return;
        }
        mRefreshHeader.onStateChanged(mRefreshHeader, mRefreshState, RefreshState.RefreshFinish);
        mRefreshState = RefreshState.RefreshFinish;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(mLayoutParams.height, 0);
        valueAnimator.setDuration(ANIM_TIME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mLayoutParams.height = animatedValue;
                mRefreshHeader.setLayoutParams(mLayoutParams);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
//                layoutParams.setMargins(0, animatedValue - mRefreshViewHeight, 0, 0);
                mRefreshHeader.getChildAt(0).setLayoutParams(layoutParams);

            }
        });
        valueAnimator.start();


    }

    public void openRefresh() {
        if (mRefreshHeader == null) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, mRefreshViewHeight);
        valueAnimator.setDuration(ANIM_TIME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mLayoutParams.height = animatedValue;
                mRefreshHeader.setLayoutParams(mLayoutParams);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
                layoutParams.setMargins(0, animatedValue - mRefreshViewHeight, 0, 0);
                mRefreshHeader.getChildAt(0).setLayoutParams(layoutParams);

                if (mRefreshState != RefreshState.PullDownToRefresh) {
                    mRefreshHeader.onStateChanged(mRefreshHeader, mRefreshState, RefreshState.PullDownToRefresh);
                    mRefreshState = RefreshState.PullDownToRefresh;
                }

            }
        });
        valueAnimator.start();


    }

    private OnRefreshListener mOnRefreshListener;

    public ReboundScrollView setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        return this;
    }

    public interface OnRefreshListener {
        void onRefreshing(int offset);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollAlphaChangeListener == null) {
            return;
        }
        scrollAlphaChangeListener.onSwipeFoeword(t - oldl > 0);
        int height = scrollAlphaChangeListener.setLimitHeight();
        if (height == -1) {
            return;
        }
        if (t <= 0) {
            scrollAlphaChangeListener.onAlphaChange(0, t);
        } else if (t <= height && t > 0) {
            /** 255/height = x/255 */
            float scale = (float) t / height;
            float alpha = (255 * scale);
            scrollAlphaChangeListener.onAlphaChange((int) alpha, t);
        } else {
            scrollAlphaChangeListener.onAlphaChange(255, t);
        }
        //t>oldt  上滑
    }

    private ScrollAlphaChangeListener scrollAlphaChangeListener;

    public void setScrollAlphaChangeListener(
            ScrollAlphaChangeListener scrollAlphaChangeListener) {
        this.scrollAlphaChangeListener = scrollAlphaChangeListener;
    }

    public static class ScrollAlphaChangeListener {
        public void onAlphaChange(int alpha, int scrollDyCounter) {
        }

        public int setLimitHeight() {
            return -1;
        }

        public void onSwipeFoeword(boolean isUp) {
        }

    }
}