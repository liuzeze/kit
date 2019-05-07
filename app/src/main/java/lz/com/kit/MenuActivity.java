package lz.com.kit;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lz.com.tools.city.CityLoadingView;
import lz.com.tools.city.CityPickerInfo;
import lz.com.kit.view.CitySelectView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.util.LzToast;
import lz.com.tools.widget.DropDownMenu;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_menu, titleName = "下拉控件")
public class MenuActivity extends BaseKitActivity {

    public String[] titleText = {"区域", "价格", "房型", "更多", "haha"};
    public View[] mViews = new View[titleText.length];

    @BindView(R.id.dropdownmenu)
    DropDownMenu mDropDownMenu;

    private int[] mCitySelectPosition;
    private ArrayList<CityPickerInfo> mAreaItem = new ArrayList<>();
    //加载框
    private CityLoadingView mCityLoadingView;

    @Override
    public void initData() {
//        mDropDownMenu.setContentView();
        mDropDownMenu.addTab(titleText);
        for (int i = 0; i < titleText.length; i++) {


            TextView textView = new TextView(mActivity);
            textView.setBackgroundResource(R.color.colorPrimary);
            textView.setText(titleText[i] + "\n\n\n\n\n\n");
            mViews[i] = textView;
        }
        mViews[0] = getCityView();


        mDropDownMenu.setDropDownMenu(mViews);
    }

    private View getCityView() {
        mCityLoadingView = new CityLoadingView(mActivity);

        CityPickerInfo cityPickerInfo;
        CityPickerInfo cityPickerInfo1;
        for (int i = 0; i < 5; i++) {
            cityPickerInfo = new CityPickerInfo();
            cityPickerInfo.setName("一级标题");
            for (int i1 = 0; i1 < 10; i1++) {
                cityPickerInfo1 = new CityPickerInfo();
                cityPickerInfo1.setName("二级标题");
                cityPickerInfo.addCity(cityPickerInfo1);
            }
            mAreaItem.add(cityPickerInfo);
        }
        CitySelectView citySelectView = new CitySelectView(mActivity);
        citySelectView.init(mCitySelectPosition, mAreaItem, " ");
        citySelectView.setOnAreaSelectListener(new CitySelectView.OnAreaSelectListener() {
            @Override
            public void onAreaSelect(String s, int... position) {
                if (!TextUtils.isEmpty(s)) {
                    LzToast.showToast(s);
                    mCitySelectPosition = position;
                    mDropDownMenu.closeMenu();
                }
            }
        });

        citySelectView.setOnReqDataListener(new CitySelectView.OnReqDataListener() {
            @Override
            public void onCityDataSelect(BaseRecycleAdapter<CityPickerInfo, BaseViewHolder> adapter, List<CityPickerInfo> cityList, String id) {
                //添加加载布局
                adapter.setNewData(null);
                mCityLoadingView.removeParent();
                adapter.setEmptyView(mCityLoadingView);
                mCityLoadingView.setText("正在加载");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SystemClock.sleep(1500);
                        CityPickerInfo cityPickerInfo;
                        for (int i = 0; i < 30; i++) {
                            cityPickerInfo = new CityPickerInfo();
                            cityPickerInfo.setName("三级网络数据");
                            cityList.add(cityPickerInfo);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setNewData(null);
                                adapter.setNewData(cityList);
                            }
                        });
                    }
                }).start();
            }
        });
        return citySelectView;
    }


}
