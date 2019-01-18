package lz.com.kit;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.tools.ReboundScrollView;

import static lz.com.kit.RecyclerViewActivity.EXPLODE_CODE;
import static lz.com.kit.RecyclerViewActivity.SLIDE_CODE;

public class ScrollViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refresh)
    SimpleRefreshLayout refresh;
    @BindView(R.id.reboundScroll)
    ReboundScrollView reboundScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Transition transition = null;
        switch (getIntent().getIntExtra("type", -1)) {
            case EXPLODE_CODE:
                transition = new Explode();
                transition.setDuration(1000);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
         /*   case EXPLODE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_explode);
                break;*/
            case SLIDE_CODE:
                transition = new Slide();
                ((Slide) transition).setSlideEdge(Gravity.RIGHT);
                transition.setDuration(1000);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
           /* case SLIDE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_slide);
                break;*/
        }
        if (transition != null) {
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);
        }

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
                System.out.println(alpha+"========滑动距离" + scrollDyCounter);
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
