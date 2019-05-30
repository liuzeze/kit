package lz.com.tools.recycleview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import lz.com.tools.refresh.BaseRefrashHeader;
import lz.com.tools.refresh.RefreshState;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-12-16        越界回弹
 */
public class ScrollHelperView extends LinearLayout {

    //移动百分比
    private float movePercent = 0.5f;

    //松开手指后, 界面回到正常位置需要的动画时间
    private static final int animTime = 300;

    //手指按下时的Y值, 用于在移动时计算移动距离
    private float startY;


    //手指按下时记录是否可以继续下拉
    private boolean mCanPullDown = false;
    //手指按下时记录是否可以继续上拉
    private boolean mCanPullUp = false;

    //在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;

    private boolean isEnableRefrash = false;

    //头部刷新控件
    private BaseRefrashHeader mRefreshHeader;
    //头部控件param
    private ViewGroup.LayoutParams mLayoutParams;
    //刷新控件高低
    private int mRefreshViewHeight;

    private int mRefreshState = RefreshState.None;
    private View mChildAt;

    public ScrollHelperView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public ScrollHelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public ScrollHelperView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new IllegalStateException("子控件 最多两个  head和content");
        }
        if (getChildCount() > 0) {
            if (getChildCount() == 1) {
                if (mChildAt == null) {
                    mChildAt = getChildAt(0);
                }
            }
            if (getChildCount() > 1) {
                if (mChildAt == null) {
                    mChildAt = getChildAt(1);
                }
                if (mRefreshHeader == null) {
                    View childAt = getChildAt(0);
                    if (childAt != null) {
                        if (childAt instanceof BaseRefrashHeader) {
                            setRefreshView((BaseRefrashHeader) childAt);
                        }
                    }
                }
            }

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //判断是否可以下拉 上拉
                mCanPullDown = !mChildAt.canScrollVertically(-1);
                mCanPullUp = !mChildAt.canScrollVertically(1);
                //记录按下时的Y值
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                //如果没有移动布局， 则跳过执行
                if (isMoved) {

                    if (isEnableRefrash) {
                        if (mRefreshHeader != null) {
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
                            valueAnimator.setDuration(animTime);
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
                        }
                    } else {
                        final float translationY = mChildAt.getTranslationY();
                        // 开启动画
                        mChildAt.animate().translationY(0)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if ((translationY < 0)) {
                                            if (mOnScrollOffsetListener != null) {
                                                mOnScrollOffsetListener.onUpScroll((int) translationY);
                                            }
                                        }
                                    }
                                }).setDuration(animTime).start();


                    }


                    //将标志位设回false
                    mCanPullDown = false;
                    mCanPullUp = false;
                    isMoved = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                //在移动的过程中， 也没有滚动到可以下拉的程度
                if (!mCanPullDown && !mCanPullUp) {
                    startY = ev.getY();
                    mCanPullDown = !mChildAt.canScrollVertically(-1);
                    mCanPullUp = !mChildAt.canScrollVertically(1);
                    break;
                }
                //计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);

                //是否应该移动布局
                boolean shouldMove =
                        //可以下拉， 并且手指向下移动
                        (mCanPullDown && deltaY > 0)
                                //可以上拉， 并且手指向上移动
                                || (mCanPullUp && deltaY < 0)
                                //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                                || (mCanPullUp && mCanPullDown);
                if (shouldMove) {

                    //计算偏移量
                    int offset = (int) (deltaY * movePercent);
                    //随着手指的移动而移动布局
                    if (isEnableRefrash) {
                        if (mRefreshHeader != null) {
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
                        mChildAt.setTranslationY(offset);
                        if ((mCanPullUp && deltaY < 0)) {
                            if (mOnScrollOffsetListener != null) {
                                mOnScrollOffsetListener.onUpMoveScroll(offset);
                            }
                        }
                    }

                    //记录移动了布局
                    isMoved = true;

                }
                break;
            default:
                break;
        }

        return !isMoved | super.dispatchTouchEvent(ev);
    }


    public void setRefreshView(BaseRefrashHeader dfRefreshHeader) {
        isEnableRefrash = dfRefreshHeader != null;
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
        valueAnimator.setDuration(animTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mLayoutParams.height = animatedValue;
                mRefreshHeader.setLayoutParams(mLayoutParams);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRefreshHeader.getChildAt(0).getLayoutParams();
//                layoutParams.setMargins(0, animatedValue - mRefreshViewHeight, 0, 0);
                layoutParams.setMargins(0, 0, 0, 0);
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
        valueAnimator.setDuration(animTime);
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

    public ScrollHelperView setMovePercent(float movePercent) {
        this.movePercent = movePercent;
        return this;
    }


    //上下回弹监听
    private OnScrollOffsetListener mOnScrollOffsetListener;

    public interface OnScrollOffsetListener {
        void onUpScroll(int offset);

        void onUpMoveScroll(int offset);
    }

    public void setOnScrollOffsetListener(OnScrollOffsetListener onUpScrollListener) {
        mOnScrollOffsetListener = onUpScrollListener;
    }


    //刷新监听
    private OnRefreshListener mOnRefreshListener;

    public ScrollHelperView setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        return this;
    }

    public interface OnRefreshListener {
        void onRefreshing(int offset);
    }
}
