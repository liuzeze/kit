package lz.com.kit.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import lz.com.tools.util.LzDisplayUtils;
import lz.com.tools.util.LzDp2Px;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-07-05       创建class
 */
public class MenuView extends LinearLayout {

    private Paint mPaint;
    private int mMeasuredHeight = LzDp2Px.dp2px(getContext(), 45);
    private int mScreenWidth;
    private int mRadius;
    private int mStartRadius;
    private int mEndRadius;
    private int mCenterRadius;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mResetAnimator;
    private Paint mBgPaint;

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setPathEffect(new CornerPathEffect(5));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.GRAY);
        mPaint.setShadowLayer(5, 0, -1, Color.BLACK);
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mScreenWidth = LzDisplayUtils.getScreenWidth(getContext());
        mRadius = mScreenWidth / 5;
        mStartRadius = (mScreenWidth - mRadius) / 2;
        mEndRadius = mStartRadius + mRadius;
        mCenterRadius = mScreenWidth / 2;
        setClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (MeasureSpec.getSize(heightMeasureSpec) + mDy));
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 1 + mDy);
        path.lineTo(mStartRadius, 1 + mDy);
        path.quadTo(mCenterRadius, 1 - mDy, mEndRadius, 1 + mDy);
        path.lineTo(mScreenWidth, 1 + mDy);


        canvas.drawPath(path, mPaint);


        path.lineTo(mScreenWidth, mMeasuredHeight);
        path.lineTo(0,mMeasuredHeight);
        path.close();
        canvas.drawPath(path, mBgPaint);
        super.onDraw(canvas);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            //   setGravity();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.setMargins(0, (int) mDy / 2 - 5, 0, 0);
        }
        super.onLayout(changed, l, t, r, b);
    }

    private float mDy;

    public void startAni() {

        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, mRadius / 3);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setLayout(animation);
                }
            });
            mValueAnimator.setDuration(300);
        }
        if (mDy == 0) {
            mValueAnimator.start();


            View child = getChildAt(getChildCount() / 2);
            if (child != null) {
                child.animate().translationY(-mRadius / 4).scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
            }
        }
    }

    private void setLayout(ValueAnimator animation) {
        mDy = (float) animation.getAnimatedValue();
        requestLayout();
        invalidate();
    }

    public void reset() {
        if (mResetAnimator == null) {

            mResetAnimator = ValueAnimator.ofFloat(mDy, 0);
            mResetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setLayout(animation);
                }
            });
            mResetAnimator.setDuration(300);
        }

        if (mDy == mRadius / 3) {
            mResetAnimator.start();
            View child = getChildAt(getChildCount() / 2);
            if (child != null) {
                child.animate().translationY(0).scaleX(1f).scaleY(1f).setDuration(300).start();
            }
        }
    }

    public boolean isOpen() {
        return mDy != 0;
    }
}
