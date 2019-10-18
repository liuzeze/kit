package lz.com.kit;

import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.view.ShadowLayout;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_shadow, titleName = "阴影控件")
public class ShadowActivity extends BaseKitActivity {


    private ShadowLayout mViewById;

    @Override
    protected void initData() {

        mViewById = findViewById(R.id.ShadowLayout);
        mViewById.setShadowBlur(20).setOffsetY(10).apply();
        mViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewById.setSelected(!mViewById.isSelected());

            }
        });
    }
}
