package lz.com.kit;

import android.graphics.Color;

import lz.com.tools.inject.InjectLayout;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_title,titleName = "标题控件")
public class TitleActivity extends BaseKitActivity {

/*

    @BindView(R.id.common_toolbar)
    TitleToolbar mTitleToolbar;
*/

    @Override
    public void initData() {
        if (getTitleToolbar() != null) {
            getTitleToolbar().setSubtitle("subtitle");
            getTitleToolbar().setSubtitleTextColor(Color.RED);
            getTitleToolbar().setSubtitleVisible(true);
            getTitleToolbar().setRightVisible(true);
            getTitleToolbar().setRightText("按钮");
            getTitleToolbar().setRightIco(getDrawable(R.drawable.arrow_right_red));
        }
    }


}
