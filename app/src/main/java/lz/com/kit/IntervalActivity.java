package lz.com.kit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import lz.com.kit.view.MenuView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.util.LzDisplayUtils;
import lz.com.tools.view.TimeSelectView;
import lz.com.tools.widget.IntervalSelectionView;


@InjectLayout(layoutId = R.layout.activity_interval)
public class IntervalActivity extends BaseKitActivity {

    @BindView(R.id.isv_condition_second)
    IntervalSelectionView isvConditionSecond;
    @BindView(R.id.time_view)
    TimeSelectView timeView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.menuview)
    MenuView menuview;

    @BindViews({R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5})
    ImageView[] images;
    @BindView(R.id.bg)
    View bg;


    private String[] mDataArea = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
            "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",};

    @Override
    public void initData() {

        isvConditionSecond.post(new Runnable() {
            @Override
            public void run() {
                isvConditionSecond
                        .setStartNum(0)
                        .setEndNum(1000)
                        .setNumStart(500)
                        .setNumEnd(756)
                        .apply();
            }
        });
        isvConditionSecond.setOnChangeListener(new IntervalSelectionView.OnChangeListener() {
            @Override
            public void leftCursor(String resultValue) {

            }

            @Override
            public void rightCursor(String resultValue) {

            }
        });

        timeView.setOnSelectAreaLisenter(new TimeSelectView.OnSelectAreaLisenter() {
            @Override
            public void onSelect(String start, String end) {
                tvTime.setText("时间\n" + start + "--" + end);
            }
        });
//        timeView.setDataArea(mDataArea);

        //433.98  432.03  164.4
    }

    public void add(View view) {
        timeView.increase();

    }

    public void reduction(View view) {
        timeView.reduction();

    }

    public void centerview(View view) {
        if (menuview.isOpen()) {
            menuview.reset();

            for (int i = 0; i < images.length; i++) {
                ImageView imageView = images[i];
                imageView.animate().scaleX(0f).scaleY(0f).setDuration(300).translationX(0).translationY(0).start();
            }
            bg.animate().alpha(0f).setDuration(300).start();
        } else {
            menuview.startAni();

            float width = LzDisplayUtils.getScreenWidth(this) * 2 / 5;
            bg.animate().alpha(0.3f).setDuration(300).start();
            for (int i = 0; i < images.length; i++) {
                ImageView imageView = images[i];
                float radian = (float) Math.toRadians((i + 1) * 30);
                int x = (int) (Math.cos(radian) * width);
                int y = -(int) (Math.sin(radian) * width) + 2 * imageView.getMeasuredHeight();

                imageView.animate().scaleX(1f).scaleY(1f).setDuration(300).translationX(x).translationY(y).start();
            }

        }
    }

    public void bottomview(View view) {
        if (menuview.isOpen()) {
            menuview.reset();
        }
    }
}
