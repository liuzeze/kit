package lz.com.kit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lz.com.tools.inject.InjectManager;
import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.inject.OnLongClick;
import lz.com.tools.widget.IntervalSelectionView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */
@LayoutId(R.layout.activity_permission)
public class ExampleFragment extends Fragment {
    @InjectView(R.id.btn)
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

        int layoutId = InjectManager.getLayoutId(this);
        View inflate = inflater.inflate(layoutId, container, false);
        InjectManager.inject(this, inflate);


        isvConditionSecond.setText("fragment中使用ioc ");

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
