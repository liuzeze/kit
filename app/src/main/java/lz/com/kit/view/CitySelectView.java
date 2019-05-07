package lz.com.kit.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lz.com.tools.R;
import lz.com.tools.city.CityPickerInfo;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.layoutmanager.GravitySnapHelper;
import lz.com.tools.util.LzDisplayUtils;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-11-14       城市区域选择
 */
public class CitySelectView extends FrameLayout {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM_TITLE = "title";
    private static final String ARG_PARAM_SELECT_POSITION = "selectPosition";


    private LinearLayout mMAreaFilter;
    //三个列表  一个确定按钮
    private ReboundReyclerView mRvProvince;
    private RecyclerView mRvCity;
    private RecyclerView mRvDistrict;
    private View btComfirm;
    //适配器
    private BaseRecycleAdapter<CityPickerInfo, BaseViewHolder> mAdapterProvince;
    private BaseRecycleAdapter<CityPickerInfo, BaseViewHolder> mAdapterCity;
    private BaseRecycleAdapter<CityPickerInfo, BaseViewHolder> mAdapterDistrict;
    private LinearLayoutManager mLayoutManagerProvince;
    private LinearLayoutManager mLayoutManagerCity;
    private LinearLayoutManager mLayoutManagerDistrict;

    //大实体集合  包含 一级 二级 三级 数据
    private List<CityPickerInfo> mAreaItem = new ArrayList<>();

    private OnAreaSelectListener mSelectListener;
    //列表条目点选位置记录
    private int mPosition = -1;
    private int mPosition1 = -1;
    private int mPosition2 = -1;
    private String mTag = getClass().getSimpleName();

    //标题
    private TextView mleftTitleTv;
    private String mTitleStr;
    private OnReqDataListener mListener;

    public CitySelectView(@NonNull Context context) {
        this(context, null);
    }

    public CitySelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CitySelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void init(int[] positions, ArrayList<CityPickerInfo> areaItem, String leftTitleStr) {
        //列表数据和 显示位置确认
        mTitleStr = leftTitleStr;
        int[] position = positions;
        if (areaItem != null && areaItem instanceof ArrayList) {
            mAreaItem = areaItem;
        }
        if (position != null) {
            mPosition = position[0];
            mPosition1 = position[1];
            mPosition2 = position[2];
        }

        initView(getContext());
        initListener();
        initData();
    }


    /**
     * 初始化显示选中条目
     */
    private void initData() {
        //显示选中列表并且 定位到选中位置
        mAdapterProvince.setNewData(mAreaItem);
        for (int i = 0; i < mAreaItem.size(); i++) {
            if (i == mPosition) {
                List<CityPickerInfo> city = mAreaItem.get(i).getCity();
                mAdapterCity.setNewData(city);
                for (int i1 = 0; i1 < city.size(); i1++) {
                    if (i1 == mPosition1) {
                        List<CityPickerInfo> city1 = city.get(i1).getCity();
                        mAdapterDistrict.setNewData(city1);
                        if (mPosition2 != -1) {
                            mLayoutManagerDistrict.scrollToPositionWithOffset(mPosition2, 0);
                        }
                        break;
                    }

                }
                if (mPosition1 != -1) {
                    if (((LinearLayout.LayoutParams) mRvDistrict.getLayoutParams()).weight == 0f) {
                        setAnimListWidth(mRvDistrict, 0, 1);
                    }
                    mLayoutManagerCity.scrollToPositionWithOffset(mPosition1, 0);
                }
                break;
            }
        }
        if (mPosition != -1) {
            mLayoutManagerProvince.scrollToPositionWithOffset(mPosition, 0);
            if (((LinearLayout.LayoutParams) mRvCity.getLayoutParams()).weight == 0f) {
                setAnimListWidth(mRvCity, 0, 1);
            }

        }
    }

    /**
     * 初始化控件
     *
     * @param base
     */
    public void initView(Context base) {
        mMAreaFilter = (LinearLayout) View.inflate(base, R.layout.layout_city_select, null);
        mMAreaFilter.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LzDisplayUtils.getScreenHeight(getContext()) / 2));
        addView(mMAreaFilter);
        ConstraintLayout title = mMAreaFilter.findViewById(R.id.cl_title);
        mMAreaFilter.removeViewAt(0);
        mMAreaFilter.addView(title, 1);
        mRvDistrict = (RecyclerView) mMAreaFilter.findViewById(R.id.area_list3);
        mRvCity = (RecyclerView) mMAreaFilter.findViewById(R.id.area_list2);
        mRvProvince = (ReboundReyclerView) mMAreaFilter.findViewById(R.id.area_list1);
        btComfirm = mMAreaFilter.findViewById(R.id.bt_confirm);
        mleftTitleTv = (TextView) mMAreaFilter.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(mTitleStr)) {
            mleftTitleTv.setText(mTitleStr);
        }

        mLayoutManagerProvince = new LinearLayoutManager(getContext());
        mRvProvince.setLayoutManager(mLayoutManagerProvince);
//        mRvProvince.addItemDecoration(new DividerItemDecoration(getContext(), 1));//默认实现,线性的
        mLayoutManagerCity = new LinearLayoutManager(getContext());
        mRvCity.setLayoutManager(mLayoutManagerCity);
//        mRvCity.addItemDecoration(new DividerItemDecoration(getContext(), 1));//默认实现,线性的
        mLayoutManagerDistrict = new LinearLayoutManager(getContext());
        mRvDistrict.setLayoutManager(mLayoutManagerDistrict);
//        mRvDistrict.addItemDecoration(new DividerItemDecoration(getContext(), 1));//默认实现,线性的
        //列表条目辅助显示工具
        new GravitySnapHelper(Gravity.TOP).attachToRecyclerView(mRvProvince);
        new GravitySnapHelper(Gravity.TOP).attachToRecyclerView(mRvCity);
        new GravitySnapHelper(Gravity.TOP).attachToRecyclerView(mRvDistrict);
        mAdapterProvince = new BaseRecycleAdapter<CityPickerInfo, BaseViewHolder>(R.layout.textview_item) {
            @Override
            protected void onBindView(BaseViewHolder helper, CityPickerInfo item) {
                TextView name = helper.getView(R.id.tv_name);
                name.setText(item.getName());
                name.setSelected(helper.getLayoutPosition() - getHeaderLayoutCount() == mPosition);
            }

        };
        mRvProvince.setAdapter(mAdapterProvince);
        mAdapterCity = new BaseRecycleAdapter<CityPickerInfo, BaseViewHolder>(R.layout.textview_item) {
            @Override
            protected void onBindView(BaseViewHolder helper, CityPickerInfo item) {
                TextView name = helper.getView(R.id.tv_name);
                name.setText(item.getName());
                name.setSelected(helper.getLayoutPosition() - getHeaderLayoutCount() == mPosition1);

            }

        };
        mRvCity.setAdapter(mAdapterCity);
        mAdapterDistrict = new BaseRecycleAdapter<CityPickerInfo, BaseViewHolder>(R.layout.textview_item) {
            @Override
            protected void onBindView(BaseViewHolder helper, CityPickerInfo item) {
                TextView name = helper.getView(R.id.tv_name);
                name.setText(item.getName());
                name.setSelected(helper.getLayoutPosition() - getHeaderLayoutCount() == mPosition2);

            }

        };
        mRvDistrict.setAdapter(mAdapterDistrict);


    }


    /**
     * 二三级列表的 展示 隐藏动画
     *
     * @param arealist
     * @param start
     * @param end
     */
    private void setAnimListWidth(final RecyclerView arealist, float start, float end) {


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) arealist.getLayoutParams();
                layoutParams.weight = (float) animation.getAnimatedValue();
                arealist.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    /**
     * 筛选区域view获取
     */

    private void initListener() {
        mAdapterProvince.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                //更新当前条目
                mPosition = position;
                adapter.setNewData(mAreaItem);


                mPosition1 = -1;
                mPosition2 = -1;
                //二三四级条目刷新
                List<CityPickerInfo> cityList = mAdapterProvince.getData().get(position).getCity();
                if (cityList.size() == 0) {
                    //去加载网络数据
                    if (mListener != null) {
                        mListener.onCityDataSelect(mAdapterCity, cityList, mAdapterProvince.getData().get(position).getId());
                    }
                } else {
                    //直接刷新
                    mAdapterCity.setNewData(cityList);
                    mAdapterDistrict.setNewData(null);

                }
                //刷新二级  隐藏三四级
                if (((LinearLayout.LayoutParams) mRvCity.getLayoutParams()).weight == 0f) {
                    setAnimListWidth(mRvCity, 0, 1);
                }

                if (((LinearLayout.LayoutParams) mRvDistrict.getLayoutParams()).weight == 1f) {
                    setAnimListWidth(mRvDistrict, 1, 0);
                }

            }
        });
        mAdapterCity.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                mPosition1 = position;
                adapter.setNewData(adapter.getData());

                mPosition2 = -1;
                //三四级条目刷新
                List<CityPickerInfo> cityList = mAdapterCity.getData().get(position).getCity();
                if (cityList.size() == 0) {
                    //去加载网络数据
                    if (mListener != null) {
                        mListener.onCityDataSelect(mAdapterDistrict, cityList, mAdapterCity.getData().get(position).getId());
                    }
                } else {
                    //直接刷新
                    mAdapterDistrict.setNewData(cityList);
                }
                //刷新 三级 隐藏四级
                if (((LinearLayout.LayoutParams) mRvDistrict.getLayoutParams()).weight == 0f) {
                    setAnimListWidth(mRvDistrict, 0, 1);
                }
            }
        });

        mAdapterDistrict.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                mPosition2 = position;
                adapter.setNewData(adapter.getData());

            }
        });

        btComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectListener != null) {
                    StringBuffer str = new StringBuffer("");
                    if (mPosition != -1) {
                        str.append("," + mAreaItem.get(mPosition).getName());
                        if (mPosition1 != -1) {
                            str.append("," + mAreaItem.get(mPosition).getCity().get(mPosition1).getName());
                            if (mPosition2 != -1) {
                                str.append("," + mAreaItem.get(mPosition).getCity().get(mPosition1).getCity().get(mPosition2).getName());
                            }
                        }
//
                    }
                    mSelectListener.onAreaSelect(str.toString().replaceFirst(",", ""), mPosition, mPosition1, mPosition2);
                }
            }
        });
    }


    public void setOnAreaSelectListener(OnAreaSelectListener listener) {
        mSelectListener = listener;
    }


    public interface OnAreaSelectListener {
        void onAreaSelect(String s, int... position);

    }

    public void setOnReqDataListener(OnReqDataListener listener) {
        mListener = listener;
    }


    public interface OnReqDataListener {
        void onCityDataSelect(BaseRecycleAdapter<CityPickerInfo, BaseViewHolder> adapter, List<CityPickerInfo> cityList, String id);
    }


}
