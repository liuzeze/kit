package lz.com.tools.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public abstract class BaseRefrashHeader extends FrameLayout implements RefreshInterface {


    public BaseRefrashHeader(@NonNull Context context) {
        this(context, null);
    }

    public BaseRefrashHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseRefrashHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public BaseRefrashHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
