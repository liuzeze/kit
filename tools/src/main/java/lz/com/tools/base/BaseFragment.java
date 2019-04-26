package lz.com.tools.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lz.fram.base.BaseView;
import com.lz.fram.inject.PresenterDispatch;
import com.lz.fram.inject.PresenterProviders;

import butterknife.ButterKnife;
import lz.com.tools.R;
import lz.com.tools.inject.InjectManager;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * Activity 基类
 * Created by 刘泽 on 2017/7/10 18:50.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = getRootView(inflater, container);
        return inflate;
    }

    private View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        Class<?> aClass = getClass();
        LayoutId layoutId = aClass.getDeclaredAnnotation(LayoutId.class);
        View inflate;
        if (layoutId.isShowFragTitle()) {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(lz.com.tools.R.layout.layout_root, null);
            TitleToolbar titleToolbar = linearLayout.findViewById(R.id.common_toolbar);
            titleToolbar.setTitle(layoutId.titleName());
            titleToolbar.setBackVisible(layoutId.isShowBackIcon());
            inflater.inflate(layoutId.value(), linearLayout);
            inflate = linearLayout;
        } else {
            inflate = inflater.inflate(layoutId.value(), container, false);
        }
       // InjectManager.inject(this, inflate);

        PresenterDispatch presenterDispatch = PresenterProviders.inject(this).presenterCreate();
        presenterDispatch.attachView(this, getLifecycle());

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initLisenter();
    }

    protected abstract void initData();

    protected void initLisenter() {
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }
    @Override
    public void showErrorMsg(String msg) {

    }
}
