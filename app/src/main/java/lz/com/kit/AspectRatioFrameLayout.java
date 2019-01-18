package lz.com.kit;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p>
 *     自定义宽高比例的FrameLayout，例如2:5、4:3等等。
 *     默认为1:1。
 * </p>
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class AspectRatioFrameLayout extends FrameLayout {
    private int baseWhat=0;
    private int xAspect=1;
    private int yAspect=1;
    public AspectRatioFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        if (xAspect <= 0)
            xAspect = 1;
        if (yAspect <= 0)
            yAspect = 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int width;
        int height;
        switch (baseWhat){
            case 0://width
                width = getMeasuredWidth();
                height = width * yAspect / xAspect;
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                break;
            case 1:
                height = getMeasuredHeight();
                width = height * xAspect / yAspect;
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getBaseWhat() {
        return baseWhat;
    }

    public void setBaseWhat(@IntRange(from = 0, to = 1) int baseWhat) {
        this.baseWhat = baseWhat;
        requestLayout();
    }

    public int getxAspect() {
        return xAspect;
    }

    public void setxAspect(@IntRange(from = 1) int xAspect) {
        this.xAspect = xAspect;
        requestLayout();
    }

    public int getyAspect() {
        return yAspect;
    }

    public void setyAspect(@IntRange(from = 1) int yAspect) {
        this.yAspect = yAspect;
        requestLayout();
    }
}
