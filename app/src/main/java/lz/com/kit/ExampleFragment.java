package lz.com.kit;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import lz.com.tools.inject.LayoutId;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */
@LayoutId(value = R.layout.activity_permission)
public class ExampleFragment extends BaseKitFragment {
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


    @Override
    protected void initData() {
        isvConditionSecond.setText("fragment中使用ioc ");
    }


    @Override
    public void showErrorMsg(String msg) {

    }
}
