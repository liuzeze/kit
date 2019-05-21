package lz.com.kit.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import lz.com.kit.R;
import lz.com.tools.util.LzDp2Px;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-17       创建class
 */
public class TimeSelectView extends View implements View.OnTouchListener {


    private float translate = 0;
    private Paint mTimePaint;
    private float offset = 150;

    private String[] timeArea = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
            "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",};
    private Paint mLinePaint;
    private Paint mBgPaint;
    private Rect mRect;
    private float mOffset;
    private float mTempset;
    private float limitOffset = 0;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private float mStartClickOffset;
    private float mEndClickOffset;
    private Bitmap mBitmap;

    public TimeSelectView(Context context) {
        this(context, null);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTimePaint = new Paint();
        mTimePaint.setColor(Color.BLACK);
        mTimePaint.setTextSize(LzDp2Px.dp2px(getContext(), 10));

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setTextSize(LzDp2Px.dp2px(getContext(), 1));

        mBgPaint = new Paint();
        //设置画笔宽度
        mBgPaint.setStrokeWidth(5);
        //设置画笔颜色
        mBgPaint.setColor(Color.RED);
        //设置画笔样式
        mBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRect = new Rect();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seekbar);

        setOnTouchListener(this);

    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewWidth", "---speSize = " + specSize + "");


        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) mTimePaint.measureText(timeArea[0]) + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
            default:
                break;
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (-mTimePaint.ascent() + mTimePaint.descent()) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值

                break;
            default:
                break;
        }
        return defaultHeight;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        limitOffset = timeArea.length * offset - mMeasuredWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();


        canvas.translate(mOffset + mTempset, 0);

        int textWidths = (int) mTimePaint.measureText(timeArea[0]);
        for (int i = 0; i < timeArea.length; i++) {
            canvas.drawText(timeArea[i], offset * i, textWidths, mTimePaint);
            canvas.drawLine(offset * i, 0, offset * i, getMeasuredHeight(), mLinePaint);


        }

        if (mStartClickOffset >= 0 && mEndClickOffset > 0) {

            mRect.top = mMeasuredHeight / 2;
            mRect.bottom = mMeasuredHeight;
            mRect.left = (int) mStartClickOffset;
            mRect.right = (int) mEndClickOffset;
            canvas.drawRect(mRect, mBgPaint);
            canvas.drawBitmap(mBitmap, mRect.right - mBitmap.getWidth() / 2, mMeasuredHeight * 3 / 4 - mBitmap.getHeight() / 2, null);
        }


        canvas.restore();
    }


    private float startX = 0;
    private float mTempStartOffset = 0;
    private boolean isScrollArea;
    private boolean isClickArea;

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                if (event.getY() < getMeasuredHeight() / 2) {
                    isScrollArea = true;
                } else {
                    isScrollArea = false;
                }
                float x = event.getX();
                if (x > mRect.right - mBitmap.getWidth() / 2 && x < mRect.right + mBitmap.getWidth() / 2) {
                    isClickArea = true;
                } else {
                    isClickArea = false;
                }
                if (mTempStartOffset == 0) {
                    mTempStartOffset = mStartClickOffset;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isScrollArea) {
                    float stopX = event.getX();
                    mTempset = (stopX - startX);
                    if (mOffset + mTempset <= -limitOffset && mTempset <= 0) {
                        mOffset = -limitOffset;
                        mTempset = 0;
                    } else if (mOffset + mTempset >= 0 && mTempset >= 0) {
                        mOffset = 0;
                        mTempset = 0;
                    }

                } else {
                    if (isClickArea) {

                        if (event.getX() - startX > 0) {
                            float startClickOffset = (int) ((Math.abs(mOffset + startX)) / offset) * offset - offset;
                            if (mStartClickOffset == 0) {
                                mStartClickOffset = startClickOffset;
                            }

                            mEndClickOffset = (Math.abs(mOffset + event.getX()));
                        } else {

                            float startClickOffset = (Math.abs(mOffset - event.getX()));
                            if (startClickOffset - mTempStartOffset > 0) {
                                mEndClickOffset = startClickOffset;
                            } else if (startClickOffset - mTempStartOffset == 0) {
                                mEndClickOffset = mTempStartOffset;
                            } else {
                                mStartClickOffset = startClickOffset;
                            }
                        }
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (isScrollArea) {
                    mOffset += mTempset;
                    mTempset = 0;
                } else {
                    if (isClickArea) {
                        if (event.getX() - startX > 0) {
                            float startClickOffset = (int) (Math.abs(mOffset + startX) / offset) * offset - offset;
                            if (startClickOffset == 0) {
                                mStartClickOffset = startClickOffset;
                            }
                            mEndClickOffset = (int) (Math.abs(mOffset + event.getX()) / offset) * offset + offset;
                        } else {
                            float startClickOffset = (Math.abs(mOffset - event.getX()));
                            if (startClickOffset - mTempStartOffset >0) {
                                mEndClickOffset = (int) (startClickOffset / offset) * offset + offset;
                            } else if (startClickOffset - mTempStartOffset == 0) {
                                mEndClickOffset = mStartClickOffset;
                            } else if (mEndClickOffset > startClickOffset) {
                                mStartClickOffset = (int) (startClickOffset / offset) * offset;
                            }

                        }
                    } else {
                        if (Math.abs(event.getX() - startX) < offset) {
                            if (event.getX() - startX > 0) {
                                float startClickOffset = (int) (Math.abs(mOffset + event.getX()) / offset) * offset;
                                float endClickOffset = (int) (Math.abs(mOffset + event.getX()) / offset) * offset + offset;
                                if (mEndClickOffset - mStartClickOffset <= offset) {


                                    if (startClickOffset == mStartClickOffset) {
                                        mStartClickOffset = 0;
                                    } else {
                                        mStartClickOffset = startClickOffset;
                                    }
                                    if (endClickOffset == mEndClickOffset) {
                                        mEndClickOffset = 0;
                                    } else {
                                        mEndClickOffset = endClickOffset;
                                    }
                                } else {
                                    mStartClickOffset = startClickOffset;
                                    mEndClickOffset = endClickOffset;
                                }
                            } else {
                                float startClickOffset = (int) (Math.abs(mOffset - event.getX()) / offset) * offset;
                                float endClickOffset = (int) (Math.abs(mOffset + event.getX()) / offset) * offset + offset;
                                if (mEndClickOffset - mStartClickOffset <= offset) {
                                    if (startClickOffset == mStartClickOffset) {
                                        mStartClickOffset = 0;
                                    } else {
                                        mStartClickOffset = startClickOffset;
                                    }
                                    if (endClickOffset == mEndClickOffset) {
                                        mEndClickOffset = 0;
                                    } else {
                                        mEndClickOffset = endClickOffset;
                                    }
                                } else {
                                    mStartClickOffset = startClickOffset;
                                    mEndClickOffset = endClickOffset;
                                }
                            }

                        }
                    }
                }
                startX = 0;
                isScrollArea = false;
                mTempStartOffset=0;
                break;
            default:
                break;
        }
        invalidate();

        return true;
    }
}
