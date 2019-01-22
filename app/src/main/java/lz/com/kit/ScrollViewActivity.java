package lz.com.kit;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.view.SimpleRefreshLayout;
import lz.com.tools.ReboundScrollView;

public class ScrollViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refresh)
    SimpleRefreshLayout refresh;
    @BindView(R.id.reboundScroll)
    ReboundScrollView reboundScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        ButterKnife.bind(this);

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
