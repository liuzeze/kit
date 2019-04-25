package lz.com.kit;

import android.widget.TextView;

import lz.com.kit.view.SimpleRefreshLayout;
import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.widget.ReboundScrollView;

@LayoutId(value = R.layout.activity_scroll_view, titleName = "scrollview")
public class ScrollViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refresh)
    SimpleRefreshLayout refresh;
    @BindView(R.id.reboundScroll)
    ReboundScrollView reboundScroll;

    @Override
    public void init() {
        super.init();
        tvTitle.getBackground().setAlpha(1);

        reboundScroll.setEnableRefrash(true);
        reboundScroll.setRefreshView(refresh);
        reboundScroll.setOnRefreshListener(new ReboundScrollView.OnRefreshListener() {
            @Override
            public void onRefreshing(int offset) {
                tvTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reboundScroll.closeRefresh();
                    }
                }, 2000);
            }
        });

        reboundScroll.setScrollAlphaChangeListener(new ReboundScrollView.ScrollAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha, int scrollDyCounter) {
                tvTitle.getBackground().setAlpha(alpha);
            }

            @Override
            public int setLimitHeight() {
                return 1300;
            }

            @Override
            public void onSwipeFoeword(boolean isUp) {
                super.onSwipeFoeword(isUp);
                System.out.println("滑动方向" + isUp);

            }
        });
    }
}
