package lz.com.tools.recycleview;


import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import lz.com.tools.recycleview.layoutmanager.LzLinearLayoutManager;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-12-16        越界回弹
 */
public class ReboundReyclerView extends RecyclerView {


    public ReboundReyclerView(Context context) {
        super(context);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LzLinearLayoutManager(context));
    }

    public ReboundReyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LzLinearLayoutManager(context));
    }

    public ReboundReyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LzLinearLayoutManager(context));
    }


    private int scrollDyCounter = 0;

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        if (position == 0) {
            scrollDyCounter = 0;
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (scrollAlphaChangeListener == null) {
            return;
        }
        int height = scrollAlphaChangeListener.setLimitHeight();
        scrollDyCounter = scrollDyCounter + dy;
        if (scrollDyCounter <= 0) {
            scrollAlphaChangeListener.onAlphaChange(0, scrollDyCounter);
        } else if (scrollDyCounter <= height && scrollDyCounter > 0) {
            /** 255/height = x/255 */
            float scale = (float) scrollDyCounter / height;
            float alpha = (255 * scale);
            scrollAlphaChangeListener.onAlphaChange((int) alpha, scrollDyCounter);
        } else {
            scrollAlphaChangeListener.onAlphaChange(255, scrollDyCounter);
        }
    }


    private ScrollAlphaChangeListener scrollAlphaChangeListener;

    public void setScrollAlphaChangeListener(
            ScrollAlphaChangeListener scrollAlphaChangeListener) {
        this.scrollAlphaChangeListener = scrollAlphaChangeListener;
    }

    public interface ScrollAlphaChangeListener {
        void onAlphaChange(int alpha, int scrollDyCounter);

        int setLimitHeight();
    }

}
