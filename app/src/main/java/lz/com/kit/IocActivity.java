package lz.com.kit;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import lz.com.kit.view.SimpleRefreshLayout;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ScrollHelperView;
import lz.com.tools.util.LzSnackbarUtils;
import lz.com.tools.util.LzToast;


@InjectLayout(layoutId = R.layout.activity_ioc)
public class IocActivity extends BaseKitActivity {

    @BindView(R.id.btn1)
    Button bt1;
    @BindView(R.id.container)
    ScrollHelperView container;
    @BindView(R.id.content)
    LinearLayout content;

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


        SimpleRefreshLayout simpleRefreshLayout = new SimpleRefreshLayout(this);
        content.addView(simpleRefreshLayout, 0);
        container.setRefreshView(simpleRefreshLayout);
    }


}
