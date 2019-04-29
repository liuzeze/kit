package lz.com.tools.widget;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import lz.com.tools.R;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-23       创建class
 */
public class WaterRippleAnim extends AppCompatTextView {

    private float maskX, maskY;
    private boolean sinking;
    private boolean setUp;

    private Matrix mMatrix;
    private Drawable mWaveBitmap;
    private BitmapShader mBitmapShader;
    private int offsetY;

    public WaterRippleAnim(Context context) {
        super(context);
        init();
    }

    public WaterRippleAnim(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public WaterRippleAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMatrix = new Matrix();

    }


    public float getMaskX() {
        return maskX;
    }

    public void setMaskX(float maskX) {
        this.maskX = maskX;
        invalidate();
    }

    public float getMaskY() {
        return maskY;
    }

    public void setMaskY(float maskY) {
        this.maskY = maskY;
        invalidate();
    }

    public boolean isSinking() {
        return sinking;
    }

    public void setSinking(boolean sinking) {
        this.sinking = sinking;
    }

    public boolean isSetUp() {
        return setUp;
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        createShader();
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        createShader();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        createShader();

        if (!setUp) {
            setUp = true;
            animate.run();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (sinking && mBitmapShader != null) {
            if (getPaint().getShader() == null) {
                getPaint().setShader(mBitmapShader);
            }
            mMatrix.setTranslate(maskX, maskY + offsetY);
            mBitmapShader.setLocalMatrix(mMatrix);
        } else {
            getPaint().setShader(null);
        }
        super.onDraw(canvas);
    }


    private void createShader() {

        if (mWaveBitmap == null) {
            mWaveBitmap = getResources().getDrawable(R.mipmap.wave);
            mWaveBitmap.setTint(Color.RED);
        }

        int waveW = mWaveBitmap.getIntrinsicWidth();
        int waveH = mWaveBitmap.getIntrinsicHeight();

        Bitmap b = Bitmap.createBitmap(waveW, waveH, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        c.drawColor(getCurrentTextColor());

        mWaveBitmap.setBounds(0, 0, waveW, waveH);
        mWaveBitmap.draw(c);

        mBitmapShader = new BitmapShader(b, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        getPaint().setShader(mBitmapShader);

        offsetY = (getHeight() - waveH) / 2;
    }


    final Runnable animate = new Runnable() {
        @Override
        public void run() {

            setSinking(true);

            ObjectAnimator maskXAnimator = ObjectAnimator.ofFloat(WaterRippleAnim.this, "maskX", 0, 160);
            maskXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            maskXAnimator.setDuration(1000);
            maskXAnimator.setStartDelay(0);

            int h = getHeight();


            ObjectAnimator maskYAnimator = ObjectAnimator.ofFloat(WaterRippleAnim.this, "maskY", h / 2, -h / 2);
            maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
            maskYAnimator.setDuration(10000);
            maskYAnimator.setStartDelay(0);

            animatorSet = new AnimatorSet();
            animatorSet.playTogether(maskXAnimator, maskYAnimator);
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setSinking(false);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        postInvalidate();
                    } else {
                        postInvalidateOnAnimation();
                    }

                    animatorSet = null;
                }
            });

            if (animatorListener != null) {
                animatorSet.addListener(animatorListener);
            }

            animatorSet.start();
        }
    };

    private AnimatorSet animatorSet;
    private Animator.AnimatorListener animatorListener;

    public Animator.AnimatorListener getAnimatorListener() {
        return animatorListener;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }

    public void start() {
        if (isSetUp()) {
            animate.run();
        }
    }

    public void cancel() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
    }


}
