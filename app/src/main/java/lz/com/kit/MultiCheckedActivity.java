package lz.com.kit;

import android.os.Bundle;

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
import lz.com.tools.recycleview.checked.MultiCheckedHelper;
import lz.com.tools.recycleview.checked.SingleCheckedHelper;

public class MultiCheckedActivity extends AppCompatActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private List<SelectBean> mStrings;
    private BaseRecycleAdapter<SelectBean, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_checked);
        ButterKnife.bind(this);
        recyclevie.setLayoutManager(new LinearLayoutManager(this));
        mStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name="位置+" + i;
            mStrings.add(selectBean);
        }
        mAdapter = new BaseRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_checked) {

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                holder.setText(R.id.tv_1, item.name);

            }
        };

        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);
        mAdapter.setCheckHelper(createHelper());

    }


    private MultiCheckedHelper createHelper() {
        MultiCheckedHelper<SelectBean> mCheckHelper = new MultiCheckedHelper<>();
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

                });

        //添加默认选中数据
        mCheckHelper.setDefaultItem(mStrings.get(1), mStrings.get(3));
        return mCheckHelper;
    }
}
