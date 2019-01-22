package lz.com.kit;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import lz.com.tools.refresh.BaseRefrashHeader;
import lz.com.tools.refresh.RefreshState;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public class SimpleRefreshLayout extends BaseRefrashHeader {

    private View mCloud1, mCloud2, mCloud3, mCircle, mHeader;
    private ValueAnimator circleAnimator;

    public SimpleRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SimpleRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.header_refresh, this);

        mCloud1 = findViewById(R.id.iv_cloud1);
        mCloud2 = findViewById(R.id.iv_cloud2);
        mCloud3 = findViewById(R.id.iv_cloud3);
        mCircle = findViewById(R.id.iv_circle);

        mHeader = findViewById(R.id.view_header);
    }

    public void startSwip() {
        startCloudAnim(mCloud3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnim(mCloud2);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnim(mCloud1);
            }
        }, 400);
    }

    public void startRefresh() {
        mCircle.setVisibility(VISIBLE);
        circleAnimator = ValueAnimator.ofInt(0, 360);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCircle.setRotation(value);
            }
        });
        circleAnimator.setInterpolator(null);
        circleAnimator.setDuration(600);
        circleAnimator.setRepeatCount(-1);
        circleAnimator.setRepeatMode(ValueAnimator.RESTART);
        circleAnimator.start();
    }

    public void refreshComplete() {
        stopAnim();
    }

    private void stopAnim() {
        if (circleAnimator != null) {
            circleAnimator.cancel();
        }
        mCircle.setVisibility(INVISIBLE);

        if (mCloud1.getTag() != null) {
            ValueAnimator animator = (ValueAnimator) mCloud1.getTag();
            animator.cancel();
        }
        if (mCloud2.getTag() != null) {
            ValueAnimator animator1 = (ValueAnimator) mCloud2.getTag();
            animator1.cancel();
        }
        if (mCloud3.getTag() != null) {
            ValueAnimator animator = (ValueAnimator) mCloud3.getTag();
            animator.cancel();
        }
    }

    private void startCloudAnim(final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.5f);
        view.setTag(valueAnimator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value);
            }
        });
        valueAnimator.setDuration(600);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }


    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

        if (percent < 0.6) {
            percent = 0.6f;
        }
        if (percent > 1.5) {
            percent = 1.5f;
        }
        mHeader.setScaleX(percent);
        mHeader.setScaleY(percent);

    }

    @Override
    public void onStateChanged(@NonNull BaseRefrashHeader refreshLayout,  int oldState,  int newState) {
        switch (newState) {
//            case None:
            case RefreshState.PullDownToRefresh:
                startSwip();
                break;
            case RefreshState.Refreshing:
            case RefreshState.RefreshReleased:
                startRefresh();
                break;
            case RefreshState.PullDownCanceled:
                stopAnim();
                break;
            case RefreshState.RefreshFinish:
                stopAnim();
                break;
            default:
                break;
        }
    }
}
