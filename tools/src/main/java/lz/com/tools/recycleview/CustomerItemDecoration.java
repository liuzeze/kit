package lz.com.tools.recycleview;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-12-13       列表分割线
 */

public class CustomerItemDecoration extends RecyclerView.ItemDecoration {

    private  @ColorInt int mColor =Color.parseColor("#ebebeb");     //分割线Drawable
    private float mDividerHeight = 0.5f;  //分割线高度

    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;
    private Paint paint;
    private int gravity = LinearLayoutManager.VERTICAL;

    /**
     * 使用line_divider中定义好的颜色
     */
    public CustomerItemDecoration() {
        paint = new Paint();
        paint.setColor(mColor);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                outRect.set((int) mDividerHeight, (int) mDividerHeight, (int) mDividerHeight, (int) mDividerHeight);
            } else if (layoutManager instanceof LinearLayoutManager) {
                if (gravity == LinearLayoutManager.VERTICAL) {
                    outRect.set(0, 0, 0, (int) mDividerHeight);
                } else {
                    outRect.set(0, 0, (int) mDividerHeight, 0);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                paint.setColor(mColor);

                int left = mPaddingLeft == 0 ? parent.getPaddingLeft() : mPaddingLeft;
                int right = parent.getWidth() - (mPaddingRight == 0 ? parent.getPaddingRight() : mPaddingRight);

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    float bottom = top + mDividerHeight * 2;
                    c.drawRect(left, top, right, bottom, paint);

                    c.drawRect(child.getRight(), child.getTop(), child.getRight() + mDividerHeight, child.getBottom(), paint);
                    c.drawRect(child.getLeft() - mDividerHeight, child.getTop(), child.getLeft(), child.getBottom(), paint);
                }
            } else if (layoutManager instanceof LinearLayoutManager) {

                int left = mPaddingLeft == 0 ? parent.getPaddingLeft() : mPaddingLeft;
                int right = parent.getWidth() - (mPaddingRight == 0 ? parent.getPaddingRight() : mPaddingRight);

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    if (gravity == LinearLayoutManager.VERTICAL) {
                        int top = child.getBottom() + params.bottomMargin;
                        float bottom = top + mDividerHeight;
                        c.drawLine(left, top, right, bottom, paint);
                    } else {
                        c.drawLine(parent.getWidth(), child.getTop(), parent.getWidth() + mDividerHeight, child.getBottom(), paint);
                    }

                }
            }
        }

    }


    public CustomerItemDecoration setDividerColor(@ColorInt int color) {
        mColor = color;
        return this;
    }

    public CustomerItemDecoration setDividerHeight(float dividerHeight) {
        mDividerHeight = dividerHeight;
        return this;
    }


    public CustomerItemDecoration setPaddingLeft(int paddingLeft) {
        mPaddingLeft = paddingLeft;
        return this;
    }

    public CustomerItemDecoration setPaddingRight(int paddingRight) {
        mPaddingRight = paddingRight;
        return this;
    }

    public CustomerItemDecoration setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }
}