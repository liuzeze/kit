package lz.com.kit;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.inject.OnLongClick;
import lz.com.tools.util.LzSnackbarUtils;
import lz.com.tools.util.LzToast;


@LayoutId(R.layout.activity_ioc)
public class IocActivity extends BaseActivity {

    @InjectView(R.id.btn1)
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
    public void init() {
        super.init();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LzSnackbarUtils.Short(v, "控件获取").gravityFrameLayout(Gravity.TOP).show();
            }
        });
    }


}
