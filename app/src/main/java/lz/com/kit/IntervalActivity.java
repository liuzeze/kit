package lz.com.kit;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import lz.com.tools.view.TimeSelectView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.widget.IntervalSelectionView;


@InjectLayout(layoutId = R.layout.activity_interval)
public class IntervalActivity extends BaseKitActivity {

    @BindView(R.id.isv_condition_second)
    IntervalSelectionView isvConditionSecond;
    @BindView(R.id.time_view)
    TimeSelectView timeView;
    @BindView(R.id.tv_time)
    TextView tvTime;
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
    }

    public void add(View view) {
        timeView.add();
    }

    public void reduction(View view) {
        timeView.reduction();
    }
}
