package lz.com.tools.city;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import lz.com.tools.R;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-03-21       城市选择控件 加载ui
 */
public class CityLoadingView extends FrameLayout {
    ProgressBar loading;
    TextView tvName;

    public CityLoadingView(@NonNull Context context) {
        super(context);
        View inflate = View.inflate(getContext(), R.layout.layout_city_loading, null);

        loading = (ProgressBar) inflate.findViewById(R.id.loading);
        tvName = (TextView) inflate.findViewById(R.id.tv_name);
        addView(inflate);

    }

    public void setProgressGone(int Visibility) {
        loading.setVisibility(Visibility);
    }

    public void setText(String name) {
        tvName.setText(name);
    }

    public void removeParent() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

}
