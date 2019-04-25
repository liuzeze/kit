package lz.com.kit;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import lz.com.tools.city.CityLoadingView;
import lz.com.tools.city.CityPickerInfo;
import lz.com.tools.city.CitySelectDialog;
import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.util.LzToast;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(R.layout.activity_select_city)
public class CitySelectActivity extends BaseActivity {

    private int[] mCitySelectPosition;
    private ArrayList<CityPickerInfo> mAreaItem = new ArrayList<>();

    @BindView(R.id.bt_show_pop)
    Button mButton;
    //加载框
    private CityLoadingView mCityLoadingView;

    @OnClick({R.id.bt_show_pop})
    public void button(View view) {

        CitySelectDialog citySelectView = CitySelectDialog.newInstance(mCitySelectPosition, mAreaItem);

        citySelectView.show(getSupportFragmentManager(), "citySelect");
        citySelectView.setOnAreaSelectListener(new CitySelectDialog.OnAreaSelectListener() {
            @Override
            public void onAreaSelect(String s, int... position) {
                if (!TextUtils.isEmpty(s)) {
                    Logger.e(s);
                    LzToast.showToast(s);
                    mCitySelectPosition = position;
                    mButton.setText(s);
                }
            }
        });

        citySelectView.setOnReqDataListener(new CitySelectDialog.OnReqDataListener() {
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
                            cityPickerInfo.setName("网络数据");
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
    }

    @Override
    public void init() {
        super.init();
        mCityLoadingView = new CityLoadingView(mActivity);

        CityPickerInfo cityPickerInfo;
        for (int i = 0; i < 10; i++) {
            cityPickerInfo = new CityPickerInfo();
            cityPickerInfo.setName("一级标题");
            mAreaItem.add(cityPickerInfo);
        }

    }


}
