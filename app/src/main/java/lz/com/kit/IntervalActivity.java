package lz.com.kit;

import android.view.View;

import butterknife.BindView;
import lz.com.kit.view.TimeSelectView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.widget.IntervalSelectionView;


@InjectLayout(layoutId = R.layout.activity_interval)
public class IntervalActivity extends BaseKitActivity {

    @BindView(R.id.isv_condition_second)
    IntervalSelectionView isvConditionSecond;
    @BindView(R.id.time_view)
    TimeSelectView timeView;

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
    }

    public void add(View view) {
        timeView.add();
    }

    public void reduction(View view) {
        timeView.reduction();
    }
}
