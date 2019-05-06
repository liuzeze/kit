package lz.com.kit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import lz.com.kit.bean.TabEntity;
import lz.com.tools.inject.InjectLayout;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_tab, titleName = "tablayout")
public class TablayoutActivity extends BaseKitActivity {


    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.tl_1)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.tl_3)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.tl_2)
    SegmentTabLayout mSegmentTabLayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void initData() {


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            mFragments.add(SimpleCardFragment.getInstance(mTitles[i]));
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.showDot(2);
        mCommonTabLayout.showMsg(1, 22);

        mSegmentTabLayout.setTabData(mTitles);

        mSlidingTabLayout.setViewPager(mViewPager, mTitles);


        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCommonTabLayout.setCurrentTab(position);
                mSegmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mSegmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCommonTabLayout.setCurrentTab(position);
                mSlidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mSlidingTabLayout.setCurrentTab(position);
                mSegmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mSlidingTabLayout.setCurrentTab(position);
                mSegmentTabLayout.setCurrentTab(position);
                mCommonTabLayout.setCurrentTab(position);
            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    public static class SimpleCardFragment extends Fragment {
        private String mTitle;

        public static SimpleCardFragment getInstance(String title) {
            SimpleCardFragment sf = new SimpleCardFragment();
            sf.mTitle = title;
            return sf;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView card_title_tv = new TextView(getContext());
            card_title_tv.setText(mTitle);

            return card_title_tv;
        }
    }
}
