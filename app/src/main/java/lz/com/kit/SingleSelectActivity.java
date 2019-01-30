package lz.com.kit;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.CheckHelper;
import lz.com.tools.recycleview.checked.SingleCheckedHelper;

public class SingleSelectActivity extends BaseActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private BaseRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;
    private ArrayList<SelectBean> mStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked);
        ButterKnife.bind(this);


//        recyclevie.setLayoutManager(new LinearLayoutManager(this));
        recyclevie.setLayoutManager(new GridLayoutManager(this, 4));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        mAdapter = new BaseRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                holder.setText(R.id.tv_1, item.name);
//
            }
        };

        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);
        mAdapter.setCheckHelper(createHelper());

    }


    private SingleCheckedHelper createHelper() {
        SingleCheckedHelper<SelectBean> mCheckHelper = new SingleCheckedHelper<>();
        mCheckHelper.setOnCheckedListener(
                new CheckHelper.OnCheckedListener<SelectBean, BaseViewHolder>() {

                    @Override
                    public void onChecked(BaseViewHolder holder, SelectBean obj) {
                        holder.itemView.setBackgroundColor(0xFF73E0E4); //蓝色
                        holder.setChecked(R.id.checkbox, true);
                    }

                    @Override
                    public void onUnChecked(BaseViewHolder holder, SelectBean obj) {
                        holder.itemView.setBackgroundColor(0xFFFFFFFF);  //白色
                        holder.setChecked(R.id.checkbox, false);

                    }

                    @Override
                    public void onSelectitem(List<SelectBean> itemLists) {
                        tvTitle.setText("");
                        ArrayList<SelectBean> checkedList = mCheckHelper.getCheckedList();
                        for (SelectBean selectBean : checkedList) {
                            tvTitle.append(selectBean.name);
                        }
                    }

                });
        //设置不能取消
        mCheckHelper.setCanCancel(false);

        //添加默认选中数据
        mCheckHelper.setDefaultItem(mStrings.get(0));
        return mCheckHelper;
    }
}
