package lz.com.kit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.tools.IntervalSelectionView;

public class IntervalActivity extends BaseActivity {

    @BindView(R.id.isv_condition_second)
    IntervalSelectionView isvConditionSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        ButterKnife.bind(this);

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
