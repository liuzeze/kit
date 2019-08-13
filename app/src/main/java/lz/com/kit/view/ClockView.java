package lz.com.kit.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import lz.com.tools.util.LzTimeUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-08-13       创建class
 */
public class ClockView extends View {

    private String mCurrentTime;
    private String mCurrentData;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private Paint mTimePaint;
    private TextPaint mDataPaint;
    private Paint mLinePaint;

    private int mHourDegrees;
    private int mMinutDegrees;
    private int mSecondDegrees;
    private TextPaint mClockPaint;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCurrentTime = LzTimeUtils.getNowString(LzTimeUtils.DATE_FORMAT_HM);
        mCurrentData = LzTimeUtils.getNowString(LzTimeUtils.DATE_FORMAT_MD) + LzTimeUtils.getChineseWeek(LzTimeUtils.getNowMills());

        mSecondDegrees = -Integer.valueOf(LzTimeUtils.getNowString("ss")) * (6);
        mMinutDegrees = -Integer.valueOf(LzTimeUtils.getNowString("mm")) * (6);
        mHourDegrees = -Integer.valueOf(LzTimeUtils.getNowString("HH")) * (15);

        mTimePaint = new Paint();
        mTimePaint.setColor(Color.WHITE);
        mTimePaint.setTextSize(40);

        mDataPaint = new TextPaint();
        mDataPaint.setColor(Color.WHITE);
        mDataPaint.setTextSize(20);

        mClockPaint = new TextPaint();
        mClockPaint.setColor(Color.WHITE);
        mClockPaint.setTextSize(20);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);


        Disposable subscribe = Flowable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (mMinutDegrees == -354) {
                        getHourAnimator().start();

                    }
                    if (mSecondDegrees == -354) {
                        getMinutAnimator().start();
                    }
                    getSecondAnimator().start();

                });


    }

    @NotNull
    private ValueAnimator getHourAnimator() {
        ValueAnimator hourAnimator = ValueAnimator.ofInt(mHourDegrees, mHourDegrees - 15).setDuration(500);
        hourAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            mHourDegrees = animatedValue;
            if ((int) mHourDegrees == -360) {
                mHourDegrees = 0;
            }
            invalidate();
        });
        hourAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentTime = LzTimeUtils.getNowString(LzTimeUtils.DATE_FORMAT_HM);
            }
        });
        return hourAnimator;
    }

    @NotNull
    private ValueAnimator getMinutAnimator() {
        ValueAnimator minutAnimator = ValueAnimator.ofInt(mMinutDegrees, mMinutDegrees - 6).setDuration(500);
        minutAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            mMinutDegrees = animatedValue;
            if (mMinutDegrees == -360) {
                mMinutDegrees = 0;
            }
            invalidate();
        });
        minutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentTime = LzTimeUtils.getNowString(LzTimeUtils.DATE_FORMAT_HM);
            }
        });
        return minutAnimator;
    }

    @NotNull
    private ValueAnimator getSecondAnimator() {
        ValueAnimator secondAnimator = ValueAnimator.ofInt(mSecondDegrees, mSecondDegrees - 6).setDuration(500);
        secondAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            mSecondDegrees = animatedValue;
            invalidate();
            if (mSecondDegrees <= -360) {
                mSecondDegrees = 0;
            }
        });
        return secondAnimator;
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        //移动原点 到中心
        canvas.translate(mMeasuredWidth / 2, mMeasuredHeight / 2);
        //时间 时 分
        canvas.drawText(mCurrentTime, -mTimePaint.measureText(mCurrentTime) / 2, 0, mTimePaint);
        float measureText = mDataPaint.measureText(mCurrentData);
        canvas.drawText(mCurrentData, -measureText / 2, mDataPaint.getTextSize(), mDataPaint);
//        绘制线条
        canvas.drawLine(0, 0, mMeasuredWidth / 2, 0, mLinePaint);
        //绘制时分 秒  12 小时

        drawCircle(canvas, measureText, 24, 15, "点", 2, mHourDegrees);

        drawCircle(canvas, measureText, 60, 6, "分", 3, mMinutDegrees);

        drawCircle(canvas, measureText, 60, 6, "秒", 4, mSecondDegrees);

    }

    private void drawCircle(Canvas canvas, float measureText, int count, int step, String unit, int position, int degrees) {
        canvas.save();
        canvas.rotate(degrees);
        for (int i = 1; i <= count; i++) {
            canvas.save();
            int degrees1 = i * step;
            if (degrees == -degrees1) {
                mClockPaint.setColor(Color.parseColor("#ffffff"));
            } else {
                mClockPaint.setColor(Color.parseColor("#66ffffff"));
            }
            canvas.rotate(degrees1, 0, 0);
            if (i != count) {
                canvas.drawText(i + unit, measureText * position, 0, mClockPaint);
            }
            if (unit.equals("点") && i == count) {
                canvas.drawText("0"+ unit, measureText * position, 0, mClockPaint);

            }
            canvas.restore();
        }
        canvas.restore();
    }


}
