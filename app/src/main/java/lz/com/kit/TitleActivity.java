package lz.com.kit;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.shopview.SlideLayout;
import lz.com.tools.toolbar.TitleToolbar;
import lz.com.tools.util.LzResUtils;
import lz.com.tools.util.LzWebViewUtils;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(R.layout.activity_title)
public class TitleActivity extends BaseActivity {


    @InjectView(R.id.common_toolbar)
    TitleToolbar mTitleToolbar;

    @Override
    public void init() {
        super.init();
        mTitleToolbar.setTitle("title");
        mTitleToolbar.setSubtitle("subtitle");
        mTitleToolbar.setRightText("按钮");


    }


}
