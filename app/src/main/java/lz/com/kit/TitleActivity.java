package lz.com.kit;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import butterknife.BindView;
import lz.com.tools.inject.InjectLayout;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_title, titleName = "标题控件")
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





           /* EventBus.getDefault().register(this);


            EventBus.getDefault().post(null);


            EventBus.getDefault().unregister(this);*/

        }


    }


    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ActivityBean activityBean) {
    }*/




}
