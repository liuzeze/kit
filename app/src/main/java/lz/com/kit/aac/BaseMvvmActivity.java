package lz.com.kit.aac;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lz.com.tools.inject.InjectLayout;
import lz.com.tools.toolbar.TitleToolbar;


public abstract class BaseMvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {
    protected V mBinding;
    protected VM viewModel;
    protected Activity mActivity;
    private TitleToolbar titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        addObserver();
        setContentViews();
        initData();
    }

    private void setContentViews() {
        LayoutInflater from = LayoutInflater.from(mActivity);
        InjectLayout injectLayout = getClass().getDeclaredAnnotation(InjectLayout.class);
        if (injectLayout != null) {
            int value = injectLayout.layoutId();
            int variableId = injectLayout.variableId();
            if (injectLayout.isShowActTitle()) {
                LinearLayout root = (LinearLayout) from.inflate(lz.com.tools.R.layout.layout_root, null);
                TitleToolbar titleToolbar = root.findViewById(lz.com.tools.R.id.common_toolbar);
                titleToolbar.setTitle(injectLayout.titleName());
                titleToolbar.setBackVisible(injectLayout.isShowBackIcon());
                mBinding = DataBindingUtil.inflate(from, value, root, true);
                setContentView(root);
            } else {
                mBinding = DataBindingUtil.setContentView(this, value);
            }
            mBinding.setVariable(variableId, viewModel);

        }
    }

    protected abstract void initData();

    protected TitleToolbar getTitleToolbar() {
        if (titleToolbar == null) {
            titleToolbar = findViewById(lz.com.tools.R.id.common_toolbar);
        }

        if (titleToolbar == null) {
            titleToolbar = new TitleToolbar(mActivity);
        }
        return titleToolbar;
    }

    private void addObserver() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) ViewModelProviders.of(this).get(modelClass);
        }
        getLifecycle().addObserver(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(viewModel);
    }
}