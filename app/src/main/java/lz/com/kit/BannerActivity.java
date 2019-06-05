package lz.com.kit;

import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import lz.com.tools.inject.InjectLayout;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_banner, titleName = "标题控件")
public class BannerActivity extends BaseKitActivity {
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};


    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.tl_2)
    SegmentTabLayout mSegmentTabLayout;


    @Override
    protected void initData() {


        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        int[] ints = {R.mipmap.index_yz_ze_zydotknowledge_picze,
                R.mipmap.index_yz_ze_zydotknowledge_piczs,
                R.mipmap.index_yz_ze_zydotknowledge_piczs,
                R.mipmap.index_yz_ze_zydotknowledge_piczy};
        bannerGuideContent.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                ints);

        bannerGuideContent.setAutoPlayAble(ints.length > 2);

        mSegmentTabLayout.setTabData(mTitles);
        bannerGuideContent.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mSegmentTabLayout.setCurrentTab(position % 4);


            }
        });


    }
}
