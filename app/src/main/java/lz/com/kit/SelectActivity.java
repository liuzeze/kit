package lz.com.kit;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.BaseCheckedRecycleAdapter;


@InjectLayout(layoutId = R.layout.activity_checked, titleName = "单选")
public class SelectActivity extends BaseKitActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.recyclevie2)
    ReboundReyclerView recyclevie2;
    @BindView(R.id.recyclevie3)
    ReboundReyclerView recyclevie3;
    @BindView(R.id.recyclevie4)
    ReboundReyclerView recyclevie4;

    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;
    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter2;
    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter3;
    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter4;
    private ArrayList<SelectBean> mStrings;

    @Override
    public void initData() {
        recyclevie.setLayoutManager(new GridLayoutManager(this, 4));
        recyclevie2.setLayoutManager(new GridLayoutManager(this, 4));
        recyclevie3.setLayoutManager(new GridLayoutManager(this, 4));
        recyclevie4.setLayoutManager(new GridLayoutManager(this, 4));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        mAdapter = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setBackgroundColor(isChecked ? 0xFF73E0E4 : 0xffffffff);
                holder.setChecked(R.id.checkbox, isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        mAdapter.getCheckHelper().setSingleDefaultItem(mStrings.get(1)).setLastItemEnable(true);
        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);

        mAdapter3 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked, BaseCheckedRecycleAdapter.MULTI) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setBackgroundColor(isChecked ? 0xFF73E0E4 : 0xffffffff);
                holder.setChecked(R.id.checkbox, isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        mAdapter3.getCheckHelper().setSingleDefaultItem(mStrings.get(1)).setLastItemEnable(false);
        recyclevie3.setAdapter(mAdapter3);
        mAdapter3.setNewData(mStrings);


        mStrings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        mStrings.get(0).name="不限";
        mAdapter2 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setBackgroundColor(isChecked ? 0xFF73E0E4 : 0xffffffff);
                holder.setChecked(R.id.checkbox, isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };

        mAdapter2.getCheckHelper().setLastItemEnable(true).setSingleDefaultItem(mStrings.get(1)).setAlwaysSelectItem(mStrings.get(0));
        recyclevie2.setAdapter(mAdapter2);
        mAdapter2.setNewData(mStrings);




        mAdapter4 = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked, BaseCheckedRecycleAdapter.MULTI) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj, boolean isChecked) {
                holder.itemView.setBackgroundColor(isChecked ? 0xFF73E0E4 : 0xffffffff);
                holder.setChecked(R.id.checkbox, isChecked);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };
        mAdapter4.getCheckHelper().setMultiDefaultItem(mStrings.get(1)).setAlwaysSelectItem(mStrings.get(0));
        recyclevie4.setAdapter(mAdapter4);
        mAdapter4.setNewData(mStrings);


    }


}
