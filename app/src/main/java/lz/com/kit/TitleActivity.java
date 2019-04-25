package lz.com.kit;

import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(value = R.layout.activity_title, titleName = "标题控件")
public class TitleActivity extends BaseActivity {


    @BindView(R.id.common_toolbar)
    TitleToolbar mTitleToolbar;

    @Override
    public void init() {
        super.init();
        if (mTitleToolbar != null) {
            mTitleToolbar.setSubtitle("subtitle");
            mTitleToolbar.setRightText("按钮");
        }


    }


}
