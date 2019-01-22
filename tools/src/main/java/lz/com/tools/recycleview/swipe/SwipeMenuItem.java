package lz.com.tools.recycleview.swipe;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-17       创建class
 */
public class SwipeMenuItem {
    private Context mContext;
    private int mDeleteLayoutId = -1;
    private String mRightText = "删除";
    private boolean mEnableSwipe = true;
    private boolean mIsIos = false;
    private boolean mLeftSwipe = true;
    private Drawable background;
    private ColorStateList mTitleColor = ColorStateList.valueOf(Color.WHITE);

    private int width = -2;
    private int height = -2;

    public boolean isIos() {
        return mIsIos;
    }

    public SwipeMenuItem setIos(boolean ios) {
        mIsIos = ios;
        return this;
    }

    public boolean isLeftSwipe() {
        return mLeftSwipe;
    }

    public SwipeMenuItem setLeftSwipe(boolean leftSwipe) {
        mLeftSwipe = leftSwipe;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public SwipeMenuItem setHeight(int height) {
        this.height = height;
        return this;
    }

    public SwipeMenuItem setTextColorResource(@ColorRes int titleColor) {
        return setTextColor(ContextCompat.getColor(mContext, titleColor));
    }

    public SwipeMenuItem setTextColor(@ColorInt int titleColor) {
        mTitleColor = ColorStateList.valueOf(titleColor);
        return this;
    }

    public ColorStateList getTitleColor() {
        return mTitleColor;
    }


    public Drawable getBackground() {
        if (background == null) {
            background = ContextCompat.getDrawable(mContext, android.R.color.holo_red_light);
        }
        return background;
    }

    public SwipeMenuItem setBackground(@DrawableRes int background) {
        this.background = ContextCompat.getDrawable(mContext, background);
        return this;
    }

    public SwipeMenuItem(Context context) {
        mContext = context;
    }


    public SwipeMenuItem setDeleteLayoutId(@LayoutRes int deleteLayoutId) {
        mDeleteLayoutId = deleteLayoutId;
        return this;
    }

    public boolean isEnableSwipe() {
        return mEnableSwipe;
    }

    public SwipeMenuItem setEnableSwipe(boolean enableSwipe) {
        mEnableSwipe = enableSwipe;
        return this;
    }



    public String getRightText() {
        return mRightText;
    }

    public SwipeMenuItem setRightText(String rightText) {
        mRightText = rightText;
        return this;
    }


    public View getRightLayout() {
        View view = null;
        if (mDeleteLayoutId != -1) {
            view = View.inflate(mContext, mDeleteLayoutId, null);
        }
        if (view == null) {
            view = new TextView(mContext);
            TextView textView = (TextView) view;
            textView.setText(getRightText());
            textView.setTextColor(getTitleColor());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
        }
        return view;
    }

}
