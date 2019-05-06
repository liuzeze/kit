package lz.com.tools.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.lz.fram.base.BaseView;
import com.lz.fram.inject.PresenterDispatch;
import com.lz.fram.inject.PresenterProviders;

import lz.com.tools.R;
import lz.com.tools.inject.InjectManager;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * Activity 基类
 * Created by 刘泽 on 2017/7/10 18:50.
 */

public abstract class BaseActivity extends FragmentActivity implements BaseView {

    protected Activity mActivity;
    private TitleToolbar titleToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initConfig();
        initData();
        initLisenter();
    }

    /**
     * 初始化公用的参数
     */
    protected void initConfig() {
        mActivity = this;
        InjectManager.getLayoutId(this);
        PresenterDispatch presenterDispatch = PresenterProviders.inject(this).presenterCreate();
        presenterDispatch.attachView(this, getLifecycle());
    }

    protected TitleToolbar getTitleToolbar() {
        if (titleToolbar == null) {
            titleToolbar = findViewById(R.id.common_toolbar);
        }

        if (titleToolbar == null) {
            titleToolbar = new TitleToolbar(this);
        }
        return titleToolbar;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected void initLisenter() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

}
