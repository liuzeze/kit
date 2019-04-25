package lz.com.kit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lz.com.tools.inject.InjectManager;
import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.inject.OnLongClick;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */
@LayoutId(value = R.layout.activity_permission,isShowTitle = false)
public class ExampleFragment extends Fragment {
    @BindView(R.id.btn)
    Button isvConditionSecond;

    @OnClick(R.id.btn)
    public void onShow(View view) {
        Toast.makeText(getContext(), "onclick", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.btn)
    public boolean onLongShow(View view) {
        Toast.makeText(getContext(), "onlongclick", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class<?> aClass = getClass();
        LayoutId layoutId = aClass.getDeclaredAnnotation(LayoutId.class);
        View inflate;
        if (layoutId.isShowTitle()) {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(lz.com.tools.R.layout.layout_root, null);
            TitleToolbar titleToolbar = linearLayout.findViewById(R.id.common_toolbar);
            titleToolbar.setTitle(layoutId.titleName());
            titleToolbar.setBackVisible(layoutId.isShowBackIcon());
            inflater.inflate(layoutId.value(), linearLayout);
            inflate = linearLayout;
        } else {
            inflate = inflater.inflate(layoutId.value(), container, false);
        }


        InjectManager.inject(this, inflate);


        isvConditionSecond.setText("fragment中使用ioc ");

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
