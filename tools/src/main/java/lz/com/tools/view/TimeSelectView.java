package lz.com.tools.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import lz.com.tools.R;
import lz.com.tools.util.LzDp2Px;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-17       创建class
 */
public class TimeSelectView extends View implements View.OnTouchListener {


    //文字画笔
    private Paint mTimePaint;
    //线框画笔
    private Paint mLinePaint;
    //选中背景画笔
    private Paint mBgPaint;
    //自己绘制 padding   画笔
    private Paint mPaddingPaint;


    //每个桃木区域宽度
    private float areaWidth = 150;

    //列表数据
    private String[] mDataArea = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};

    //选中的绘制区域
    private Rect mRect;
    //画布的偏移量
    private float mOffset;
    //手指左右滑动时额度偏移记录
    private float mTempset;
    //最大可以偏移的距离 做越界绘制的限制
    private float limitOffset = 0;
    //当前控件的宽高
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    //选中区域的开始和结束位置
    private float mStartClickOffset;
    private float mEndClickOffset;
    //拖拽图片
    private Bitmap mBitmap;
    //做惯性滑动时的加速度计算辅助类
    private VelocityTracker mVelocityTracker;
    //惯性滑动动画
    private ValueAnimator mAnimatorRunning;


    //绘制文字大小
    private int mTextsize = LzDp2Px.dp2px(getContext(), 10);
    //选中绘制区域颜色
    private @ColorInt
    int mBgColor = Color.parseColor("#ff8522");
    //拖拽图片资源
    private @DrawableRes
    int mBitmapRes = R.mipmap.seekbar;

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
        //时间画笔
        mTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimePaint.setColor(Color.BLACK);
        mTimePaint.setTextSize(mTextsize);

        //线条画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setTextSize(LzDp2Px.dp2px(getContext(), 1));

        //选中背景画笔
        mBgPaint = new Paint();
        //设置画笔颜色
        mBgPaint.setColor(mBgColor);
        //设置画笔样式
        mBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaddingPaint = new Paint();
        mPaddingPaint.setColor(Color.WHITE);
        //选中区域
        mRect = new Rect();

        //图片
        mBitmap = BitmapFactory.decodeResource(getResources(), mBitmapRes);

        setOnTouchListener(this);

        setBackgroundColor(Color.WHITE);

    }

    /**
     * //宽度测量
     *
     * @param defaultWidth
     * @param measureSpec
     * @return
     */
    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);


        int defaultWidth1 = (int) (mDataArea.length * areaWidth + getPaddingLeft() + getPaddingRight());
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = defaultWidth1 > getMeasuredWidth() ? getMeasuredWidth() : defaultWidth1;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = defaultWidth1 > specSize ? specSize : defaultWidth1;
//                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
            default:
                break;
        }
        return defaultWidth;
    }

    /**
     * //高度测量
     *
     * @param defaultHeight
     * @param measureSpec
     * @return
     */
    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (-mTimePaint.ascent() + mTimePaint.descent()) * 6 + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(mTextsize * 6, specSize);
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

        //控件宽高 和最大偏移数值赋值
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        limitOffset = mDataArea.length * areaWidth - mMeasuredWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //z左
        /*canvas.drawLine(getPaddingLeft(),
                getPaddingTop(),
                getPaddingLeft(),
                mMeasuredHeight - getPaddingBottom(),
                mLinePaint);*/
       /* //右边
        canvas.drawLine(mMeasuredWidth - getPaddingRight(),
                getPaddingTop(),
                mMeasuredWidth - getPaddingRight() - 2,
                mMeasuredHeight - getPaddingBottom(),
                mLinePaint);*/

        //绘制线条
        //上
        canvas.drawLine(getPaddingLeft(),
                getPaddingTop(),
                mMeasuredWidth - getPaddingRight(),
                getPaddingTop(),
                mLinePaint);
        //中
        canvas.drawLine(getPaddingLeft(),
                getPaddingTop() + (mMeasuredHeight - getPaddingTop() - getPaddingBottom()) / 2,
                mMeasuredWidth - getPaddingRight(),
                getPaddingTop() + (mMeasuredHeight - getPaddingTop() - getPaddingBottom()) / 2,
                mLinePaint);
        //下
        canvas.drawLine(getPaddingLeft(),
                mMeasuredHeight - 1 - getPaddingBottom(),
                mMeasuredWidth - getPaddingRight(),
                mMeasuredHeight - 1 - getPaddingBottom(),
                mLinePaint);

        canvas.save();
        //平移画布实现滑动效果
        canvas.translate(mOffset + mTempset, 0);
        //绘制竖直线条和时间字体
        for (int i = 0; i < mDataArea.length; i++) {
            if (i != 0) {
                canvas.drawLine(areaWidth * i + getPaddingLeft(),
                        getPaddingTop(),
                        areaWidth * i + getPaddingLeft(),
                        mMeasuredHeight - getPaddingBottom(),
                        mLinePaint);
            }
            canvas.drawText(mDataArea[i],
                    areaWidth * i + 10 + getPaddingLeft(),
                    getPaddingTop() + (mMeasuredHeight - getPaddingTop() - getPaddingBottom()) / 4 + mTextsize / 2,
                    mTimePaint);
        }


        //绘制选中区域和图片
        if (mStartClickOffset >= 0 && mEndClickOffset > 0) {

            //确定绘制的区域范围
            mRect.top = getPaddingTop() + (mMeasuredHeight - getPaddingTop() - getPaddingBottom()) / 2;
            mRect.bottom = mMeasuredHeight - getPaddingBottom();
            mRect.left = (int) mStartClickOffset + getPaddingLeft();
            mRect.right = (int) mEndClickOffset + getPaddingLeft();
            canvas.drawRect(mRect, mBgPaint);
            //图片绘制方向 左右
            if (isDrawLeft) {
                canvas.drawBitmap(mBitmap,
                        mRect.left - mBitmap.getWidth() / 2,
                        (mRect.top + (mRect.bottom - mRect.top) / 2) - mBitmap.getHeight() / 2,
                        null);
            } else {

                canvas.drawBitmap(mBitmap,
                        mRect.right - mBitmap.getWidth() / 2,
                        (mRect.top + (mRect.bottom - mRect.top) / 2) - mBitmap.getHeight() / 2,
                        null);
            }

        }

        canvas.restore();


        //货值padding
        Rect rect = new Rect();
        //paddingleft
        if (getPaddingLeft() > 0) {
            if (getBackground() != null) {
                ColorDrawable colordDrawable = (ColorDrawable) getBackground();
                int color = colordDrawable.getColor();
                mPaddingPaint.setColor(color);
            }

            rect.top = getPaddingTop();
            rect.bottom = mMeasuredHeight - getPaddingBottom();
            rect.left = 0;
            rect.right = getPaddingLeft();
            canvas.drawRect(rect, mPaddingPaint);
        }

        //paddright
        if (getPaddingRight() > 0) {
            if (getBackground() != null) {
                ColorDrawable colordDrawable = (ColorDrawable) getBackground();
                int color = colordDrawable.getColor();
                mPaddingPaint.setColor(color);
            }
            rect.top = getPaddingTop();
            rect.bottom = mMeasuredHeight - getPaddingBottom();
            rect.left = mMeasuredWidth - getPaddingRight() + mBitmap.getWidth() / 2;
            rect.right = mMeasuredWidth;
            canvas.drawRect(rect, mPaddingPaint);
        }
    }


    //dowm时间x位置
    private float startX = 0;
    //临时记录已选择的起点位置
    private float mTempStartOffset = 0;
    //点击的是否是左右滑动区域
    private boolean isScrollArea;
    //是否点击在图片上
    private boolean isClickImg;
    //点击的是否是选中的并且没惦记在图片上
    private boolean isClickContent;
    //推按画在左侧还是右侧
    private boolean isDrawLeft;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //加速度计算
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();//手指抬起之后的速度变化
        }
        mVelocityTracker.computeCurrentVelocity(200);
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                //是否点击在控件上部  区分左右滑动 和点击选中区域
                if (event.getY() < getPaddingTop() + (mMeasuredHeight - getPaddingTop() - getPaddingBottom()) / 2) {
                    isScrollArea = true;
                } else {
                    isScrollArea = false;
                }
                //点击位置在画布上的绝对位置
                float currentPos = Math.abs(mOffset) + event.getX();
                //点击的位置是否是图片
                if (currentPos > mRect.right - mBitmap.getWidth() / 2 && currentPos < mRect.right + mBitmap.getWidth() / 2) {
                    isClickImg = true;
                } else {
                    isClickImg = false;
                }
                //是否点击在选中区域 并且不在图片上
                if (currentPos > mRect.left && currentPos < mRect.right && mRect.width() == areaWidth) {
                    isClickContent = true;
                } else {
                    isClickContent = false;
                }
                //临时记录上次选中的起始位置
                if (mTempStartOffset == 0) {
                    mTempStartOffset = mStartClickOffset;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //滚动整个控件
                if (isScrollArea) {
                    float stopX = event.getX();
                    mTempset = (stopX - startX);
                    //防止内容划出控件
                    float offset = -(limitOffset + getPaddingLeft() + getPaddingRight());
                    //右边界判断
                    if (mOffset + mTempset <= offset && mTempset <= 0) {
                        mOffset = offset;
                        mTempset = 0;
                    } else if (mOffset + mTempset >= 0 && mTempset >= 0) {
                        //左边界判断
                        mOffset = 0;
                        mTempset = 0;
                    }

                } else {
                    //点击图片
                    if (isClickImg) {
                        //右滑
                        float endClickOffset = Math.abs(mOffset) + Math.abs(event.getX());

                        //
                        if (endClickOffset > mTempStartOffset) {
                            //在开始右侧滑动
                            mEndClickOffset = endClickOffset;
                            mStartClickOffset = mTempStartOffset;

                            //右侧边界
                            if (endClickOffset >= mDataArea.length * areaWidth) {
                                mEndClickOffset = mDataArea.length * areaWidth;
                            }
                            isDrawLeft = false;
                        } else {
                            //开始左侧滑动
                            //终点是初始化的起点
                            mEndClickOffset = mTempStartOffset;
                            //起点跟随手指
                            mStartClickOffset = endClickOffset;

                            if (mStartClickOffset <= getPaddingLeft()) {
                                mStartClickOffset = 0;
                            }
                            isDrawLeft = true;
                        }

                    } else {
                        //点击不是图片的地方
                        //左滑
                        if (isClickContent) {
                            mStartClickOffset = mTempStartOffset + (event.getX() - startX);
                            //向前限制
                            if (mStartClickOffset <= 0) {
                                mStartClickOffset = 0;
                            }
                            //向后限制
                            //防止内容划出控件
                            if (mStartClickOffset > mDataArea.length * areaWidth - areaWidth) {
                                mStartClickOffset = mDataArea.length * areaWidth - areaWidth;
                            }
                            //开始位置不变终点改变
                            mEndClickOffset = mStartClickOffset + areaWidth;
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
                    if (isClickImg) {
                        //右滑
                        int currentPos2 = (int) (Math.abs(mOffset) + Math.abs(event.getX()));
                        //当前位置与起点比较
                        //在起点右侧
                        if (currentPos2 > mTempStartOffset) {
                            int v1 = (int) ((int) (currentPos2 / areaWidth) * areaWidth);
                            mEndClickOffset = currentPos2 % areaWidth > (areaWidth / 2) ? v1 + areaWidth : v1;
                            mStartClickOffset = mTempStartOffset;

                            if (mEndClickOffset - mStartClickOffset < areaWidth) {
                                mEndClickOffset = mStartClickOffset + areaWidth;
                            }
                            //右侧边界
                            if (mEndClickOffset > mDataArea.length * areaWidth) {
                                mEndClickOffset = mDataArea.length * areaWidth;
                            }
                        } else {
                            //在起点左侧
                            //终点是初始化的起点
                            mEndClickOffset = mTempStartOffset;
                            //起点跟随手指
                            int v1 = (int) ((int) (currentPos2 / areaWidth) * areaWidth);
                            mStartClickOffset = currentPos2 % areaWidth > (areaWidth / 2) ? v1 + areaWidth : v1;
                            //滑动小于一个偏移量
                            if (mEndClickOffset - mStartClickOffset < areaWidth) {
                                mStartClickOffset = mEndClickOffset - areaWidth;
                            }
                        }

                    } else {
                        float startClickOffset = (int) ((Math.abs(mOffset) + Math.abs(event.getX())) / areaWidth) * areaWidth;
                        float endClickOffset = startClickOffset + areaWidth;
                        //点击事件 或者点击选中区域进行左滑操作
                        float v1 = event.getX() - startX;
                        if (Math.abs(v1) < 30) {
                            //点击数据啊in
                            if (startClickOffset == mStartClickOffset && endClickOffset == mEndClickOffset) {
                                mStartClickOffset = 0;
                                mEndClickOffset = 0;
                            } else {
                                mStartClickOffset = startClickOffset;
                                //防止内容划出控件
                                if (mStartClickOffset > mDataArea.length * areaWidth - areaWidth) {
                                    mStartClickOffset = mDataArea.length * areaWidth - areaWidth;
                                }
                                //越界判断
                                mEndClickOffset = mStartClickOffset + areaWidth;
                            }
                        } else if (isClickContent) {
                            mStartClickOffset = startClickOffset;
                            if (mStartClickOffset <= 0) {
                                mStartClickOffset = 0;
                            }
                            //向后限制
                            //防止内容划出控件
                            if (mStartClickOffset > mDataArea.length * areaWidth - areaWidth) {
                                mStartClickOffset = mDataArea.length * areaWidth - areaWidth;
                            }
                            //越界判断
                            mEndClickOffset = mStartClickOffset + areaWidth;
                        }
                    }
                }

                if (isScrollArea) {
                    int xVelocity = (int) mVelocityTracker.getXVelocity();
                    setXVelocity(xVelocity);
                    mVelocityTracker.clear();
                }
                if (!isScrollArea && mOnSelectAreaLienter != null) {
                    setOnlisenter();
                }
                startX = 0;
                isScrollArea = false;
                mTempStartOffset = 0;
                isClickContent = false;
                isDrawLeft = false;
                break;
            default:
                break;
        }
        invalidate();

        return true;
    }

    private void setOnlisenter() {
        if (mEndClickOffset == 0) {
            mOnSelectAreaLienter.onSelect("", "");
        } else {
            try {
                int start = (int) (mStartClickOffset / areaWidth);
                float v1 = (mEndClickOffset - mStartClickOffset) / areaWidth;
                int end = (int) (start + v1);
                if (end > mDataArea.length-1) {
                    end = mDataArea.length - 1;
                }
                mOnSelectAreaLienter.onSelect(mDataArea[start], mDataArea[end]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 惯性滑动
     *
     * @param xVelocity
     */
    protected void setXVelocity(int xVelocity) {
        if (Math.abs(xVelocity) < 20) {
            return;
        }
        if (mAnimatorRunning != null && mAnimatorRunning.isRunning()) {
            return;
        }
        mAnimatorRunning = ValueAnimator.ofInt(0, xVelocity / 20).setDuration(Math.abs(xVelocity / 10));
        mAnimatorRunning.setInterpolator(new DecelerateInterpolator());
        mAnimatorRunning.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset += (int) animation.getAnimatedValue();
                mTempset = 0;
                //防止内容划出控件
                float offset = -(limitOffset + getPaddingLeft() + getPaddingRight());
                if (mOffset + mTempset <= offset) {
                    mOffset = offset;
                } else if (mOffset + mTempset >= 0) {
                    mOffset = 0;
                }

                invalidate();
            }

        });
        mAnimatorRunning.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                invalidate();
            }
        });

        mAnimatorRunning.start();
    }


    public TimeSelectView setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
        }
        return this;
    }

    public TimeSelectView setTextsize(int textsize) {
        mTextsize = textsize;
        mTimePaint.setTextSize(mTextsize);
        return this;
    }

    public TimeSelectView setBgColor(int bgColor) {
        mBgColor = bgColor;
        mBgPaint.setColor(mBgColor);
        return this;
    }

    public TimeSelectView setBitmapRes(int bitmapRes) {
        mBitmapRes = bitmapRes;
        mBitmap = BitmapFactory.decodeResource(getResources(), mBitmapRes);

        return this;
    }

    public TimeSelectView setDataArea(String[] dataArea) {
        mDataArea = dataArea;
        return this;
    }

    public TimeSelectView add() {
        mEndClickOffset += areaWidth;
        //防止内容划出控件
        if (mEndClickOffset > mDataArea.length * areaWidth + getPaddingLeft()) {
            mEndClickOffset = mDataArea.length * areaWidth;
        }


        int defaultWidth1 = (int) (mDataArea.length * areaWidth + getPaddingLeft() + getPaddingRight());
        if (defaultWidth1 > mMeasuredWidth) {
            if (mEndClickOffset >= Math.abs(mOffset) + mMeasuredWidth - areaWidth - getPaddingRight()) {
                mOffset = -(mEndClickOffset - mMeasuredWidth + areaWidth + getPaddingRight() + getPaddingLeft());
            }
            float offset = -(limitOffset + getPaddingLeft() + getPaddingRight());
            if (mOffset + mTempset <= offset) {
                mOffset = offset;
                mTempset = 0;
            }
        }
        invalidate();
        setOnlisenter();
        return this;
    }

    public TimeSelectView reduction() {
        mStartClickOffset -= areaWidth;
        if (mStartClickOffset <= getPaddingLeft()) {
            mStartClickOffset = 0;
        }

        int defaultWidth1 = (int) (mDataArea.length * areaWidth + getPaddingLeft() + getPaddingRight());
        if (defaultWidth1 > mMeasuredWidth) {
            if (mStartClickOffset <= Math.abs(mOffset) + areaWidth + getPaddingLeft()) {
                mOffset = -(mStartClickOffset - areaWidth + getPaddingLeft());
            }
            if (mOffset + mTempset >= 0) {
                mOffset = 0;
                mTempset = 0;
            }
        }
        invalidate();
        setOnlisenter();
        return this;
    }


    public interface OnSelectAreaLisenter {
        void onSelect(String start, String end);
    }

    private OnSelectAreaLisenter mOnSelectAreaLienter;

    public void setOnSelectAreaLisenter(OnSelectAreaLisenter onSelectAreaLienter) {
        mOnSelectAreaLienter = onSelectAreaLienter;
    }
}
