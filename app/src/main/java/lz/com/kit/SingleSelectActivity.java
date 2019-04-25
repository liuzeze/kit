package lz.com.kit;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import lz.com.kit.bean.SelectBean;
import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.BaseCheckedRecycleAdapter;
import lz.com.tools.recycleview.checked.SingleCheckedHelper;


@LayoutId(value = R.layout.activity_checked, titleName = "单选")
public class SingleSelectActivity extends BaseActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;
    private ArrayList<SelectBean> mStrings;
    private SingleCheckedHelper<SelectBean> mCheckHelper;

    @Override
    public void init() {
        super.init();

//        recyclevie.setLayoutManager(new LinearLayoutManager(this));
        recyclevie.setLayoutManager(new GridLayoutManager(this, 4));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        mAdapter = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {
            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj) {
                holder.itemView.setBackgroundColor(0xFF73E0E4);
                holder.setChecked(R.id.checkbox, true);
            }

            @Override
            public void onUnChecked(BaseViewHolder holder, SelectBean obj) {
                holder.itemView.setBackgroundColor(0xFFFFFFFF);
                holder.setChecked(R.id.checkbox, false);
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };

        mAdapter.getCheckHelper().setDefaultItem(mStrings.get(1)).setAlwaysSelectItem(mStrings.get(5)).setLastItemEnable(true);

        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);

    }


}
