package lz.com.kit;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import lz.com.kit.bean.SelectBean;
import lz.com.tools.inject.BindView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.BaseCheckedRecycleAdapter;

@LayoutId(value = R.layout.activity_checked, titleName = "单选不可取消")
public class SingleUnCancelCheckedActivity extends BaseActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<SelectBean> mStrings;
    private BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;

    @Override
    public void init() {
        super.init();
        recyclevie.setLayoutManager(new GridLayoutManager(this, 4));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        SelectBean selectBean = new SelectBean();
        selectBean.name = "不限+";
        mStrings.add(0, selectBean);
        mAdapter = new BaseCheckedRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {

            @Override
            public void onChecked(BaseViewHolder holder, SelectBean obj) {
                holder.itemView.setBackgroundColor(0xFF73E0E4);
                holder.setChecked(R.id.checkbox, true);
                tvTitle.setText("");
                ArrayList<SelectBean> checkedList = mCheckHelper.getCheckedList();
                for (SelectBean selectBean : checkedList) {
                    tvTitle.append(selectBean.name);
                }
            }

            @Override
            public void onUnChecked(BaseViewHolder holder, SelectBean obj) {
                holder.itemView.setBackgroundColor(0xFFFFFFFF);
                holder.setChecked(R.id.checkbox, false);
                tvTitle.setText("");
                ArrayList<SelectBean> checkedList = mCheckHelper.getCheckedList();
                for (SelectBean selectBean : checkedList) {
                    tvTitle.append(selectBean.name);
                }
            }

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                super.onBindView(holder, item);
                holder.setText(R.id.tv_1, item.name);

            }
        };


        mAdapter.getCheckHelper().setLastItemEnable(true).setDefaultItem(mStrings.get(1)).setAlwaysSelectItem(mStrings.get(5));

        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);
        recyclevie.getItemAnimator().setChangeDuration(0);
    }


}
