package lz.com.kit;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.util.LzSnackbarUtils;
import lz.com.tools.util.LzToast;


@LayoutId(R.layout.activity_ioc)
public class IocActivity extends BaseKitActivity {

    @BindView(R.id.btn1)
    Button bt1;

    @OnClick(R.id.btn)
    public void onShow(View view) {
        LzToast.error("单击事件");
    }

    @OnLongClick(R.id.btn)
    public boolean onLongShow(View view) {
        LzSnackbarUtils.Short(view, "长按事件").show();
        return false;
    }

    @Override
    public void initData() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LzSnackbarUtils.Short(v, "控件获取").gravityFrameLayout(Gravity.TOP).show();
            }
        });
    }


}
