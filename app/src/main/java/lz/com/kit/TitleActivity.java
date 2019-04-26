package lz.com.kit;

import butterknife.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(value = R.layout.activity_title, titleName = "标题控件")
public class TitleActivity extends BaseKitActivity {


    @BindView(R.id.common_toolbar)
    TitleToolbar mTitleToolbar;

    @Override
    public void initData() {
        if (mTitleToolbar != null) {
            mTitleToolbar.setSubtitle("subtitle");
            mTitleToolbar.setRightText("按钮");
        }
    }


}
