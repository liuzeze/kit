package lz.com.tools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-16       创建class
 */
public class BigImageView extends View implements
        GestureDetector.OnGestureListener,
        View.OnTouchListener {

    private Rect mRect;
    private GestureDetector mGestureDetector;
    private BitmapFactory.Options mOptions;
    private Scroller mScroller;
    private int mImgOutWidth;
    private int mImgOutHeight;
    private BitmapRegionDecoder mBitmapRegionDecoder;
    private int mViewHeight;
    private int mViewWidth;
    private Bitmap bitmap;
    private Matrix mMatrix;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BigImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //图片要显示的区域
        mRect = new Rect();
        //手势滑动起
        mGestureDetector = new GestureDetector(getContext(), this);
        //图片option信息
        mOptions = new BitmapFactory.Options();
        //滑动帮助类
        mScroller = new Scroller(getContext());

        mMatrix = new Matrix();

        setOnTouchListener(this);

    }


    public void setImageInput(InputStream is) {
        //计算图片的原始大小 和控件的大小
        mOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(is, null, mOptions);
        mImgOutWidth = mOptions.outWidth;
        mImgOutHeight = mOptions.outHeight;
        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        mOptions.inJustDecodeBounds = false;
        //图片解码区域
        try {
            mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mRect.left = 0;
        mRect.top = 0;

        mRect.right = mViewWidth;
        mRect.bottom = mViewHeight;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapRegionDecoder == null) {
            return;
        }
        mOptions.inBitmap = bitmap;
        bitmap = mBitmapRegionDecoder.decodeRegion(mRect, mOptions);
        canvas.drawBitmap(bitmap, mMatrix, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) ;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset((int) distanceX, (int) distanceY);
        if (mRect.bottom > mImgOutHeight) {
            mRect.top = (int) (mImgOutHeight - mViewHeight);
            mRect.bottom = mImgOutHeight;
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight);
        }

        if (mRect.left < 0) {
            mRect.left = 0;
            mRect.right = (int) (mViewWidth);
        }

        if (mRect.right > mImgOutWidth) {
            mRect.left = (int) (mImgOutWidth - mViewWidth);
            mRect.right = mImgOutWidth;
        }
        invalidate();
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mRect.left, mRect.top,
                (int) -velocityX, (int) -velocityY,
                0, (int) (mImgOutWidth - mViewWidth),
                0, mImgOutHeight - (int) (mViewHeight));
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.isFinished()) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.getCurrY();
            mRect.left = mScroller.getCurrX();
            mRect.right = mRect.left + (int) (mViewWidth);
            mRect.bottom = mRect.top + (int) (mViewHeight);
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }


}
