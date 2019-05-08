package lz.com.kit;

import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.city.CityLoadingView;
import lz.com.tools.city.CityPickerInfo;
import lz.com.kit.view.CitySelectView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.BaseCheckedRecycleAdapter;
import lz.com.tools.util.LzToast;
import lz.com.tools.widget.DropDownMenu;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_menu, titleName = "下拉控件")
public class MenuActivity extends BaseKitActivity {

    public String[] titleText = {"区域", "价格", "房型", "更多", "排序"};
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
        mViews[0] = getCityView();
        mViews[1] = getPriceView();
        mViews[2] = getHouseView();
        mViews[3] = getMoreView();
        mViews[4] = getSortView();


        mDropDownMenu.setDropDownMenu(mViews);

        mDropDownMenu.setOnDropDownMenuListener(new DropDownMenu.OnDropDownMenuListener() {
            @Override
            public void onMenuClose(int position) {

            }
        });

        //
    }

    private View getMoreView() {

        LinearLayout linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(getPriceView());
        linearLayout.addView(getHouseView());
        return linearLayout;

    }

    private View getPriceView() {
        ArrayList<SelectBean> mStrings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = i * 1000 + "";
            mStrings.add(selectBean);
        }
        mStrings.get(0).name = "不限";
        ReboundReyclerView reyclerView = new ReboundReyclerView(mActivity);
        BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter2 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setSelected(isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        reyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        reyclerView.setBackgroundColor(Color.WHITE);
        mAdapter2.getCheckHelper().setLastItemEnable(true).setAlwaysSelectItem(mStrings.get(0));
        reyclerView.setAdapter(mAdapter2);
        mAdapter2.setNewData(mStrings);
        mAdapter2.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                ArrayList<SelectBean> selectitem = mAdapter2.getSelectitem();
                StringBuffer s = new StringBuffer();
                selectitem.forEach(new Consumer<SelectBean>() {
                    @Override
                    public void accept(SelectBean selectBean) {
                        s.append(selectBean.name);
                    }
                });
                mDropDownMenu.setTabSelect(1, TextUtils.isEmpty(s.toString()) ? titleText[1] : s.toString());
                mDropDownMenu.closeMenu();
            }
        });
        return reyclerView;
    }

    private View getHouseView() {
        ArrayList<SelectBean> mStrings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = i + "居室";
            mStrings.add(selectBean);
        }
        mStrings.get(0).name = "不限";
        ReboundReyclerView reyclerView = new ReboundReyclerView(mActivity);
        BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter4 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked, BaseCheckedRecycleAdapter.MULTI) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setSelected(isChecked);

            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        reyclerView.setBackgroundColor(Color.WHITE);

        reyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter4.getCheckHelper().setAlwaysSelectItem(mStrings.get(0));
        reyclerView.setAdapter(mAdapter4);
        mAdapter4.setNewData(mStrings);
        mAdapter4.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                ArrayList<SelectBean> selectitem = mAdapter4.getSelectitem();
                StringBuffer s = new StringBuffer();
                selectitem.forEach(new Consumer<SelectBean>() {
                    @Override
                    public void accept(SelectBean selectBean) {
                        s.append(selectBean.name);
                    }
                });
                mDropDownMenu.setTabSelect(2, TextUtils.isEmpty(s.toString()) ? titleText[2] : s.toString());
                // mDropDownMenu.closeMenu();
            }
        });
        return reyclerView;
    }

    private View getSortView() {
        ArrayList<SelectBean> mStrings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "排序方式" + i;
            mStrings.add(selectBean);
        }
        mStrings.get(0).name = "不限";
        ReboundReyclerView reyclerView = new ReboundReyclerView(mActivity);
        BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter2 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setSelected(isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        reyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        reyclerView.setBackgroundColor(Color.WHITE);
        mAdapter2.getCheckHelper().setLastItemEnable(true).setAlwaysSelectItem(mStrings.get(0));
        reyclerView.setAdapter(mAdapter2);
        mAdapter2.setNewData(mStrings);
        mAdapter2.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                ArrayList<SelectBean> selectitem = mAdapter2.getSelectitem();
                StringBuffer s = new StringBuffer();
                selectitem.forEach(new Consumer<SelectBean>() {
                    @Override
                    public void accept(SelectBean selectBean) {
                        s.append(selectBean.name);
                    }
                });
                mDropDownMenu.setTabSelect(3, TextUtils.isEmpty(s.toString()) ? titleText[3] : s.toString());
                mDropDownMenu.closeMenu();
            }
        });
        return reyclerView;
        // 33000
        // 18000   18000 ==36000  8000
    }

    /**
     * 区域
     *
     * @return
     */
    private View getCityView() {
        mCityLoadingView = new CityLoadingView(mActivity);

        CityPickerInfo cityPickerInfo;
        CityPickerInfo cityPickerInfo1;
        for (int i = 0; i < 5; i++) {
            cityPickerInfo = new CityPickerInfo();
            cityPickerInfo.setName("一级区域");
            for (int i1 = 0; i1 < 10; i1++) {
                cityPickerInfo1 = new CityPickerInfo();
                cityPickerInfo1.setName("二级区域");
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

                }
                mDropDownMenu.setTabSelect(0, s);
                mDropDownMenu.closeMenu();
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
