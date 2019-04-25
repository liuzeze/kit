package lz.com.kit;

import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.widget.IntervalSelectionView;


@LayoutId(R.layout.activity_interval)
public class IntervalActivity extends BaseActivity {

    @BindView(R.id.isv_condition_second)
    IntervalSelectionView isvConditionSecond;

    @Override
    public void init() {
        super.init();

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
}
