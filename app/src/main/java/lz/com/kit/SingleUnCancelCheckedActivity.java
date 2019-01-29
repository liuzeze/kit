package lz.com.kit;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.CheckHelper;
import lz.com.tools.recycleview.checked.SingleUnCancelCheckedHelper;

public class SingleUnCancelCheckedActivity extends AppCompatActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<SelectBean> mStrings;
    private BaseRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked);
        ButterKnife.bind(this);
        recyclevie.setLayoutManager(new LinearLayoutManager(this));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        SelectBean selectBean = new SelectBean();
        selectBean.name = "不限+";
        mStrings.add(0, selectBean);
        mAdapter = new BaseRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                holder.setText(R.id.tv_1, item.name);

            }
        };

        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);
        mAdapter.setCheckHelper(createHelper());
        recyclevie.getItemAnimator().setChangeDuration(0);
    }


    private SingleUnCancelCheckedHelper createHelper() {
        SingleUnCancelCheckedHelper<SelectBean> mCheckHelper = new SingleUnCancelCheckedHelper<>();
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

        //添加默认选中数据
        mCheckHelper.setUnCancelItem(mStrings.get(0));
        return mCheckHelper;
    }
}
